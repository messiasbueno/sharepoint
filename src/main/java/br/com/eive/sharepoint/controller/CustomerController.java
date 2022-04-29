package br.com.eive.sharepoint.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.eive.sharepoint.controller.dto.CustomerDto;
import br.com.eive.sharepoint.controller.form.CustomerForm;
import br.com.eive.sharepoint.model.Customer;
import br.com.eive.sharepoint.repository.CustomerRepository;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<CustomerDto>> findAll(@RequestParam(name = "name") Optional<String> name) {

		List<Customer> customers;
		if (!name.isPresent()) {
			customers = customerRepository.findAll();
		} else {
			customers = customerRepository.findByNameContaining(name.get());
		}

		if (customers.isEmpty()) {
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.ok(customers.stream().map(customer -> modelMapper.map(customer, CustomerDto.class))
				.collect(Collectors.toList()));
	}

	@RequestMapping("/{id}")
	@GetMapping
	public ResponseEntity<?> findId(@PathVariable("id") Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (!customer.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(customer.get());
	}

	@PostMapping
	@Transactional
	public ResponseEntity<Customer> create(@RequestBody CustomerForm customerForm, UriComponentsBuilder uriBuilder) {
		Customer customer = customerForm.convert();
		customerRepository.save(customer);
		
		URI uri = uriBuilder.path("/customer/{id}").buildAndExpand(customer.getId()).toUri();
		
		return ResponseEntity.created(uri).body(customer);
	}
}
