package champions.myapp.com.campeonatinho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import champions.myapp.com.campeonatinho.R;
import champions.myapp.com.campeonatinho.model.Pontuacao;
import champions.myapp.com.campeonatinho.model.UsuarioPontuacao;

/**
 * Created by Usuario on 15/12/2017.
 */

public class UsuarioPontuacaoAdapter extends ArrayAdapter<Pontuacao> {

    private List<Pontuacao> mensagens;
    private Context context;
    private UsuarioPontuacao usuarioPontuacao;

    public UsuarioPontuacaoAdapter(@NonNull Context context, @NonNull List<Pontuacao> objects,
                                   UsuarioPontuacao usuarioPontuacao) {
        super(context, 0, objects);
        this.usuarioPontuacao = usuarioPontuacao;
        this.mensagens = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        View view = null;

        if (mensagens != null) {

            Pontuacao ponto = mensagens.get(position);
            final String idPonto = ponto.getId();
            final Integer qtdPontoFixo = ponto.getQtdPontosFixo();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_usuario_pontos, parent, false);
            final TextView pontoDescricao = view.findViewById(R.id.tv_descricao);
            pontoDescricao.setText(ponto.getDescricao());

            final TextView valorPonto = view.findViewById(R.id.valor_pt);
            valorPonto.setText(String.valueOf(ponto.getQtdPontosFixo()));

            final TextView qtdPonto = view.findViewById(R.id.tv_pontos);
            qtdPonto.setText(String.valueOf(ponto.getQtdPontos()));
            Button addButton = view.findViewById(R.id.buttonAdd);
            addButton.setOnClickListener(v ->
                alterarQuantidadePontos(idPonto, pontoDescricao, qtdPonto, qtdPontoFixo, true));

            Button removeButton = view.findViewById(R.id.removeButton);
            removeButton.setOnClickListener(v ->
                alterarQuantidadePontos(idPonto, pontoDescricao, qtdPonto, qtdPontoFixo, false));

        }

        return view;
    }

    private void alterarQuantidadePontos(String idPonto, TextView pontoDescricao, TextView qtdPonto,
                                         Integer qtdPontoFixo, boolean incrementar) {
        final Pontuacao pontuacao = new Pontuacao();
        pontuacao.setId(idPonto);
        pontuacao.setDescricao(pontoDescricao.getText().toString());
        pontuacao.setQtdPontosFixo(qtdPontoFixo);
        Integer qtdPontoAtualizado;
        if(incrementar){
            qtdPontoAtualizado = Integer.parseInt(qtdPonto.getText().toString()) + 1;
        } else {
            qtdPontoAtualizado = Integer.parseInt(qtdPonto.getText().toString()) - 1;
            if(qtdPontoAtualizado < 0){
                qtdPontoAtualizado  = 0;
            }
        }

        pontuacao.setQtdPontos(qtdPontoAtualizado);
        qtdPonto.setText(String.valueOf(qtdPontoAtualizado));
        if(usuarioPontuacao.getPontuacoes().contains(pontuacao)) {
            for(Pontuacao p : usuarioPontuacao.getPontuacoes()){
                if(p.equals(pontuacao)){
                    p.setQtdPontos(pontuacao.getQtdPontos());
                }
            }
        } else {
            usuarioPontuacao.getPontuacoes().add(pontuacao);
        }
    }
}
