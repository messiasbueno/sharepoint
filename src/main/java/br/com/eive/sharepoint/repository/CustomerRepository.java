package br.com.eive.sharepoint.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.eive.sharepoint.model.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>{

}
