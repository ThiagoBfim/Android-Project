package champions.myapp.com.campeonatinho.activity.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import champions.myapp.com.campeonatinho.R;
import champions.myapp.com.campeonatinho.activity.CampeonatoActivity;
import champions.myapp.com.campeonatinho.adapter.PontuacaoAdapter;
import champions.myapp.com.campeonatinho.adapter.UsuarioPontuacaoAdapter;
import champions.myapp.com.campeonatinho.model.Pontuacao;
import champions.myapp.com.campeonatinho.model.UsuarioPontuacao;
import champions.myapp.com.campeonatinho.service.PontuacaoService;

/**
 * Created by Usuario on 21/12/2017.
 */

public class ParticipanteLayout extends LinearLayout {

    private EditText nomeText;
    private Context context;
    private DatabaseReference firebase;
    private ValueEventListener contatosEvent;
    private ListView listView;
    private List<Pontuacao> pontuacoes = new ArrayList<>();
    private ArrayAdapter<Pontuacao> adapter;
    private String idCampeonato;
    private UsuarioPontuacao usuarioPontuacao;

    public ParticipanteLayout(Context context, UsuarioPontuacao usuarioPontuacao, String idCampeonato) {
        super(context);
        this.context = context;
        this.idCampeonato = idCampeonato;
        this.usuarioPontuacao = usuarioPontuacao;
        setOrientation(LinearLayout.VERTICAL);

        nomeText = new EditText(context);
        nomeText.setHint("E-mail do usuário");
        addView(nomeText);

        if(usuarioPontuacao != null){
            listView = new ListView(context);
            adapter = new UsuarioPontuacaoAdapter(context, pontuacoes, usuarioPontuacao);
            listView.setAdapter(adapter);
            addView(listView);

            firebase = PontuacaoService.getPontuacaoDataBaseReference(idCampeonato);
            contatosEvent = getValueContatoEventListener();
            firebase.addValueEventListener(contatosEvent);
            nomeText.setText(usuarioPontuacao.getUsuario().getEmail());
        }

    }

    @NonNull
    private ValueEventListener getValueContatoEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Limpar lista
                pontuacoes.clear();

                //Listar contatos
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Pontuacao pontuacao = dados.getValue(Pontuacao.class);
                    pontuacao.setId(dados.getKey());
                    pontuacoes.add(pontuacao);
                }

                for(Pontuacao p1 : usuarioPontuacao.getPontuacoes()) {
                    for (Pontuacao pontuacao : pontuacoes) {
                        if(p1.equals(pontuacao)){
                            pontuacao.setQtdPontos(p1.getQtdPontos());
                        }
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    public EditText getNomeText() {
        return nomeText;
    }

    public void setNomeText(EditText nomeText) {
        this.nomeText = nomeText;
    }
}
