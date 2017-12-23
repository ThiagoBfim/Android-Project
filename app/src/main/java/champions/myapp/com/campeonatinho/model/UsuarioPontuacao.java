package champions.myapp.com.campeonatinho.model;

import java.util.List;

/**
 * Created by Usuario on 23/12/2017.
 */

public class UsuarioPontuacao {

    private String id;
    private Usuario usuario;
    private List<Pontuacao> pontuacoes;
    private Integer qtdTotalPontos = 0;

    public UsuarioPontuacao() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Pontuacao> getPontuacoes() {
        return pontuacoes;
    }

    public void setPontuacoes(List<Pontuacao> pontuacoes) {
        this.pontuacoes = pontuacoes;
    }

    public Integer getQtdTotalPontos() {
        return qtdTotalPontos;
    }

    public void setQtdTotalPontos(Integer qtdTotalPontos) {
        this.qtdTotalPontos = qtdTotalPontos;
    }
}
