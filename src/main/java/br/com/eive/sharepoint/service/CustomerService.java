package br.com.eive.sharepoint.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.eive.sharepoint.controller.dto.ContactDto;
import br.com.eive.sharepoint.controller.dto.CustomerDto;
import br.com.eive.sharepoint.controller.form.CustomerForm;
import br.com.eive.sharepoint.model.Contact;
import br.com.eive.sharepoint.model.Customer;
import br.com.eive.sharepoint.repository.ContactRepository;
import br.com.eive.sharepoint.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ModelMapper modelMapper;

	public List<CustomerDto> findAll(Optional<String> name) {
		List<Customer> customers;
		if (!name.isPresent()) {
			customers = customerRepository.findAll();
		} else {
			customers = customerRepository.findByName(name.get());
		}

		List<CustomerDto> customersDto = customers.stream()
				.map(customer -> modelMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());

		List<Contact> contacts = contactRepository
				.findByCustomerIdInAndMainTrue(customers.stream().map(Customer::getId).collect(Collectors.toList()));

		customersDto.forEach(customerDto -> {
			Optional<Contact> contactSon = contacts.stream()
					.filter(contact -> contact.getCustomer().getId() == customerDto.getId()).findFirst();
			if (contactSon.isPresent()) {
				customerDto.setContact(modelMapper.map(contactSon.get(), ContactDto.class));
			}
		});

		return customersDto;
	}

	public Optional<CustomerDto> findById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (!customer.isPresent()) {
			return Optional.empty();
		}

		CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);

		Optional<Contact> contact = contactRepository.findByCustomerIdAndMainTrue(customerDto.getId()).stream()
				.findAny();

		if (contact.isPresent()) {
			customerDto.setContact(modelMapper.map(contact.get(), ContactDto.class));
		}

		return Optional.of(customerDto);
	}

	public CustomerDto create(CustomerForm customerForm) {
		return modelMapper.map(customerRepository.save(modelMapper.map(customerForm, Customer.class)),
				CustomerDto.class);
	}

	public Optional<CustomerDto> update(Long id, CustomerForm customerForm) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (!customer.isPresent()) {
			return Optional.empty();
		}

		Customer customerUpdate = customer.get();
		modelMapper.map(customerForm, customerUpdate);
		customerUpdate = customerRepository.save(customerUpdate);

		return Optional.of(modelMapper.map(customerUpdate, CustomerDto.class));
	}

	public Boolean remove(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (!customer.isPresent()) {
			return false;
		}

		customerRepository.deleteById(id);
		return true;
	}

	public Boolean existsById(Long id) {
		return customerRepository.existsById(id);
	}

}
