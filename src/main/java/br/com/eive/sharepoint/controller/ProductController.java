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

import br.com.eive.sharepoint.controller.dto.ProductDto;
import br.com.eive.sharepoint.controller.form.ProductForm;
import br.com.eive.sharepoint.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping
	public ResponseEntity<List<ProductDto>> findAll() {
		List<ProductDto> products = productService.findAll();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<List<ProductDto>> findId(@PathVariable("id") Long customerId) {
		List<ProductDto> products = productService.findByCustomerId(customerId);
		return ResponseEntity.ok(products);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductForm productForm,
			UriComponentsBuilder uriBuilder) {
		ProductDto product = productService.create(productForm);

		URI uri = uriBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri();

		return ResponseEntity.created(uri).body(product);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProductDto> update(@PathVariable("id") Long id,
			@RequestBody @Valid ProductForm productForm) {
		Optional<ProductDto> product = productService.update(id, productForm);
		if (!product.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(product.get());
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Object> remove(@PathVariable Long id) {
		if (!productService.remove(id)) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().build();
	}
}
