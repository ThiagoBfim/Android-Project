package champions.myapp.com.campeonatinho.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import champions.myapp.com.campeonatinho.R;
import champions.myapp.com.campeonatinho.activity.view.CampeonatoLayout;
import champions.myapp.com.campeonatinho.activity.view.PontuacaoLayout;
import champions.myapp.com.campeonatinho.adapter.TabAdapter;
import champions.myapp.com.campeonatinho.config.ConfiguracaoFirebase;
import champions.myapp.com.campeonatinho.helper.Preferencias;
import champions.myapp.com.campeonatinho.helper.SlidingTabLayout;
import champions.myapp.com.campeonatinho.model.Campeonato;
import champions.myapp.com.campeonatinho.model.Usuario;
import champions.myapp.com.campeonatinho.model.UsuarioPontuacao;
import champions.myapp.com.campeonatinho.service.CampeonatoService;
import champions.myapp.com.campeonatinho.service.UsuarioPontuacaoService;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth usuarioFirebase;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Campeonatos");
        setSupportActionBar(toolbar);

        slidingTabLayout = findViewById(R.id.stl_tabs);
        viewPager = findViewById(R.id.vp_pagina);

        //Configurar sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.colorAccent));

        //Configurar adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.item_adicionar_pessoa);
        item.setVisible(false);

        MenuItem itemExcluir = menu.findItem(R.id.item_remover_campeonato);
        itemExcluir.setVisible(false);
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
                abrirCadastroCampeonato();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirCadastroCampeonato() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setTitle("Novo Campeonato");
        final CampeonatoLayout campeonatoLayout = new CampeonatoLayout(MainActivity.this);
        alertDialog.setView(campeonatoLayout);
        alertDialog.setCancelable(false);

        //Configura botões
        alertDialog.setPositiveButton("Cadastrar", (dialog, which) -> {

            Campeonato campeonato = new Campeonato();
            String titulo = campeonatoLayout.getTituloText().getText().toString();
            String descricao = campeonatoLayout.getDescricaoText().getText().toString();

            campeonato.setTitulo(titulo);
            campeonato.setDescricao(descricao);

            //Valida se o nome foi digitado
            if (titulo.isEmpty()) {
                Toast.makeText(MainActivity.this, "Preencha o titulo", Toast.LENGTH_LONG).show();
            } else if (descricao.isEmpty()) {
                Toast.makeText(MainActivity.this, "Preencha a descricao", Toast.LENGTH_LONG).show();
            } else {
                //Salvar instância Firebase
                Preferencias preferencias = new Preferencias(MainActivity.this);
                CampeonatoService.salvar(campeonato, preferencias);
            }

        });

        alertDialog.setNegativeButton("Cancelar", (dialog, which) -> {

        });

        alertDialog.create();
        alertDialog.show();

    }


    public void deslogarUsuario() {

        usuarioFirebase.signOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
