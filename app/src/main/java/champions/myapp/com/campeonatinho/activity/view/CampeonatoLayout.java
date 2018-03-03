package champions.myapp.com.campeonatinho.activity.view;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import champions.myapp.com.campeonatinho.model.Campeonato;
import champions.myapp.com.campeonatinho.model.Pontuacao;

/**
 * Created by Usuario on 21/12/2017.
 */

public class CampeonatoLayout extends LinearLayout {

    private EditText descricaoText;
    private EditText tituloText;
    private Context context;

    public CampeonatoLayout(Context context) {
        super(context);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);

        tituloText = new EditText(context);
        tituloText.setHint("Nome do Campeonato");
        addView(tituloText);


        descricaoText = new EditText(context);
        descricaoText.setHint("Descricao do campeonato");
        addView(descricaoText);

    }

    public EditText getDescricaoText() {
        return descricaoText;
    }

    public void setDescricaoText(EditText descricaoText) {
        this.descricaoText = descricaoText;
    }

    public EditText getTituloText() {
        return tituloText;
    }

    public void setTituloText(EditText tituloText) {
        this.tituloText = tituloText;
    }
}
