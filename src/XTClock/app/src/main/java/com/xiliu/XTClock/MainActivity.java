package com.xiliu.XTClock;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

import com.xiliu.XTClock.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {

            super.onCreate(savedInstanceState);

            ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());


            setSupportActionBar(binding.appBarMain.toolbar);

            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            assert navHostFragment != null;
            NavController navController = navHostFragment.getNavController();

            // 检查是否存在NavigationView
            if (binding.navView != null) {
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_settings, R.id.nav_about)
                        .setOpenableLayout(binding.drawerLayout)
                        .build();
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
                NavigationUI.setupWithNavController(binding.navView, navController);
            } else {
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_settings, R.id.nav_about)
                        .build();
                NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            }

            // 初始化底部导航视图（如果存在）
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
            if (bottomNavigationView != null) {
                NavigationUI.setupWithNavController(bottomNavigationView, navController);
            }
        }catch(Exception e){
            e.printStackTrace();
        }




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        
        // 处理工具栏菜单项点击
        if (item.getItemId() == R.id.nav_about) {
            navController.navigate(R.id.nav_about);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 处理向上导航
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) 
                || super.onSupportNavigateUp();
    }



}