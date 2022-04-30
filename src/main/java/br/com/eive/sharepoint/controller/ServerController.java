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

import br.com.eive.sharepoint.controller.dto.ServerDto;
import br.com.eive.sharepoint.controller.form.ServerForm;
import br.com.eive.sharepoint.model.Server;
import br.com.eive.sharepoint.repository.ServerRepository;

@CrossOrigin
@RestController
@RequestMapping("/server")
public class ServerController {

	@Autowired
	ServerRepository serverRepository;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<?>> findAll() {

		List<Server> servers = serverRepository.findAll();

		if (servers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<ServerDto> serversDto = servers.stream().map(server -> modelMapper.map(server, ServerDto.class))
				.collect(Collectors.toList());

		return ResponseEntity.ok(serversDto);
	}

	@RequestMapping("server/{id}")
	@GetMapping
	public ResponseEntity<?> findId(@PathVariable("id") Long id) {

		List<Server> servers = serverRepository.findByCustomerId(id);
		if (servers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<ServerDto> serversDto = servers.stream().map(server -> modelMapper.map(server, ServerDto.class))
				.collect(Collectors.toList());

		return ResponseEntity.ok(serversDto);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ServerDto> create(@RequestBody @Validated ServerForm serverForm,
			UriComponentsBuilder uriBuilder) {
		Server server = modelMapper.map(serverForm, Server.class);
		server = serverRepository.save(server);

		URI uri = uriBuilder.path("/server/{id}").buildAndExpand(server.getId()).toUri();

		return ResponseEntity.created(uri).body(modelMapper.map(server, ServerDto.class));
	}

	@RequestMapping("/{id}")
	@PutMapping
	@Transactional
	public ResponseEntity<ServerDto> update(@PathVariable("id") Long id,
			@RequestBody @Validated ServerForm serverForm) {
		Optional<Server> server = serverRepository.findById(id);

		if (!server.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Server serverUpdate = server.get();
		modelMapper.map(serverForm, serverUpdate);
		serverUpdate = serverRepository.save(serverUpdate);

		return ResponseEntity.ok(modelMapper.map(serverUpdate, ServerDto.class));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Server> server = serverRepository.findById(id);
		if (server.isPresent()) {
			serverRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}
