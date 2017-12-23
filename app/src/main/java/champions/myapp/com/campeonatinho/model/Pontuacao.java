package champions.myapp.com.campeonatinho.model;

/**
 * Created by Usuario on 21/12/2017.
 */

public class Pontuacao {

    private String id;
    private String descricao;
    private Integer qtdPontos;

    public Pontuacao() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQtdPontos() {
        return qtdPontos;
    }

    public void setQtdPontos(Integer qtdPontos) {
        this.qtdPontos = qtdPontos;
    }
}
