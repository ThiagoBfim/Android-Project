package champions.myapp.com.campeonatinho.model;

/**
 * Created by Usuario on 21/12/2017.
 */

public class Pontuacao {

    private String id;
    private String descricao;
    private Integer qtdPontos = 0;
    private Integer qtdPontosFixo = 0;
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

    public Integer getQtdPontosFixo() {
        return qtdPontosFixo;
    }

    public void setQtdPontosFixo(Integer qtdPontosFixo) {
        this.qtdPontosFixo = qtdPontosFixo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pontuacao pontuacao = (Pontuacao) o;

        return getId() != null ? getId().equals(pontuacao.getId()) : pontuacao.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
