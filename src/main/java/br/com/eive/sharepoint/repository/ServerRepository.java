package br.com.eive.sharepoint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.eive.sharepoint.model.Server;

public interface ServerRepository extends JpaRepository<Server, Long>{

	List<Server> findByCustomerId(Long customerId);
}
