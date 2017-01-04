package com.applek.happy.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.applek.happy.Base.BaseFragment;
import com.applek.happy.R;
import com.applek.happy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        String[] str = new String[]{"段子", "趣图", "其他"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str);
        binding.idLv.setAdapter(adapter);
        binding.idLv.setOnItemClickListener(this);

        initFragment();
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle("开心一笑");
        bar.hide();

    }

    private void initFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, new TextFragment()).commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 1:
                ImageFragment imageFragment = new ImageFragment();
                switchContent(imageFragment);
                break;
            case 0:
                switchContent(new TextFragment());
                break;
        }
        binding.drawerLayout.closeDrawer(binding.idDrawer);
    }

    private void switchContent(BaseFragment imageFragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.main_container,imageFragment).commit();
    }


}
