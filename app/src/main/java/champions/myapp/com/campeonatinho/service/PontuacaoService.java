package champions.myapp.com.campeonatinho.service;

import com.google.firebase.database.DatabaseReference;

import champions.myapp.com.campeonatinho.config.ConfiguracaoFirebase;
import champions.myapp.com.campeonatinho.model.Campeonato;
import champions.myapp.com.campeonatinho.model.Pontuacao;

/**
 * Created by Usuario on 21/12/2017.
 */

public class PontuacaoService {

    public static void salvar(Pontuacao pontuacao, String idCampeonato) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("pontuacoes").child(idCampeonato).push().setValue(pontuacao);
    }

    public static DatabaseReference getPontuacaoDataBaseReference(String idCampeonato) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        return referenciaFirebase.child("pontuacoes").child(idCampeonato);
    }

    public static void alterar(Pontuacao pontuacao, String idCampeonato) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("pontuacoes").child(idCampeonato).child(pontuacao.getId()).setValue(pontuacao);
    }

    public static void remover(Pontuacao pontuacao, String idCampeonato) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("pontuacoes").child(idCampeonato).child(pontuacao.getId()).removeValue();
    }
}
