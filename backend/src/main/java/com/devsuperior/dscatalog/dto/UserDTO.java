package com.devsuperior.dscatalog.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

//import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;

public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Campo obrigatório")
    private String firstName;
    private String lastName;
    @Email(message = "Favor entrar um email válido")
    private String email;
    //private String password; //20231126 aula 03-07 foi criado o objeto UserInsertDTO.java 

    Set<RoleDTO> roles = new HashSet<>();
    
    public UserDTO(){        
    }

    public UserDTO(Long id, String firstName, String lastName, String email/*, String password*/) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        //this.password = password;
    }

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        //this.password = entity.getPassword();

        //1153
        //Association entre User and Role.
        //@ManyToMany(fetch = FetchType.EAGER) //Força que se traga os papéis/perfis "pendurado" a um usuário na banco.
                                               //Trata-se de uma exigência do Spring Security na auttenticação.           
        //Só foi possível fazer assim porque forçamos no mapeamento da entidade User o FetchType.EAGER.
        entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role))); //roles = papéis/perfis Ex.: ROLE_OPERATOR, ROLE_ADMIN
    }

    /*
    public UserDTO(User entity, Set<Role> roles) { //roles = papéis/perfis Ex.: ROLE_OPERATOR, ROLE_ADMIN
        this(entity); 
        roles.forEach(role -> this.roles.add(new RoleDTO(role)));
    }
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() { //roles = papéis/perfis Ex.: ROLE_OPERATOR, ROLE_ADMIN
        return roles;
    }        
}
