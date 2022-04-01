package com.devsuperior.dscatalog.repositories;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    //Exemplo de Fixtures
    //Executado antes de cada um dos testes da classe.
    private long exintingId;
    private long nonExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        exintingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L; 
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        Product product = Factory.createProduct();
        product.setId(null);
        product = repository.save(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }
    
    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
	    //1º 
	    //long exintingId = 1L;

	    //2º
	    repository.deleteById(exintingId);
	    Optional<Product> result = repository.findById(exintingId);

	    //3º
	    Assertions.assertFalse(result.isPresent());	
    }

    @Test
    public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {
    
        //long nonExistingId = 1000L;

        // 3º [A]ssert
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {

            // 1º [A]rrange

            // 2º [A]ct
            repository.deleteById(nonExistingId);
        });
    }
    
    //02-21 Exercício com repository
    //Exercícios: testes de repository
    //Solução: https://youtu.be/qm3K1dkzJBM
    
    //Favor implementar os seguintes testes em ProductRepositoryTests:
    //    • findById deveria 
    //        ◦ retornar um Optional<Product> não vazio quando o id existir
    //        ◦ retornar um Optional<Product> vazio quando o id não existir    
    @Test
    public void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
	    //1º 
	    //long exintingId = 1L;

	    //2º	    
	    Optional<Product> result = repository.findById(exintingId);

	    //3º
	    Assertions.assertTrue(result.isPresent());	
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist() {
	    //1º 
	    //long exintingId = 1L;

	    //2º	    
	    Optional<Product> result = repository.findById(nonExistingId);

	    //3º
	    Assertions.assertTrue(result.isEmpty());	
    }


}
