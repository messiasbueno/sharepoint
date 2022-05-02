package br.com.eive.sharepoint.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.eive.sharepoint.controller.dto.ContactDto;
import br.com.eive.sharepoint.controller.form.ContactForm;
import br.com.eive.sharepoint.core.error.ErrorException;
import br.com.eive.sharepoint.model.Contact;
import br.com.eive.sharepoint.repository.ContactRepository;

@Service
public class ContactService {

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CustomerService customerService;

	public List<ContactDto> findAll() {
		return contactRepository.findAll().stream().map(contact -> modelMapper.map(contact, ContactDto.class))
				.collect(Collectors.toList());
	}

	public List<ContactDto> findByCustomerId(Long customerId) {
		return contactRepository.findByCustomerId(customerId).stream()
				.map(contact -> modelMapper.map(contact, ContactDto.class)).collect(Collectors.toList());
	}

	public ContactDto create(ContactForm contactForm) {
		if (!customerService.existsById(contactForm.getCustomerId())) {
			throw new ErrorException("customerId", messageSource.getMessage("customer.notfound",
					new Object[] { contactForm.getCustomerId() }, Locale.getDefault()));
		}

		return modelMapper.map(contactRepository.save(modelMapper.map(contactForm, Contact.class)), ContactDto.class);
	}

	public Optional<ContactDto> update(Long id, ContactForm contactForm) {
		Optional<Contact> contact = contactRepository.findById(id);

		if (!contact.isPresent()) {
			return Optional.empty();
		}

		Contact contactUpdate = contact.get();
		modelMapper.map(contactForm, contactUpdate);
		contactUpdate = contactRepository.save(contactUpdate);

		return Optional.of(modelMapper.map(contactUpdate, ContactDto.class));
	}

	public Boolean remove(Long id) {
		Optional<Contact> contact = contactRepository.findById(id);
		if (!contact.isPresent()) {
			return false;
		}

		contactRepository.deleteById(id);
		return true;
	}
}
