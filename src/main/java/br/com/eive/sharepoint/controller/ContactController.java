package br.com.eive.sharepoint.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.eive.sharepoint.controller.dto.ContactDto;
import br.com.eive.sharepoint.controller.form.ContactForm;
import br.com.eive.sharepoint.service.ContactService;

@CrossOrigin
@RestController
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	ContactService contactService;

	@GetMapping
	public ResponseEntity<List<ContactDto>> findAll() {
		List<ContactDto> contacts = contactService.findAll();
		return ResponseEntity.ok(contacts);
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<List<ContactDto>> findId(@PathVariable("id") Long customerId) {
		List<ContactDto> contacts = contactService.findByCustomerId(customerId);
		return ResponseEntity.ok(contacts);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ContactDto> create(@RequestBody @Valid ContactForm contactForm,
			UriComponentsBuilder uriBuilder) {
		ContactDto contact = contactService.create(contactForm);

		URI uri = uriBuilder.path("/contact/{id}").buildAndExpand(contact.getId()).toUri();

		return ResponseEntity.created(uri).body(contact);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ContactDto> update(@PathVariable("id") Long id,
			@RequestBody @Valid ContactForm contactForm) {
		Optional<ContactDto> contact = contactService.update(id, contactForm);
		if (!contact.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(contact.get());
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> remove(@PathVariable Long id) {
		if (!contactService.remove(id)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().build();
	}
}
