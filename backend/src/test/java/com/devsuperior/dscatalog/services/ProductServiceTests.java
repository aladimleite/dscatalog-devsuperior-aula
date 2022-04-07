/*
06/04/2022 Aula 02-22 Começando testes de ProductService, Mockito vs MockBean.
Teste de unidade: testa somente uma classe específica sem carregar suas dependências.
Ex.: ProductService depende do ProductRepository e CategoryRepository. 
Então, para eu fazer um teste de unidade do ProductService sem carregar os repositórios
ProductRepository e CategoryRepository vamos "mocar" as dependências. 
Vamos usar o mockito. Temos que fazer isso porque se eu carregar outros componentes dentro 
da classe, por exemplo, ProductRepository, CategoryRepository não seria teste de unidade, 
mas de integração. Queremos só testar o ProductService para ficar um teste isolado e mais 
rápido. Os testes de unidades são importantes para validar um componente específico.
*/
package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        /*
        Configuro comportamentos simulados para o objeto repository mocado.

        @Mock
        private ProductRepository repository;

        12:30 Quando eu chamar o deleteById com Id existente esse método não vai fazer
        nada. 
        */
        Mockito.doNothing().when(repository).deleteById(existingId);

        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
    
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
    
        Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
    }
        
    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> {
            //1º teste
            service.delete(existingId);
            //2º teste - só para ver se está funcionando...
            //service.delete(nonExistingId);
        });
        
        //1º teste
        //Mockito.verify(repository).deleteById(existingId);
        //2º teste
        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
        //3º teste
        //Mockito.verify(repository, Mockito.never()).deleteById(existingId);
        //4º teste
        //Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
    }    
}
