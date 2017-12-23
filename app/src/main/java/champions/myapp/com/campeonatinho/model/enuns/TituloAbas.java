package champions.myapp.com.campeonatinho.model.enuns;

/**
 * Created by Usuario on 23/12/2017.
 */

public enum TituloAbas {

    CAMPEONATO(1, "Campeonato"),
    PARTICIPANTES(2, "Participantes");

    private String descricao;
    private Integer codigo;

    TituloAbas(int codigo, String descricao) {
        this.descricao = descricao;
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }
}
