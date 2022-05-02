package br.com.eive.sharepoint.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import br.com.eive.sharepoint.service.ServerService;

@CrossOrigin
@RestController
@RequestMapping("/server")
public class ServerController {

	@Autowired
	ServerService serverService;

	@GetMapping
	public ResponseEntity<List<ServerDto>> findAll() {
		List<ServerDto> servers = serverService.findAll();
		return ResponseEntity.ok(servers);
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<List<ServerDto>> findId(@PathVariable("id") Long customerId) {
		List<ServerDto> servers = serverService.findByCustomerId(customerId);
		return ResponseEntity.ok(servers);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ServerDto> create(@RequestBody @Valid ServerForm serverForm,
			UriComponentsBuilder uriBuilder) {
		ServerDto server = serverService.create(serverForm);

		URI uri = uriBuilder.path("/server/{id}").buildAndExpand(server.getId()).toUri();

		return ResponseEntity.created(uri).body(server);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ServerDto> update(@PathVariable("id") Long id,
			@RequestBody @Valid ServerForm serverForm) {
		Optional<ServerDto> server = serverService.update(id, serverForm);
		if (!server.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(server.get());
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> remove(@PathVariable Long id) {
		if (!serverService.remove(id)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().build();
	}
}
