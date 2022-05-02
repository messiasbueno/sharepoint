package br.com.eive.sharepoint.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.eive.sharepoint.controller.dto.ServerDto;
import br.com.eive.sharepoint.controller.form.ServerForm;
import br.com.eive.sharepoint.core.error.ErrorException;
import br.com.eive.sharepoint.model.Server;
import br.com.eive.sharepoint.repository.ServerRepository;

@Service
public class ServerService {

	@Autowired
	ServerRepository serverRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CustomerService customerService;

	public List<ServerDto> findAll() {
		return serverRepository.findAll().stream().map(server -> modelMapper.map(server, ServerDto.class))
				.collect(Collectors.toList());
	}

	public List<ServerDto> findByCustomerId(Long customerId) {
		return serverRepository.findByCustomerId(customerId).stream()
				.map(server -> modelMapper.map(server, ServerDto.class)).collect(Collectors.toList());
	}

	public ServerDto create(ServerForm serverForm) {
		if (!customerService.existsById(serverForm.getCustomerId())) {
			throw new ErrorException("customerId", messageSource.getMessage("customer.notfound",
					new Object[] { serverForm.getCustomerId() }, Locale.getDefault()));
		}

		return modelMapper.map(serverRepository.save(modelMapper.map(serverForm, Server.class)), ServerDto.class);
	}

	public Optional<ServerDto> update(Long id, ServerForm serverForm) {
		Optional<Server> server = serverRepository.findById(id);

		if (!server.isPresent()) {
			return Optional.empty();
		}

		Server serverUpdate = server.get();
		modelMapper.map(serverForm, serverUpdate);
		serverUpdate = serverRepository.save(serverUpdate);

		return Optional.of(modelMapper.map(serverUpdate, ServerDto.class));
	}

	public Boolean remove(Long id) {
		Optional<Server> server = serverRepository.findById(id);
		if (!server.isPresent()) {
			return false;
		}

		serverRepository.deleteById(id);
		return true;
	}
}
