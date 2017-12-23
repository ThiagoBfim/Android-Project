package champions.myapp.com.campeonatinho.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import champions.myapp.com.campeonatinho.R;
import champions.myapp.com.campeonatinho.activity.view.PontuacaoLayout;
import champions.myapp.com.campeonatinho.adapter.PontuacaoAdapter;
import champions.myapp.com.campeonatinho.config.ConfiguracaoFirebase;
import champions.myapp.com.campeonatinho.model.Pontuacao;
import champions.myapp.com.campeonatinho.service.PontuacaoService;
import champions.myapp.com.campeonatinho.service.UsuarioPontuacaoService;

public class CampeonatoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth usuarioFirebase;
    private DatabaseReference firebase;
    private ValueEventListener contatosEvent;
    private ListView listView;
    private List<Pontuacao> pontuacoes = new ArrayList<>();
    private ArrayAdapter<Pontuacao> adapter;
    private String idCampeonato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campeonato);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        toolbar = findViewById(R.id.toolbar);
        Bundle extra = getIntent().getExtras();
        String nomeCampeonato = "";
        if (extra != null) {
            nomeCampeonato = extra.getString("nome");
            idCampeonato = extra.getString("campeonatoId");
        }
        toolbar.setTitle(nomeCampeonato);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.lv_conversas);
        adapter = new PontuacaoAdapter(CampeonatoActivity.this, pontuacoes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pontuacao pontuacao = pontuacoes.get(position);
                abrirCadastroCampeonato(pontuacao);

            }
        });

        firebase = PontuacaoService.getPontuacaoDataBaseReference(idCampeonato);
        contatosEvent = getValueContatoEventListener();

    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(contatosEvent);
        Log.i("ValueEventListener", "OnStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(contatosEvent);
        Log.i("ValueEventListener", "OnStop");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.item_adicionar_pessoa);
        item.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            case R.id.item_configuracoes:
                return true;
            case R.id.item_adicionar:
                abrirCadastroCampeonato(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroCampeonato(final Pontuacao pontuacao) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CampeonatoActivity.this);

        //Configurações do Dialog
        String labelButton;
        if(pontuacao == null){
            alertDialog.setTitle("Nova Pontuação");
            labelButton = "Cadastrar";
        } else {
            alertDialog.setTitle("Editar Pontuação");
            labelButton = "Salvar";
        }
        final PontuacaoLayout pontuacaoView = new PontuacaoLayout(CampeonatoActivity.this, pontuacao);
        alertDialog.setView(pontuacaoView);
        alertDialog.setCancelable(false);

        //Configura botões
        alertDialog.setPositiveButton(labelButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String descricaoPontuacao = pontuacaoView.getDescricaoText().getText().toString();
                String qtdPontosText = pontuacaoView.getPontuacaoText().getText().toString();

                //Valida se o nome foi digitado
                if( descricaoPontuacao.isEmpty() ){
                    Toast.makeText(CampeonatoActivity.this, "Preencha a descrição da pontuação", Toast.LENGTH_LONG).show();
                }else{
                    if(qtdPontosText.isEmpty()){
                        Toast.makeText(CampeonatoActivity.this, "Preencha a quantidade de pontos", Toast.LENGTH_LONG).show();
                    } else {
                        Integer qtdPontos = 0;
                        try {
                            qtdPontos = Integer.valueOf(qtdPontosText);
                        } catch (NumberFormatException e) {
                            Log.e("Erro ao criar pontuacao", e.getMessage());
                            Toast.makeText(CampeonatoActivity.this, "Quantidade de pontos precisa ser numerica.", Toast.LENGTH_LONG).show();
                        }
                        //Salvar instância Firebase
                        Pontuacao pontuacaoNova = new Pontuacao();
                        pontuacaoNova.setDescricao(descricaoPontuacao);
                        pontuacaoNova.setQtdPontosFixo(qtdPontos);
                        if (pontuacao != null) {
                            pontuacaoNova.setId(pontuacao.getId());
                            PontuacaoService.alterar(pontuacaoNova, idCampeonato);
                        } else {
                            PontuacaoService.salvar(pontuacaoNova, idCampeonato);
                        }
                    }

                }

            }
        });

        if(pontuacao != null) {
            alertDialog.setNeutralButton("Excluir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PontuacaoService.remover(pontuacao, idCampeonato);
                    UsuarioPontuacaoService.removerPontuacao(pontuacao, idCampeonato);
                }
            });
        }

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.create();
        alertDialog.show();

    }


    public void deslogarUsuario() {

        usuarioFirebase.signOut();

        Intent intent = new Intent(CampeonatoActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}
