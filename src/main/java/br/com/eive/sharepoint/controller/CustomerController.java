package br.com.eive.sharepoint.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.eive.sharepoint.controller.dto.ContactDto;
import br.com.eive.sharepoint.controller.dto.CustomerDto;
import br.com.eive.sharepoint.controller.dto.CustomerSimpleDto;
import br.com.eive.sharepoint.controller.form.CustomerForm;
import br.com.eive.sharepoint.model.Contact;
import br.com.eive.sharepoint.model.Customer;
import br.com.eive.sharepoint.repository.ContactRepository;
import br.com.eive.sharepoint.repository.CustomerRepository;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<CustomerDto>> findAll(@RequestParam(name = "name") Optional<String> name) {

		List<Customer> customers;
		if (!name.isPresent()) {
			customers = customerRepository.findAll();
		} else {
			customers = customerRepository.findByName(name.get());
		}

		if (customers.isEmpty()) {
			return ResponseEntity.noContent().build();
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

		return ResponseEntity.ok(customersDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> findId(@PathVariable("id") Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (!customer.isPresent()) {
			return ResponseEntity.noContent().build();
		}

		CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);

		Optional<Contact> contact = contactRepository.findByCustomerIdAndMainTrue(customerDto.getId());

		if (contact.isPresent()) {
			customerDto.setContact(modelMapper.map(contact.get(), ContactDto.class));
		}

		return ResponseEntity.ok(customerDto);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<CustomerDto> create(@RequestBody @Validated CustomerForm customerForm,
			UriComponentsBuilder uriBuilder) {
		Customer customer = modelMapper.map(customerForm, Customer.class);
		customer = customerRepository.save(customer);

		URI uri = uriBuilder.path("/customer/{id}").buildAndExpand(customer.getId()).toUri();

		return ResponseEntity.created(uri).body(modelMapper.map(customer, CustomerDto.class));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CustomerSimpleDto> update(@PathVariable("id") Long id,
			@RequestBody @Validated CustomerForm customerForm) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (!customer.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Customer customerUpdate = customer.get();
		modelMapper.map(customerForm, customerUpdate);
		customerUpdate = customerRepository.save(customerUpdate);

		return ResponseEntity.ok(modelMapper.map(customerUpdate, CustomerSimpleDto.class));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isPresent()) {
			customerRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}
