package ar.edu.unq.uis.domino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;


import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.uis.domino.model.Pedido;
import retrofit2.Call;

/**
 * Created by Carla Sarappa on 29/11/2017.
 */

public class AdminActivity extends PedidosActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_admin;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new PedidosListoParaEnviarFragment(), "Listo Para Enviar");
        adapter.addFragment(new PedidosEnViajeFragment(), "En Viaje");
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static class PedidosEnViajeFragment extends PedidosFragment{
        @Override
        protected Call<List<Pedido>> getPedidos() {
            return service.getPedidos("EnViaje");
        }
    }

    public static class PedidosListoParaEnviarFragment extends PedidosFragment{
        @Override
        protected Call<List<Pedido>> getPedidos() {
            return service.getPedidos("ListoParaEnviar");
        }
    }

    // https://github.com/chrisbanes/cheesesquare
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
