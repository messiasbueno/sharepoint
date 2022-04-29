package br.com.eive.sharepoint.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.eive.sharepoint.controller.adapter.PageAdapter;
import br.com.eive.sharepoint.controller.form.CustomerForm;
import br.com.eive.sharepoint.model.Customer;
import br.com.eive.sharepoint.repository.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@CrossOrigin
	@GetMapping
	public ResponseEntity<PageAdapter<Customer>> findAll(Pageable pageable) {
		Page<Customer> customers = customerRepository.findAll(pageable);
		if (customers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(new PageAdapter<Customer>(customers));
	}

	@CrossOrigin
	@RequestMapping("/{id}")
	@GetMapping
	public ResponseEntity<?> findId(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		if (customer.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(customer);
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
