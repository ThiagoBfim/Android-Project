package champions.myapp.com.campeonatinho.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import champions.myapp.com.campeonatinho.R;
import champions.myapp.com.campeonatinho.activity.ParticipantesActivity;
import champions.myapp.com.campeonatinho.adapter.CampeonatoAdapter;
import champions.myapp.com.campeonatinho.helper.Preferencias;
import champions.myapp.com.campeonatinho.model.Campeonato;
import champions.myapp.com.campeonatinho.model.UsuarioPontuacao;
import champions.myapp.com.campeonatinho.service.UsuarioPontuacaoService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParticipantesFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Campeonato> campeonatoes = new ArrayList<>();
    private DatabaseReference firebase;
    private ValueEventListener usuarioPontuacaoEvent;
    private List<String> idCampeonatos = new ArrayList<>();

    public ParticipantesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(usuarioPontuacaoEvent);
        Log.i("ValueEventListener", "OnStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(usuarioPontuacaoEvent);
        Log.i("ValueEventListener", "OnStop");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_campeonato, container, false);
        listView = view.findViewById(R.id.lv_campeonato);

        firebase = UsuarioPontuacaoService.getAllCampeonato();
        usuarioPontuacaoEvent = getValueUsuarioPontuacaoEventListener();
        adapter = new CampeonatoAdapter(getActivity(), campeonatoes);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getActivity(), ParticipantesActivity.class);

            Campeonato campeonato = campeonatoes.get(position);
            intent.putExtra("titulo", campeonato.getTitulo());
            intent.putExtra("campeonatoId", campeonato.getId());

            startActivity(intent);
        });

        return view;
    }

    @NonNull
    private ValueEventListener getValueUsuarioPontuacaoEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                campeonatoes.clear();


                Preferencias preferencias = new Preferencias(getActivity());
                String identificadorLogado = preferencias.getIdentificador();

                //Listar contatos
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    String idUsuario = dados.getKey();
                    if (idUsuario.equals(identificadorLogado)) {
                        for (DataSnapshot campeonatos : dados.getChildren()) {
                            String idCampeonato = campeonatos.getKey();
                            if(idUsuario.equals(identificadorLogado) || idCampeonatos.contains(idCampeonato)) {
                                idCampeonatos.add(idCampeonato);
                                for (DataSnapshot usuPontuacao : campeonatos.getChildren()) {
                                    UsuarioPontuacao usuarioPontuacao = usuPontuacao.getValue(UsuarioPontuacao.class);
                                    campeonatoes.add(usuarioPontuacao.getCampeonato());
                                }
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
