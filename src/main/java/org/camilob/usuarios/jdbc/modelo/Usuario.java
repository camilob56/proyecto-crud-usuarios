package org.camilob.usuarios.jdbc.modelo;

public class Usuario {

    private Long id;
    private String username;
    private String password;
    private String email;

    public Usuario() {
    }

    public Usuario(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getId())
                .append(" | ")
                .append(getUsername())
                .append(" | ")
                .append(getPassword())
                .append(" | ")
                .append(getEmail());
        return sb.toString();
    }
}
