package champions.myapp.com.campeonatinho.service;

import com.google.firebase.database.DatabaseReference;

import champions.myapp.com.campeonatinho.config.ConfiguracaoFirebase;
import champions.myapp.com.campeonatinho.model.UsuarioPontuacao;

/**
 * Created by Usuario on 21/12/2017.
 */

public class UsuarioPontuacaoService {

    public static void salvar(UsuarioPontuacao usuarioPontuacao, String idCampeonato){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarioPontuacao").child(idCampeonato).push().setValue( usuarioPontuacao );
    }

    public static void remover(UsuarioPontuacao usuarioPontuacaos, String idCampeonato) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarioPontuacao").child(idCampeonato).child(usuarioPontuacaos.getId()).removeValue();
    }

    public static DatabaseReference getUsuarioPontuacaoDataBaseReference(String idCampeonato) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        return referenciaFirebase.child("usuarioPontuacao").child(idCampeonato);
    }
}
