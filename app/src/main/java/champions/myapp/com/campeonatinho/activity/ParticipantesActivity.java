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
import android.widget.EditText;
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
import champions.myapp.com.campeonatinho.activity.view.ParticipanteLayout;
import champions.myapp.com.campeonatinho.activity.view.PontuacaoLayout;
import champions.myapp.com.campeonatinho.adapter.ParticipanteAdapter;
import champions.myapp.com.campeonatinho.config.ConfiguracaoFirebase;
import champions.myapp.com.campeonatinho.helper.Base64Util;
import champions.myapp.com.campeonatinho.helper.Preferencias;
import champions.myapp.com.campeonatinho.model.Campeonato;
import champions.myapp.com.campeonatinho.model.Usuario;
import champions.myapp.com.campeonatinho.model.UsuarioPontuacao;
import champions.myapp.com.campeonatinho.service.CampeonatoService;
import champions.myapp.com.campeonatinho.service.UsuarioPontuacaoService;

public class ParticipantesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth usuarioFirebase;
    private DatabaseReference firebase;
    private ValueEventListener contatosEvent;
    private ListView listView;
    private List<UsuarioPontuacao> usuarioPontuacaos = new ArrayList<>();
    private ArrayAdapter<UsuarioPontuacao> adapter;
    private String idCampeonato;
    private String tituloCampeonato;
    private List<String> idCampeonatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        toolbar = findViewById(R.id.toolbar);
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            tituloCampeonato = extra.getString("titulo");
            idCampeonato = extra.getString("campeonatoId");
        }
        toolbar.setTitle(tituloCampeonato);
        setSupportActionBar(toolbar);

        listView = findViewById(R.id.lv_conversas);
        adapter = new ParticipanteAdapter(ParticipantesActivity.this, usuarioPontuacaos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            UsuarioPontuacao pontuacao = usuarioPontuacaos.get(position);
            abrirCadastroPessoa(pontuacao);

        });

        firebase = UsuarioPontuacaoService.getAllCampeonato();
        contatosEvent = getValueUsuarioPontuacaoEventListener();
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

        MenuItem item = menu.findItem(R.id.item_adicionar);
        item.setVisible(false);

        MenuItem item2 = menu.findItem(R.id.item_remover_campeonato);
        item2.setVisible(false);

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
            case R.id.item_adicionar_pessoa:
                abrirCadastroPessoa(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroPessoa(final UsuarioPontuacao usuarioPontuacao) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ParticipantesActivity.this);

        String labelButton;
        //Configurações do Dialog
        if(usuarioPontuacao == null) {
            labelButton = "Cadastrar";
            alertDialog.setTitle("Novo participante");
        } else {
            alertDialog.setTitle("Editar participante");
            labelButton = "Salvar";
        }
        final ParticipanteLayout participanteLayout = new ParticipanteLayout(ParticipantesActivity.this, usuarioPontuacao, idCampeonato);
        alertDialog.setView(participanteLayout);
        alertDialog.setCancelable(false);

        //Configura botões
        alertDialog.setPositiveButton(labelButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String emailParticipante = participanteLayout.getNomeText().getText().toString();
                if(usuarioPontuacao != null){
                    emailParticipante = usuarioPontuacao.getUsuario().getEmail();
                }
                //Valida se o nome foi digitado
                if (emailParticipante.isEmpty()) {
                    Toast.makeText(ParticipantesActivity.this, "Preencha o e-mail do participante", Toast.LENGTH_LONG).show();
                } else {
                    //Verificar se o usuário já está cadastrado no nosso App
                    String identificadorContato = Base64Util.codificarBase64(emailParticipante);

                    //Recuperar instância Firebase
                    firebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(identificadorContato);

                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() != null) {

                                //Recuperar dados do contato a ser adicionado
                                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                                usuario.setId(dataSnapshot.getKey());


                                if(usuarioPontuacao != null){
                                    usuarioPontuacao.setUsuario(usuario);
                                    UsuarioPontuacaoService.alterar(usuarioPontuacao, idCampeonato, usuario.getId());
                                } else {
                                    UsuarioPontuacao usuarioPontuacaoNovo = new UsuarioPontuacao();
                                    usuarioPontuacaoNovo.setUsuario(usuario);
                                    Campeonato campeonato = new Campeonato();
                                    campeonato.setId(idCampeonato);
                                    campeonato.setTitulo(tituloCampeonato);
                                    usuarioPontuacaoNovo.setCampeonato(campeonato);
                                    UsuarioPontuacaoService.salvar(usuarioPontuacaoNovo, idCampeonato, usuario.getId());
                                }


                            } else {
                                Toast.makeText(ParticipantesActivity.this,
                                        "Usuário não possui cadastro.", Toast.LENGTH_LONG)
                                        .show();
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        if(usuarioPontuacao != null) {
            alertDialog.setNeutralButton("Excluir", (dialog, which) -> {
                Preferencias preferencias = new Preferencias(ParticipantesActivity.this);
                String identificadorLogado = preferencias.getIdentificador();
                UsuarioPontuacaoService.remover(usuarioPontuacao.getId(), idCampeonato,identificadorLogado);
            });
        }

        alertDialog.setNegativeButton("Cancelar", (dialog, which) -> {

        });

        alertDialog.create();
        alertDialog.show();

    }


    public void deslogarUsuario() {

        usuarioFirebase.signOut();

        Intent intent = new Intent(ParticipantesActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @NonNull
    private ValueEventListener getValueUsuarioPontuacaoEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuarioPontuacaos.clear();

                Preferencias preferencias = new Preferencias(ParticipantesActivity.this);
                String identificadorLogado = preferencias.getIdentificador();

                //Listar contatos
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                        for (DataSnapshot campeonatos : dados.getChildren()) {
                            String idCampeonatoRetrived = campeonatos.getKey();
                            if(idCampeonato.equals(idCampeonatoRetrived)) {
                                for (DataSnapshot usuPontuacao : campeonatos.getChildren()) {
                                    UsuarioPontuacao usuarioPontuacao = usuPontuacao.getValue(UsuarioPontuacao.class);
                                    usuarioPontuacao.setId(usuPontuacao.getKey());
                                    usuarioPontuacaos.add(usuarioPontuacao);
                                }
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
}
