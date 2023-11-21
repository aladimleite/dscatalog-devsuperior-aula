package com.devsuperior.dscatalog.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.services.ProductService;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

@WebMvcTest(ProductResource.class)
public class ProductResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;
    //0340 Vamos lembrar aqui também pessoal o seguinte: colocamos até aqui no material. Mockito vs MockBean.
    //Você dará preferência para usar @Mock - lá do Mockito independente, vanilla - quando realizamos testes de unidade, 
    //de componentes, services e você está usando @ExtendWith. Ex.: ProductServiceTests.java
    //04 Quando você estiver usando @SpringBootTest que carrega o contexto da aplicação ou @WebMvcTest que carrega só a camada web
    //você dá preferência para o @MockBean. Ele é mais integrado ao spring e vai carregar o contexto (@SpringBootTest) parcialmente 
    //ou integralmente só que ele vai substituir o componente específico que você colocar no @MockBean - neste exemplo ProductService -
    //por um componente mockado.

    //0437 1º teste: chamar o método ProductResource.findAll que recebe um pageable e retorna uma página de ProductDTO.
    //Vamos fazer um teste de unidade simulando o comportamento do service.findAllPaged e o nosso teste vai ter que certificar
    //que realmente tá voltando um objeto do tipo página.

    //0507 Primeira coisa: simular o comportamento do service (@MockBean private ProductService service). 
    //O método findAll do resource chama o findAll do service.

    //0655 Aqui no teste vou ter que usar um tipo concreto de página. Neste caso será PageImpl.
    private PageImpl<ProductDTO> page;
    
    //0800 
    private ProductDTO productDTO;

    //02-32 Testando o findById
    //0107
    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;

    //02-33
    @Autowired
    private ObjectMapper objectMapper;


    //Fixtures - @BeforeAll, @AfterAll, @BeforeEach, @AfterEach: é uma forma de organizar melhor o código dos testes e evitar repetições.
    @BeforeEach //Preparação antes de cada teste da classe 
    void setUp() throws Exception {
        //02-32 Testando o findById
        //0107
        existingId = 1L;
        nonExistingId = 2L;
        dependentId = 3L;

        //0804 
        productDTO = Factory.createProductDTO();

        //0712 Instancio um objeto concreto.
        //Por que estou usando PageImpl e não o Page? Porque o PageImpl aceita o new.
        //A lista conterá pelo menos um produto. O ".of" permite permite que eu instancie uma lista já com um elemento dentro dela.
        //Este elemento vai ser do tipo ProductDTO. //0800 private ProductDTO productDTO;
        page = new PageImpl<>(List.of(productDTO));        

        //0840 Só tá faltando criar um objeto para servir de argumento para findAllPaged.
        //O ProductService.findAllPaged recebe um pageable. Só que no comportamento "mocado"/simulado, não importa o valor.
        //Vamos usar um cara especial aqui: "any()". O "any()" vem do ArgumentMatchers do Mockito.

        //0544 Vamos simular o comportamento do service.findAllPaged.
        //Quando eu chamar o serviço passando como argumento um pageable ele deve retornar uma página.
        //page é do tipo ProductDTO. Page<ProductDTO>
        when(service.findAllPaged(any())).thenReturn(page);

        //02-32 Testando o findById
        //0140
        when(service.findById(existingId)).thenReturn(productDTO);
        when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        //02-33 Testando o update
        when(service.update(eq(existingId), any())).thenReturn(productDTO);
        when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

        //02-34 Simulando outros comportamentos do ProductService
        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
        doThrow(DatabaseException.class).when(service).delete(dependentId);
        
        //02-34 02-35 Exercício testes na camada web
        when(service.insert(any())).thenReturn(productDTO);
    }
    
    //02-34 02-35 Exercício testes na camada web
    @Test
    public void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
       ResultActions result =
               mockMvc.perform(delete("/products/{id}", nonExistingId)
                   .accept(MediaType.APPLICATION_JSON));
       result.andExpect(status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
       ResultActions result =
               mockMvc.perform(delete("/products/{id}", existingId)
                   .accept(MediaType.APPLICATION_JSON));
       result.andExpect(status().isNoContent());
    }

    @Test
    public void insertShouldReturnProductDTOCreated() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
               mockMvc.perform(post("/products")
                   .content(jsonBody)
                   .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        //o $ representa o objeto
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    //02-33 Testando o update: updateShouldReturnProductDTOWhenIdExists e
    //updateShouldReturnNotFoundWhenIdDoesNotExists
    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
               mockMvc.perform(put("/products/{id}", nonExistingId)
                   .content(jsonBody)
                   .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void updateShouldReturnProductDTOWhenIdExists() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(productDTO);

        ResultActions result =
               mockMvc.perform(put("/products/{id}", existingId)
                   .content(jsonBody)
                   .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

        //o $ representa o objeto
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }

    //1034    
    @Test
    public void findAllShouldReturnPage() throws Exception {
        //mockMvc.perform(get("/products")).andExpect(status().isOk());
        
        ResultActions result =
            mockMvc.perform(get("/products")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());

    }

    //02-32 Testando o findById
    //0237
    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception {

        ResultActions result = 
                mockMvc.perform(get("/products/{id}", existingId)
                    .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        //o $ representa o objeto
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.name").exists());
        result.andExpect(jsonPath("$.description").exists());
    }
    
    @Test
    public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {

        ResultActions result = 
                mockMvc.perform(get("/products/{id}", nonExistingId)
                    .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }
    
}
