package champions.myapp.com.campeonatinho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import champions.myapp.com.campeonatinho.R;
import champions.myapp.com.campeonatinho.helper.Preferencias;
import champions.myapp.com.campeonatinho.model.Pontuacao;

/**
 * Created by Usuario on 15/12/2017.
 */

public class PontuacaoAdapter extends ArrayAdapter<Pontuacao> {

    private List<Pontuacao> mensagens;
    private Context context;

    public PontuacaoAdapter(@NonNull Context context, @NonNull List<Pontuacao> objects) {
        super(context, 0, objects);
        this.mensagens = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if (mensagens != null) {

            Pontuacao ponto = mensagens.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_pontos, parent, false);

            TextView pontoDescricao = (TextView) view.findViewById(R.id.tv_title);
            pontoDescricao.setText(ponto.getDescricao());

            TextView qtdPonto = (TextView) view.findViewById(R.id.tv_subTtitle);
            qtdPonto.setText(String.valueOf(ponto.getQtdPontos()));

        }

        return view;
    }
}
