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

    public Preferencias(Context contextoParametro){

        contexto = contextoParametro;
        preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE );
        editor = preferences.edit();

    }

    public void salvarDados(String identificadorUsuario){
        editor.putString(CHAVE_IDENTIFICADOR, identificadorUsuario);
        editor.commit();
    }

    public String getIdentificador() {
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }


}
