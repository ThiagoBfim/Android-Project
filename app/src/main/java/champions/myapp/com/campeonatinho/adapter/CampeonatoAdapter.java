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
import champions.myapp.com.campeonatinho.model.Campeonato;

/**
 * Created by Usuario on 15/12/2017.
 */

public class CampeonatoAdapter extends ArrayAdapter<Campeonato> {

    private List<Campeonato> contatos;
    private Context context;

    public CampeonatoAdapter(@NonNull Context context, @NonNull List<Campeonato> objects) {
        super(context, 0, objects);
        this.contatos = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;

        if(contatos != null && !contatos.isEmpty() ){
            Campeonato campeonato = contatos.get(position);
            if(campeonato != null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.lista_pontos, parent, false);
                TextView title = view.findViewById(R.id.tv_title);
                title.setText(campeonato.getTitulo());

                TextView subTitle = view.findViewById(R.id.tv_subTtitle);
                subTitle.setText(campeonato.getDescricao());
            }



        }

        return view;
    }
}
