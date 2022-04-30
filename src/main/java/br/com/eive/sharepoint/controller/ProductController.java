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

import br.com.eive.sharepoint.controller.dto.ProductDto;
import br.com.eive.sharepoint.controller.form.ProductForm;
import br.com.eive.sharepoint.model.Product;
import br.com.eive.sharepoint.repository.ProductRepository;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ModelMapper modelMapper;

	@GetMapping
	public ResponseEntity<List<ProductDto>> findAll() {

		List<Product> products = productRepository.findAll();

		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<ProductDto> productsDto = products.stream().map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());

		return ResponseEntity.ok(productsDto);
	}

	@RequestMapping("customer/{id}")
	@GetMapping
	public ResponseEntity<List<ProductDto>> findId(@PathVariable("id") Long id) {

		List<Product> products = productRepository.findByCustomerId(id);
		if (products.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		List<ProductDto> productsDto = products.stream().map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());

		return ResponseEntity.ok(productsDto);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ProductDto> create(@RequestBody @Validated ProductForm productForm,
			UriComponentsBuilder uriBuilder) {
		Product product = modelMapper.map(productForm, Product.class);
		product = productRepository.save(product);

		URI uri = uriBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri();

		return ResponseEntity.created(uri).body(modelMapper.map(product, ProductDto.class));
	}

	@RequestMapping("/{id}")
	@PutMapping
	@Transactional
	public ResponseEntity<ProductDto> update(@PathVariable("id") Long id,
			@RequestBody @Validated ProductForm productForm) {
		Optional<Product> product = productRepository.findById(id);

		if (!product.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		Product productUpdate = product.get();
		modelMapper.map(productForm, productUpdate);
		productUpdate = productRepository.save(productUpdate);

		return ResponseEntity.ok(modelMapper.map(productUpdate, ProductDto.class));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Product> product = productRepository.findById(id);
		if (product.isPresent()) {
			productRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}
}
