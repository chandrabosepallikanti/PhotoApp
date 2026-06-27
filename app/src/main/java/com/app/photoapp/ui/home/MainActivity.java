package com.app.photoapp.ui.home;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.app.photoapp.R;
import com.app.photoapp.dialogs.ExitBottomSheetDialog;
import com.app.photoapp.ui.creations.CreationsFragment;
import com.app.photoapp.ui.favourites.FavouritesFragment;
import com.app.photoapp.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private HomeFragment homeFragment;
    private FavouritesFragment favouritesFragment;
    private SettingsFragment settingsFragment;
    private CreationsFragment creationsFragment;
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);

        homeFragment = new HomeFragment();
        favouritesFragment = new FavouritesFragment();
        settingsFragment = new SettingsFragment();
        creationsFragment = new CreationsFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, homeFragment, "home")
                    .add(R.id.fragment_container, favouritesFragment, "favourites")
                    .hide(favouritesFragment)
                    .add(R.id.fragment_container, settingsFragment, "settings")
                    .hide(settingsFragment)
                    .add(R.id.fragment_container, creationsFragment, "creations")
                    .hide(creationsFragment)
                    .commit();
            activeFragment = homeFragment;
        }

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                switchFragment(homeFragment);
                return true;
            } else if (id == R.id.nav_favourites) {
                switchFragment(favouritesFragment);
                return true;
            } else if (id == R.id.nav_settings) {
                switchFragment(settingsFragment);
                return true;
            } else if (id == R.id.nav_creations) {
                switchFragment(creationsFragment);
                return true;
            }
            return false;
        });
    }

    private void switchFragment(Fragment target) {
        if (activeFragment == target) return;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .hide(activeFragment)
                .show(target)
                .commit();
        activeFragment = target;
    }

    public void switchToHome() {
        bottomNav.setSelectedItemId(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        ExitBottomSheetDialog dialog = new ExitBottomSheetDialog();
        dialog.show(getSupportFragmentManager(), "exit_dialog");
    }
}
