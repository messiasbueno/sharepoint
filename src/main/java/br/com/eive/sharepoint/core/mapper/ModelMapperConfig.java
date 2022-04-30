package br.com.eive.sharepoint.core.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.eive.sharepoint.controller.form.ContactForm;
import br.com.eive.sharepoint.controller.form.CustomerForm;
import br.com.eive.sharepoint.controller.form.ProductForm;
import br.com.eive.sharepoint.controller.form.ServerForm;
import br.com.eive.sharepoint.model.Contact;
import br.com.eive.sharepoint.model.Customer;
import br.com.eive.sharepoint.model.Product;
import br.com.eive.sharepoint.model.Server;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();

		modelMapper.addMappings(new PropertyMap<CustomerForm, Customer>() {@Override protected void configure() {skip(destination.getId());}});
		modelMapper.addMappings(new PropertyMap<ProductForm, Product>() {@Override protected void configure() {skip(destination.getId());}});
		modelMapper.addMappings(new PropertyMap<ContactForm, Contact>() {@Override protected void configure() {skip(destination.getId());}});
		modelMapper.addMappings(new PropertyMap<ServerForm, Server>() {@Override protected void configure() {skip(destination.getId());}});
		

		return modelMapper;
	}

}