package com.devsuperior.dscatalog.repositories;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import com.devsuperior.dscatalog.entities.Product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.dao.EmptyResultDataAccessException;

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

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
    
        long nonExistingId = 1000L;

        // 3º [A]ssert
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {

            // 1º [A]rrange

            // 2º [A]ct
            repository.deleteById(nonExistingId);
        });
    }    
}
