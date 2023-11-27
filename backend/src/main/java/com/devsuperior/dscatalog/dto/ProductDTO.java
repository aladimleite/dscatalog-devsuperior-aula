package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    @Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
    @NotBlank(message = "Campo requerido")
    private String name;    
    @NotBlank(message = "Campo requerido")
    private String description;

    @Positive(message = "Preço deve ser um valor positivo")
    private Double price;
    private String imgUrl; 

    @PastOrPresent(message = "A data do produto não pode ser futura")   
    private Instant date;
    
    //um produto pode aceitar uma lista de categorias.
    private List<CategoryDTO> categories = new ArrayList<>();

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getDate() {
        return this.date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<CategoryDTO> getCategories() {
        return this.categories;
    }

    /*
    public List<CategoryDTO> getCategories() {
        return categories;
    }    
    */
    
    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }    
    
    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date/*, List<CategoryDTO> categories*/) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
        /*this.categories = categories;*/
    }

    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();        
    }

    public ProductDTO(Product entity, Set<Category> categories) {
        this(entity); //chama o construtor que recebe a categoria como parâmetro.
        categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
    }

}
