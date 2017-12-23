package champions.myapp.com.campeonatinho.service;

import com.google.firebase.database.DatabaseReference;

import champions.myapp.com.campeonatinho.config.ConfiguracaoFirebase;
import champions.myapp.com.campeonatinho.model.Usuario;

/**
 * Created by Usuario on 21/12/2017.
 */

public class UsuarioService {

    public static void salvar(Usuario usuario){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child("usuarios").child( usuario.getId() ).setValue( usuario );
    }
}
