package champions.myapp.com.campeonatinho.service;

import com.google.firebase.database.DatabaseReference;

import champions.myapp.com.campeonatinho.config.ConfiguracaoFirebase;
import champions.myapp.com.campeonatinho.model.Campeonato;
import champions.myapp.com.campeonatinho.model.Usuario;

/**
 * Created by Usuario on 21/12/2017.
 */

public class CampeonatoService {

    public static void salvar(Campeonato campeonato, String identificador) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("campeonatos").child(identificador).push().setValue(campeonato);
    }

    public static DatabaseReference getCampeonatosDataBaseReference(String identificador) {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        return referenciaFirebase.child("campeonatos").child(identificador);
    }
}
