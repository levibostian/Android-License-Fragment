package com.artitk.licensefragment.example;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.MenuItem;

import com.artitk.licensefragment.support.v4.LicenseFragmentBase;
import com.artitk.licensefragment.support.v4.ListViewLicenseFragment;
import com.artitk.licensefragment.support.v4.RecyclerViewLicenseFragment;
import com.artitk.licensefragment.support.v4.ScrollViewLicenseFragment;
import com.artitk.licensefragment.model.CustomUI;
import com.artitk.licensefragment.model.License;
import com.artitk.licensefragment.model.LicenseID;
import com.artitk.licensefragment.model.LicenseType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, LicenseFragmentBase.OnAttachedListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static int fragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                fragmentManager.findFragmentById(R.id.navigation_drawer);
        if (savedInstanceState == null) mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

//        ScrollViewLicenseFragment scrollViewLicenseFragment = (ScrollViewLicenseFragment) fragmentManager.findFragmentById(R.id.fragment);
//        scrollViewLicenseFragment.setLog(true);
//        scrollViewLicenseFragment.addLicense(new int[]{LicenseID.PICASSO, LicenseID.STATED_FRAGMENT, LicenseID.GSON});

//        ListViewLicenseFragment listViewLicenseFragment = (ListViewLicenseFragment) fragmentManager.findFragmentById(R.id.fragment);
//
//        listViewLicenseFragment.setLog(true);
//        listViewLicenseFragment.addLicense(new int[]{LicenseID.PICASSO, LicenseID.STATED_FRAGMENT, LicenseID.GSON});
//        listViewLicenseFragment.withLicenseChain(false);
//
//        ArrayList<License> licenses = new ArrayList<>();
//        licenses.add(new License(this, "Title", LicenseType.BSD_3_CLAUSE, "YEAR", "OWNER"));
//        listViewLicenseFragment.addCustomLicense(licenses);

//        RecyclerViewLicenseFragment recyclerViewLicenseFragment = (RecyclerViewLicenseFragment) fragmentManager.findFragmentById(R.id.fragment);
//
//        recyclerViewLicenseFragment.setLog(true);
//        recyclerViewLicenseFragment.addLicense(new int[]{LicenseID.PICASSO, LicenseID.STATED_FRAGMENT, LicenseID.GSON});
//        recyclerViewLicenseFragment.withLicenseChain(false);
//
//        ArrayList<License> licenses = new ArrayList<>();
//        licenses.add(new License(this, "Title", LicenseType.BSD_3_CLAUSE, "YEAR", "OWNER"));
//        recyclerViewLicenseFragment.addCustomLicense(licenses);
//        recyclerViewLicenseFragment.setCustomUI(new CustomUI().setTitleBackgroundColor(Color.RED));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
//        if (true) return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;

        ArrayList<Integer> licenseIds = new ArrayList<>();
        licenseIds.add(LicenseID.GSON);
        licenseIds.add(LicenseID.RETROFIT);

        switch (position) {
            case 0:
                if (fragmentManager.findFragmentById(R.id.container) instanceof ScrollViewLicenseFragment) return;
                fragment = ScrollViewLicenseFragment.newInstance(licenseIds);   // Call newInstance() using parameter ArrayList<Integer>
                break;
            case 1:
                if (fragmentManager.findFragmentById(R.id.container) instanceof ListViewLicenseFragment) return;
                fragment = ListViewLicenseFragment.newInstance(new int[]{LicenseID.PICASSO}) // Call newInstance() using parameter array
                        .withLicenseChain(false);                                               // Disable license chain
                break;
            case 2:
                if (fragmentManager.findFragmentById(R.id.container) instanceof RecyclerViewLicenseFragment) return;
                ArrayList<License> licenses = new ArrayList<>();
                licenses.add(new License(this, "Test Library 1", LicenseType.MIT_LICENSE, "2000-2001", "Test Owner 1"));
                licenses.add(new License(this, "Test Library 2", LicenseType.GPL_30, "2002", "Test Owner 2"));
                licenses.add(new License(this, "Test Library 3", LicenseType.EPL_10, "2003", "Test Owner 3"));
                licenses.add(new License(this, "Custom License 1", R.raw.wtfpl, "2004", "Test Owner 3"));
                licenses.add(new License(this, "Custom License 2", R.raw.x11, "2005", "Test Owner 4"));
                fragment = RecyclerViewLicenseFragment.newInstance()    // Call newInstance() using without parameter
                        .setLog(true)                                   // Enable Log
                        .withLicenseChain(true)                         // Enable license chain (default)
                        .addLicense(new int[] { LicenseID.PICASSO })    // Add array (same call newInstance)
                        .addLicense(licenseIds)                         // Add ArrayList<Integer> (same call newInstance)
                        .addCustomLicense(licenses)                     // Add Custom License
                        .setCustomUI(new CustomUI()                     // Set Custom UI
                                .setTitleBackgroundColor(Color.parseColor("#7fff7f"))
                                .setTitleTextColor(getResources().getColor(android.R.color.holo_green_dark))
                                .setLicenseBackgroundColor(Color.rgb(127, 223, 127))
                                .setLicenseTextColor(Color.DKGRAY));
                break;
            default:
                return;
        }

//        ((LicenseFragmentBase) fragment).setLog(true);

        // update the main content by replacing fragments
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        fragmentId = position + 1;
    }

    @Override
    public void onAttached() {
        switch (fragmentId) {
            case 1: mTitle = getString(R.string.title_section1); break;
            case 2: mTitle = getString(R.string.title_section2); break;
            case 3: mTitle = getString(R.string.title_section3); break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) return;

//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_url)));
            startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
