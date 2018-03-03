package champions.myapp.com.campeonatinho.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import champions.myapp.com.campeonatinho.R;
import champions.myapp.com.campeonatinho.activity.CampeonatoActivity;
import champions.myapp.com.campeonatinho.adapter.CampeonatoAdapter;
import champions.myapp.com.campeonatinho.helper.Preferencias;
import champions.myapp.com.campeonatinho.model.Campeonato;
import champions.myapp.com.campeonatinho.service.CampeonatoService;

/**
 * A simple {@link Fragment} subclass.
 */
public class CampeonatoFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Campeonato> campeonatoes = new ArrayList<>();
    private DatabaseReference firebase;
    private ValueEventListener campeonatoEvent;

    public CampeonatoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(campeonatoEvent);
        Log.i("ValueEventListener", "OnStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(campeonatoEvent);
        Log.i("ValueEventListener", "OnStop");
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_campeonato, container, false);
        listView = view.findViewById(R.id.lv_campeonato);

        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorLogado = preferencias.getIdentificador();
        firebase = CampeonatoService.getCampeonatosDataBaseReference(identificadorLogado);
        campeonatoEvent = getValueCampeonatoEventListener();


        adapter = new CampeonatoAdapter(getActivity(), campeonatoes);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CampeonatoActivity.class);
                Campeonato campeonato = campeonatoes.get(position);
                intent.putExtra("nome", campeonato.getTitulo());
                intent.putExtra("campeonatoId", campeonato.getId());

                startActivity(intent);
            }
        });

        return view;
    }

    @NonNull
    private ValueEventListener getValueCampeonatoEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                campeonatoes.clear();

                //Listar contatos
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Campeonato campeonato = dados.getValue(Campeonato.class);
                    campeonato.setId(dados.getKey());
                    campeonatoes.add(campeonato);
                }


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

}
