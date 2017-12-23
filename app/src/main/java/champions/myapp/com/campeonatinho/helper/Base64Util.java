package champions.myapp.com.campeonatinho.helper;

import android.util.Base64;

/**
 * Created by Usuario on 13/12/2017.
 */

public class Base64Util {

    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String deCodificarBase64(String textoCodificcado){
        return new String(Base64.decode(textoCodificcado.getBytes(), Base64.DEFAULT));
    }
}
