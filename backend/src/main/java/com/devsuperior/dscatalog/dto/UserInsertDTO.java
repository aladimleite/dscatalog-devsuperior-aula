package com.devsuperior.dscatalog.dto;

//20231126 aula 03-07 CRUD de usuários PARTE2
public class UserInsertDTO extends UserDTO {
    private static final long serialVersionUID = 1L;

    private String password;

    UserInsertDTO() {
        super();
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    
}
