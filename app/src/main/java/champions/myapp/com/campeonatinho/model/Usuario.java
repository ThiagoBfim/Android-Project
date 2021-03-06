package champions.myapp.com.campeonatinho.model;

import com.google.firebase.database.Exclude;

public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private Boolean ehAdm = Boolean.FALSE;

    public Usuario() {

    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getEhAdm() {
        return ehAdm;
    }

    public void setEhAdm(Boolean ehAdm) {
        this.ehAdm = ehAdm;
    }
}
