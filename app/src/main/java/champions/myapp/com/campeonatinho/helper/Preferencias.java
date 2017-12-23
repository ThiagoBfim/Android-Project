package champions.myapp.com.campeonatinho.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private static final String NOME_ARQUIVO = "whatsapp.preferencias";
    private static final int MODE = 0;
    private SharedPreferences.Editor editor;

    private static final String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";
    private static final String CHAVE_NOME = "nomeUsuarioLogado";

    public Preferencias(Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE );
        editor = preferences.edit();

    }

    public void salvarDados(String identificadorUsuario, String nome){
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.putString(CHAVE_NOME, nome);
        editor.commit();
    }

    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

    public String getNome() {
        return preferences.getString(CHAVE_NOME, null);
    }

}
