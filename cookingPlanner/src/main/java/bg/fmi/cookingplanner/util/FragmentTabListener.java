package bg.fmi.cookingplanner.util;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import bg.fmi.cookingplanner.R;

/**
 * Created by krasimira on 15-7-26.
 */
public class FragmentTabListener implements ActionBar.TabListener {

        Fragment fragment;
        android.support.v4.app.FragmentManager fragmentManager;

        public FragmentTabListener (Fragment fragment,
            android.support.v4.app.FragmentManager fragmentManager) {
            this.fragment = fragment;
            this.fragmentManager = fragmentManager;
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }
}