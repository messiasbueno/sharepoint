package br.com.eive.sharepoint.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eive.sharepoint.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long>{

	Optional<Contact> findByCustomerIdAndMainTrue(Long customerId);
	List<Contact> findByCustomerIdInAndMainTrue(Iterable<Long> customersId);
	List<Contact> findByCustomerId(Long customerId);
}
