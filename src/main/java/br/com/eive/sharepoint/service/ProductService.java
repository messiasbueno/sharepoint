package br.com.eive.sharepoint.service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import br.com.eive.sharepoint.controller.dto.ProductDto;
import br.com.eive.sharepoint.controller.form.ProductForm;
import br.com.eive.sharepoint.core.error.ErrorException;
import br.com.eive.sharepoint.model.Product;
import br.com.eive.sharepoint.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	MessageSource messageSource;

	@Autowired
	CustomerService customerService;

	public List<ProductDto> findAll() {
		return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDto.class))
				.collect(Collectors.toList());
	}

	public List<ProductDto> findByCustomerId(Long customerId) {
		return productRepository.findByCustomerId(customerId).stream()
				.map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
	}

	public ProductDto create(ProductForm productForm) {
		if (!customerService.existsById(productForm.getCustomerId())) {
			throw new ErrorException("customerId", messageSource.getMessage("customer.notfound",
					new Object[] { productForm.getCustomerId() }, Locale.getDefault()));
		}

		return modelMapper.map(productRepository.save(modelMapper.map(productForm, Product.class)), ProductDto.class);
	}

	public Optional<ProductDto> update(Long id, ProductForm productForm) {
		Optional<Product> product = productRepository.findById(id);

		if (!product.isPresent()) {
			return Optional.empty();
		}

		Product productUpdate = product.get();
		modelMapper.map(productForm, productUpdate);
		productUpdate = productRepository.save(productUpdate);

		return Optional.of(modelMapper.map(productUpdate, ProductDto.class));
	}

	public Boolean remove(Long id) {
		Optional<Product> product = productRepository.findById(id);
		if (!product.isPresent()) {
			return false;
		}

		productRepository.deleteById(id);
		return true;
	}
}
