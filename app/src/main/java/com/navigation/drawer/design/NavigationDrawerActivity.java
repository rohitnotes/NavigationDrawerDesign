package com.navigation.drawer.design;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //******* tool bar variable**********
    private Toolbar toolbar;
    public static ActionBar actionbar;

    //******* drawer other variable *******
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    //******* index to identify current navigation menu item *******
    public static int navigationItemIndex = 0;

    //******* drawer header variable *********
    private View navigationDrawerHeaderView;
    public TextView userName,userEmail;
    public CircleImageView userImage;

    //******* floating action button variable *********
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        setupToolbar();     //***** call setupToolbar Function ********
        setupDrawer();      //***** call setupDrawer Function ********
        setupHeader();      //***** call setupHeader Function ********

        //********* Floating Button *********
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Welcome To First Navigation Drawer", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //***** set default fragment ********
        refreshFragment(new HomeFragment());
        setActionBarTitle("Home");
    }

    //************* setup toolbar ****************
    private void setupToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        assert actionbar != null;
    }
    //************* end ****************

    //************* setup drawer ****************
    private void setupDrawer()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView)
            {

            }
            @Override
            public void onDrawerOpened(View drawerView)
            {

            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    //************* end ****************

    //************* setup header ****************
    private void setupHeader()
    {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationDrawerHeaderView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        userImage    = navigationDrawerHeaderView.findViewById(R.id.userProfileImage);
        userName     = navigationDrawerHeaderView.findViewById(R.id.userName);
        userEmail    =navigationDrawerHeaderView.findViewById(R.id.userEmail);

        navigationDrawerHeaderView.findViewById(R.id.header).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getApplicationContext(), "Header Click", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }
    //************* end ****************

    //*************** event if drawer item is click ****************
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.action_navigation_home:
                navigationItemIndex = 0;
                HomeFragment homeFragment= new HomeFragment();
                refreshFragment(homeFragment);
                break;
            case R.id.action_navigation_gallery:
                navigationItemIndex = 1;
                GalleryFragment galleryFragment= new GalleryFragment();
                setFragment(galleryFragment);
                setActionBarTitle("Gallery");
                break;
            case R.id.action_navigation_slideshow:
                navigationItemIndex = 2;
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_navigation_tools:
                navigationItemIndex = 3;
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_navigation_share:
                navigationItemIndex = 4;
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_navigation_send:
                navigationItemIndex = 5;
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_LONG).show();
                break;
            default:
                navigationItemIndex = 0;
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    //************* end ****************

    //*************** set fragment method ****************
    public void setFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        //*************** show or hide the floatingActionButton ****************
        floatingActionButton();
    }
    //****** end ****************

    //*************** refresh fragment method ****************
    public void refreshFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        setActionBarTitle("Home");

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START))
        {
                this.drawerLayout.closeDrawer(GravityCompat.START);
        }

        //*************** show or hide the floatingActionButton ****************
        floatingActionButton();
    }
    //****** end ****************

    //****** get selected menu ****************
    private void getSelectedNavigationMenu()
    {
        navigationView.getMenu().getItem(navigationItemIndex).setChecked(true);
    }
    //****** end ****************

    //****** set action bar title ****************
    public void setActionBarTitle(String title)
    {
        actionbar.setTitle(title);
    }
    //****** end ****************

    //****** show or hide the floatingActionButton ****************
    private void floatingActionButton()
    {
        if (navigationItemIndex == 0)
            floatingActionButton.show();
        else
            floatingActionButton.hide();
    }
    //****** end ****************

    //******* main menu **********
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_main_settings:
                Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
                break;
            case R.id.action_main_logout:
                Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //******* main menu end **********

    //*************** if all fragment is finish ****************
    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(fragments == 1)
        {
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(this).inflate(R.layout.exit_alert_dialog, viewGroup, false);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogView);
            builder.setCancelable(false);
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            Button no = dialogView.findViewById(R.id.no_button);
            Button yes =dialogView.findViewById(R.id.yes_button);

            no.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    alertDialog.dismiss();
                }
            });

            yes.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    alertDialog.dismiss();
                    NavigationDrawerActivity.this.finish();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
    //****** end ****************
}