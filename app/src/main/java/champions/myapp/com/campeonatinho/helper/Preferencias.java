package champions.myapp.com.campeonatinho.helper;

import android.content.Context;
import android.content.SharedPreferences;

import champions.myapp.com.campeonatinho.model.Usuario;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private static final String NOME_ARQUIVO = "whatsapp.preferencias";
    private static final int MODE = 0;
    private SharedPreferences.Editor editor;

    private static final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private static final String CHAVE_NOME = "nomeUsuario";
    private static final String CHAVE_EMAIL = "emailUsuario";

    public Preferencias(Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE );
        editor = preferences.edit();

    }

    public void salvarDados(Usuario usuario){
        editor.putString(CHAVE_IDENTIFICADOR, usuario.getId());
        editor.putString(CHAVE_NOME, usuario.getNome());
        editor.putString(CHAVE_EMAIL, usuario.getEmail());

        editor.commit();
    }

     public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNome() {
        return preferences.getString(CHAVE_NOME, null);
    }

    public String getEmail() {
        return preferences.getString(CHAVE_EMAIL, null);
    }


}
