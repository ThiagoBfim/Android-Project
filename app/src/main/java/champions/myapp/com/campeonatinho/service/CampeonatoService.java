package champions.myapp.com.campeonatinho.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import champions.myapp.com.campeonatinho.config.ConfiguracaoFirebase;
import champions.myapp.com.campeonatinho.helper.Preferencias;
import champions.myapp.com.campeonatinho.model.Campeonato;
import champions.myapp.com.campeonatinho.model.Usuario;
import champions.myapp.com.campeonatinho.model.UsuarioPontuacao;

/**
 * Created by Usuario on 21/12/2017.
 */

public class CampeonatoService {

    public static void salvar(Campeonato campeonato, Preferencias preferencias) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("campeonatos").child(preferencias.getIdentificador()).push().setValue(campeonato);

        getCampeonatosDataBaseReference(preferencias.getIdentificador())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot dados: dataSnapshot.getChildren()){
                            Campeonato campeonatoRetrived = dados.getValue( Campeonato.class);
                            if(campeonatoRetrived.getNome().equals(campeonato.getNome())){
                                UsuarioPontuacao usuarioPontuacao = new UsuarioPontuacao();
                                campeonato.setId(dados.getKey());
                                usuarioPontuacao.setCampeonato(campeonato);
                                Usuario usuario = new Usuario();
                                usuario.setId(preferencias.getIdentificador());
                                usuario.setNome(preferencias.getNome());
                                usuario.setEmail(preferencias.getEmail());
                                usuarioPontuacao.setUsuario(usuario);
                                referenciaFirebase.child("usuarioPontuacao").child(preferencias.getIdentificador())
                                        .child(dados.getKey()).push().setValue( usuarioPontuacao );
                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public static DatabaseReference getCampeonatosDataBaseReference(String identificador) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        return referenciaFirebase.child("campeonatos").child(identificador);
    }
}
