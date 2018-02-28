package champions.myapp.com.campeonatinho.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import champions.myapp.com.campeonatinho.config.ConfiguracaoFirebase;
import champions.myapp.com.campeonatinho.model.Pontuacao;
import champions.myapp.com.campeonatinho.model.UsuarioPontuacao;

/**
 * Created by Usuario on 21/12/2017.
 */

public class UsuarioPontuacaoService {

    public static void salvar(UsuarioPontuacao usuarioPontuacao, String idCampeonato, String identificadorLogado){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarioPontuacao").child(identificadorLogado).child(idCampeonato).push().setValue( usuarioPontuacao );
    }

    public static void remover(String usuarioPontuacaosCod, String idCampeonato, String identificadorLogado) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarioPontuacao").child(identificadorLogado)
                .child(idCampeonato).child(usuarioPontuacaosCod).removeValue();
    }

    public static void removerAll(String idCampeonato, String identificadorLogado) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarioPontuacao").child(identificadorLogado)
                .child(idCampeonato).removeValue();
    }

    public static DatabaseReference getUsuarioPontuacaoDataBaseReference(String idCampeonato, String identificadorLogado) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        return referenciaFirebase.child("usuarioPontuacao").child(identificadorLogado)
                .child(idCampeonato);
    }

    public static void alterar(UsuarioPontuacao usuarioPontuacao, String idCampeonato, String identificadorLogado) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarioPontuacao").child(identificadorLogado)
                .child(idCampeonato).child(usuarioPontuacao.getId()).setValue(usuarioPontuacao);
    }

    public static DatabaseReference getAllCampeonato() {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        return referenciaFirebase.child("usuarioPontuacao");
    }

    public static void removerPontuacao(Pontuacao pontuacao, String idCampeonato, String identificadorLogado) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarioPontuacao").child(identificadorLogado).child(idCampeonato).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Listar contatos
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    UsuarioPontuacao usuarioPontuacao = dados.getValue(UsuarioPontuacao.class);
                    usuarioPontuacao.setId(dados.getKey());

                   if(usuarioPontuacao.getPontuacoes().contains(pontuacao)){
                       usuarioPontuacao.getPontuacoes().remove(pontuacao);
                       remover(usuarioPontuacao.getId(), idCampeonato, identificadorLogado);
                   }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
