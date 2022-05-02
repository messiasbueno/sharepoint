package br.com.eive.sharepoint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eive.sharepoint.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

	List<Contact> findByCustomerIdAndMainTrue(Long customerId);
	List<Contact> findByCustomerIdInAndMainTrue(Iterable<Long> customersId);
	List<Contact> findByCustomerId(Long customerId);
}
