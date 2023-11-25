package com.devsuperior.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "tb_category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant createdAt;
    
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant updatedAt;

    //Aladim 20231125 - aula 03-04
    @ManyToMany(mappedBy = "categories")
    private Set <Product> products = new HashSet<>();    
    /* mappedBy: fará o mapeamento complementar, ou seja, considera
     * o já existente.
     * //Association entre Product and Categories.
     * @ManyToMany
     * @JoinTable(name = "tb_product_category",
     * joinColumns = @JoinColumn(name = "product_id"),
     * inverseJoinColumns = @JoinColumn(name = "category_id"))
     * Set<Category> categories = new HashSet<>(); 
     */

    public Category() {
    }

    public Category(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    /*public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }*/

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    /*public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }*/
    
    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    //Aladim 20231125 - aula 03-04
    public Set<Product> getProducts() {
        return products;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        Category other = (Category) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;

        return true;
    }

}