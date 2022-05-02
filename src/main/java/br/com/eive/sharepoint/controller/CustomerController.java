package br.com.eive.sharepoint.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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

import br.com.eive.sharepoint.controller.dto.CustomerDto;
import br.com.eive.sharepoint.controller.form.CustomerForm;
import br.com.eive.sharepoint.service.CustomerService;

@CrossOrigin
@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@GetMapping
	public ResponseEntity<List<CustomerDto>> findAll(@RequestParam(name = "name") Optional<String> name) {
		return ResponseEntity.ok(customerService.findAll(name));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> findId(@PathVariable("id") Long id) {
		Optional<CustomerDto> customer = customerService.findById(id);
		
		if (!customer.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer.get());
	}

	@PostMapping
	@Transactional
	public ResponseEntity<CustomerDto> create(@RequestBody @Validated CustomerForm customerForm,
			UriComponentsBuilder uriBuilder) {
		CustomerDto customer = customerService.create(customerForm);

		URI uri = uriBuilder.path("/customer/{id}").buildAndExpand(customer.getId()).toUri();

		return ResponseEntity.created(uri).body(customer);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CustomerDto> update(@PathVariable("id") Long id,
			@RequestBody @Validated CustomerForm customerForm) {
		Optional<CustomerDto> customer = customerService.update(id, customerForm);
		if (!customer.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(customer.get());
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remove(@PathVariable Long id) {
		if (!customerService.remove(id)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().build();
	}
}
