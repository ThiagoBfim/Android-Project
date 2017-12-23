package champions.myapp.com.campeonatinho.activity.view;

import android.content.Context;
import android.view.ContextMenu;
import android.widget.EditText;
import android.widget.LinearLayout;

import champions.myapp.com.campeonatinho.activity.CampeonatoActivity;
import champions.myapp.com.campeonatinho.model.Pontuacao;

/**
 * Created by Usuario on 21/12/2017.
 */

public class PontuacaoLayout extends LinearLayout {

    private EditText descricaoText;
    private EditText pontuacaoText;
    private Context context;

    public PontuacaoLayout(Context context, Pontuacao pontuacao) {
        super(context);
        this.context = context;
        setOrientation(LinearLayout.VERTICAL);

        descricaoText = new EditText(context);
        descricaoText.setHint("Descricao da pontuação");
        addView(descricaoText);


        pontuacaoText = new EditText(context);
        pontuacaoText.setHint("Valor da pontuação");
        addView(pontuacaoText);


        if(pontuacao != null){
            descricaoText.setText(pontuacao.getDescricao());
            pontuacaoText.setText(String.valueOf(pontuacao.getQtdPontos()));
        }

    }


    public EditText getDescricaoText() {
        return descricaoText;
    }

    public void setDescricaoText(EditText descricaoText) {
        this.descricaoText = descricaoText;
    }

    public EditText getPontuacaoText() {
        return pontuacaoText;
    }

    public void setPontuacaoText(EditText pontuacaoText) {
        this.pontuacaoText = pontuacaoText;
    }
}
