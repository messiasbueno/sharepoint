package br.com.eive.sharepoint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eive.sharepoint.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByCustomerId(Long customerId);
}















