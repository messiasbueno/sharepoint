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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.eive.sharepoint.controller.dto.ContactDto;
import br.com.eive.sharepoint.controller.form.ContactForm;
import br.com.eive.sharepoint.model.Contact;
import br.com.eive.sharepoint.repository.ContactRepository;

@CrossOrigin
@RestController
@RequestMapping("/contact")
public class ContactController {

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<ContactDto>> findAll() {

		List<Contact> contacts = contactRepository.findAll();

		if (contacts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<ContactDto> contactsDto = contacts.stream().map(contact -> modelMapper.map(contact, ContactDto.class))
				.collect(Collectors.toList());

		return ResponseEntity.ok(contactsDto);
	}

	@RequestMapping("contact/{id}")
	@GetMapping
	public ResponseEntity<List<ContactDto>> findId(@PathVariable("id") Long id) {

		List<Contact> contacts = contactRepository.findByCustomerId(id);
		if (contacts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<ContactDto> contactsDto = contacts.stream().map(contact -> modelMapper.map(contact, ContactDto.class))
				.collect(Collectors.toList());

		return ResponseEntity.ok(contactsDto);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ContactDto> create(@RequestBody @Validated ContactForm contactForm,
			UriComponentsBuilder uriBuilder) {
		Contact contact = modelMapper.map(contactForm, Contact.class);
		contact = contactRepository.save(contact);

		URI uri = uriBuilder.path("/contact/{id}").buildAndExpand(contact.getId()).toUri();

		return ResponseEntity.created(uri).body(modelMapper.map(contact, ContactDto.class));
	}

	@RequestMapping("/{id}")
	@PutMapping
	@Transactional
	public ResponseEntity<ContactDto> update(@PathVariable("id") Long id,
			@RequestBody @Validated ContactForm contactForm) {
		Optional<Contact> contact = contactRepository.findById(id);

		if (!contact.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Contact contactUpdate = contact.get();
		modelMapper.map(contactForm, contactUpdate);
		contactUpdate = contactRepository.save(contactUpdate);

		return ResponseEntity.ok(modelMapper.map(contactUpdate, ContactDto.class));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Contact> contact = contactRepository.findById(id);
		if (contact.isPresent()) {
			contactRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}
