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

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;
    
}
