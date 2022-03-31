package com.devsuperior.dscatalog.repositories;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import com.devsuperior.dscatalog.entities.Product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
	    //1º 
	    long exintingId = 1L;

	    //2º
	    repository.deleteById(exintingId);
	    Optional<Product> result = repository.findById(exintingId);

	    //3º
	    Assertions.assertFalse(result.isPresent());	
    }
    
}
