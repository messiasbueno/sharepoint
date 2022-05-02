package br.com.eive.sharepoint.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
		List<Contact> contacts = contactRepository.findAll();
		List<ContactDto> contactsDto = contacts.stream().map(contact -> modelMapper.map(contact, ContactDto.class))
				.collect(Collectors.toList());

		return contactsDto;
	}

	public List<ContactDto> findByCustomerId(Long customerId) {
		List<Contact> contacts = contactRepository.findByCustomerId(customerId);
		List<ContactDto> contactsDto = contacts.stream().map(contact -> modelMapper.map(contact, ContactDto.class))
				.collect(Collectors.toList());

		return contactsDto;
	}

	public ContactDto create(ContactForm contactForm) {
		if (!customerService.existsById(contactForm.getCustomerId())) {
			throw new ErrorException("customerId", messageSource.getMessage("customer.notfound",
					new Object[] { contactForm.getCustomerId() }, Locale.getDefault()));
		}

		Contact contact = modelMapper.map(contactForm, Contact.class);
		contact = contactRepository.save(contact);

		return modelMapper.map(contact, ContactDto.class);
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

	@PostConstruct
	private void modelMapperConfig() {
		this.modelMapper.addMappings(new PropertyMap<ContactForm, Contact>() {
			@Override
			protected void configure() {
				skip(destination.getId());
			}
		});
	}
}
