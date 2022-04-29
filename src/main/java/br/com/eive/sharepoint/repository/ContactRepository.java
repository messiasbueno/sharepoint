package br.com.eive.sharepoint.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.eive.sharepoint.model.Contact;

public interface ContactRepository extends CrudRepository<Contact, Long>{

}
