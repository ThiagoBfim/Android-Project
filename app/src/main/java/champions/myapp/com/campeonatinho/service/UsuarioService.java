package champions.myapp.com.campeonatinho.service;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

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

    public static DatabaseReference retrieveUsuarioById(String idUsuario){
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        return referenciaFirebase.child("usuarios").child(idUsuario);
    }
}
