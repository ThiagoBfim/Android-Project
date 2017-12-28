package champions.myapp.com.campeonatinho.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import champions.myapp.com.campeonatinho.activity.fragment.CampeonatoFragment;
import champions.myapp.com.campeonatinho.activity.fragment.ParticipantesFragment;
import champions.myapp.com.campeonatinho.model.enuns.TituloAbas;

public class TabAdapter extends FragmentStatePagerAdapter {

    private TituloAbas[] tituloAbas = TituloAbas.values();

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new CampeonatoFragment();
                break;
            case 1:
                fragment = new ParticipantesFragment();
                break;
        }

        return fragment;

    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tituloAbas[position].getDescricao();
    }
}
