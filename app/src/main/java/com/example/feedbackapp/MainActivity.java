package com.example.feedbackapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.feedbackapp.Common.AccountManager;

import com.example.feedbackapp.Common.Connectivity;
import com.example.feedbackapp.Common.ShowLoader;
import com.example.feedbackapp.Common.Utility;
import com.example.feedbackapp.Interface.FragmentBackPressed;
import com.example.feedbackapp.ThirdParty.Permissions.helper.PermissionHelper;
import com.example.feedbackapp.activity.FilterListByDateActivity;
import com.example.feedbackapp.fragment.FeedbackFragment;
import com.example.feedbackapp.fragment.HomeFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
                                NavigationView.OnNavigationItemSelectedListener

{

    boolean Navigationflag = true;

    public FragmentBackPressed backPressedInterface;

    //Permission Related Setup
    private PermissionHelper.PermissionBuilder permissionRequest;
    public static ArrayList<String> permissions = new ArrayList<String>();
    public static final int requestCode = 1;
    public static final int REQUEST_PERMISSION_SETTING = 101;
    public static boolean sentToSettings = false;

    //Drawer Related
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private AccountManager accountManager;
    private CoordinatorLayout coordinatorLayout;
    public Toolbar toolbar;
    String username, password;
    private TextView txtUserName, txtUserEmailAddress, txtUserMobileDrawer;
    private boolean flagForOptionMenu = false;
    private ImageView imgUserAvatar;
    private long back_pressed;
    private MenuItem menuItem;

    //Database Related
    private SQLiteDatabase database;

    //API Related

    private ShowLoader showLoader;


    private FrameLayout frameLayout;


    private String appLink;
    private boolean flagForUptoDate = false;
    private RecyclerView recyclerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = getLayoutInflater().inflate(R.layout.activity_main, null);

        Utility.overrideFonts(this, view);

        setContentView(view);

        initViews();

    }


    private void initViews() {


        accountManager = new AccountManager(this);
        //progress wheel Initialization
        showLoader = new ShowLoader(MainActivity.this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.home));
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        txtUserName = (TextView) headerView.findViewById(R.id.userNameDrawer);
        txtUserEmailAddress = (TextView) headerView.findViewById(R.id.userEmailDrawer);
        txtUserMobileDrawer = (TextView) headerView.findViewById(R.id.userMobileDrawer);
        imgUserAvatar = (ImageView) headerView.findViewById(R.id.imgUserAvatar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener()
        {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset)
            {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView)
            {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView)
            {

            }

            @Override
            public void onDrawerStateChanged(int newState)
            {
                Utility.hideSoftKeyboard(MainActivity.this);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        toggle.syncState();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContentRoot, new HomeFragment())
                .addToBackStack(null)
                .commit();



    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        Fragment fragment = null;
        Fragment tempFragment = null;
        boolean flagToAddFragment = true;
        menuItem.setChecked(true);
        switch (menuItem.getItemId())
        {

            case R.id.feedback:

                Navigationflag = true;
                flagForOptionMenu = false;

                fragment = new FeedbackFragment();
                tempFragment = getCurrentFragment();
                if (tempFragment != null)
                {
                    if (tempFragment instanceof FeedbackFragment)
                    {
                        flagToAddFragment = false;
                    }
                }





            break;



            case R.id.report:

                Intent reportIntent = new Intent(MainActivity.this,FilterListByDateActivity.class);
                startActivity(reportIntent);


                break;

           /* case R.id.add_appointment:

                if(Connectivity.isConnected(MainActivity.this)){

                    Navigationflag = true;
                    flagForOptionMenu = false;

                    fragment = new AddAppointmentFragment();
                    tempFragment = getCurrentFragment();
                    if (tempFragment != null)
                    {
                        if (tempFragment instanceof AddAppointmentFragment)
                        {
                            flagToAddFragment = false;
                        }
                    }


                }
                else
                {
                    Utility.showSnackBar(getString(R.string.internet_unavailable), coordinatorLayout);
                }


                break;


           // case R.id.add_user:

                if(Connectivity.isConnected(MainActivity.this)){

                    Navigationflag = true;
                    flagForOptionMenu = false;

                    fragment = new AddUserFragment();
                    tempFragment = getCurrentFragment();
                    if (tempFragment != null)
                    {
                        if (tempFragment instanceof AddUserFragment)
                        {
                            flagToAddFragment = false;
                        }
                    }


                }
                else
                {
                    Utility.showSnackBar(getString(R.string.internet_unavailable), coordinatorLayout);
                }

                 break;
*/



            /* case R.id.homeadd_appointment:
                if (Connectivity.isConnected(MainActivity.this))
                {
                    Navigationflag = true;
                    flagForOptionMenu = false;

                    fragment = new Home();
                    tempFragment = getCurrentFragment();
                    if (tempFragment != null)
                    {
                        if (tempFragment instanceof Home)
                        {
                            flagToAddFragment = false;
                        }
                    }
                }
                else
                {
                    Utility.showSnackBar(getString(R.string.internet_unavailable), coordinatorLayout);
                }
                break;
            case R.id.menu_mrn:
                if (Connectivity.isConnected(MainActivity.this))
                {
                    Navigationflag = true;
                    flagForOptionMenu = false;

                    fragment = new MRNFragment();
                    tempFragment = getCurrentFragment();
                    if (tempFragment != null)
                    {
                        if (tempFragment instanceof MRNFragment)
                        {
                            flagToAddFragment = false;
                        }
                    }
                }
                else
                {
                    Utility.showSnackBar(getString(R.string.internet_unavailable), coordinatorLayout);
                }
                break;
            case R.id.menu_po:
                if (Connectivity.isConnected(MainActivity.this))
                {
                    Navigationflag = true;
                    flagForOptionMenu = false;



                    fragment = new PurchaseOrderFragment();
                    tempFragment = getCurrentFragment();
                    if (tempFragment != null)
                    {
                        if (tempFragment instanceof PurchaseOrderFragment)
                        {
                            flagToAddFragment = false;
                        }
                    }
                }
                else
                {
                    Utility.showSnackBar(getString(R.string.internet_unavailable), coordinatorLayout);
                }
                break;
            case R.id.menu_grn:

                if(Connectivity.isConnected(MainActivity.this)){

                    Navigationflag = true;
                    flagForOptionMenu = false;
                    menuItem.setChecked(true);

                    fragment=new GRNPreRequireFragment();
                    tempFragment=getCurrentFragment();
                    if(tempFragment != null){

                        if (tempFragment instanceof GRNPreRequireFragment)
                        {
                            flagToAddFragment = false;
                        }
                    }

                }
                else
                {
                    Utility.showSnackBar(getString(R.string.internet_unavailable), coordinatorLayout);
                }


                break;
            case R.id.menu_invoice:
                if (Connectivity.isConnected(MainActivity.this))
                {

                    Navigationflag = true;
                    flagForOptionMenu = false;
                    menuItem.setChecked(true);

                    fragment = new InvoiceFragment();
                    tempFragment = getCurrentFragment();
                    if (tempFragment != null)
                    {
                        if (tempFragment instanceof InvoiceFragment)
                        {
                            flagToAddFragment = false;
                        }
                    }
                }
                else
                {
                    Utility.showSnackBar(getString(R.string.internet_unavailable), coordinatorLayout);
                }
                break;
            case R.id.menu_materialIssue:
                if (Connectivity.isConnected(MainActivity.this))
                {
                    Navigationflag = true;
                    flagForOptionMenu = false;
                    menuItem.setChecked(true);

                    fragment = new MaterialIssueFragment();
                    tempFragment = getCurrentFragment();
                    if (tempFragment != null)
                    {
                        if (tempFragment instanceof MaterialIssueFragment)
                        {
                            flagToAddFragment = false;
                        }
                    }



                }
                else
                {
                    Utility.showSnackBar(getString(R.string.internet_unavailable), coordinatorLayout);
                }
                break;
            case R.id.menu_dailyStatusReport:


                break;
            case R.id.menu_bill:

                break;

            case R.id.menu_stock:

                break;
 */


            /*case R.id.logout:

                Utility.logoutUserSucessfully(MainActivity.this, accountManager);


                break;
*/
        }
        if (fragment != null)
        {
            if (flagToAddFragment)
            {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.flContentRoot, fragment)
                        .addToBackStack(null)
                        .commit();
            }

        }
        else
        {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();




    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments())
        {
            handleResult(fragment, requestCode, resultCode, data);
        }

        switch (requestCode)
        {
            // Check for the integer request code originally supplied to startResolutionForResult().

            case REQUEST_PERMISSION_SETTING:
                sentToSettings = true;
                break;
        }
    }


    private void handleResult(Fragment frag, int requestCode, int resultCode, Intent data)
    {

        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null)
        {
            for (Fragment f : frags)
            {
                if (f != null)
                {
                    handleResult(f, requestCode, resultCode, data);
                }
            }
        }
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();

        Log.e("Ethe yeto Ka", "");
        if (sentToSettings)
        {
            sentToSettings = false;
        }
    }

    Fragment getCurrentFragment()
    {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.flContentRoot);
        return currentFragment;
    }

    @Override
    public void onBackPressed()
    {
        Log.e("Fragment Count", "" + getSupportFragmentManager()
                .getBackStackEntryCount());

        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            if (drawer.isDrawerOpen(GravityCompat.START))
            {
                drawer.closeDrawers();
            }
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() > 1)
        {
            for (Fragment fragment : getSupportFragmentManager().getFragments())
            {
                handleBackPressed(fragment);
            }

        }
        else
        {
            if (back_pressed + 1000 > System.currentTimeMillis())
            {
                Log.e("Count Madhe ss",
                        "" + getFragmentManager().getBackStackEntryCount());
                finish();

            }
            else
            {
                Toast.makeText(getBaseContext(),
                        "Press once again to exit " + getResources()
                                .getString(R.string.app_name) + " !",
                        Toast.LENGTH_SHORT).show();
            }
            back_pressed = System.currentTimeMillis();
        }
    }

    private void handleBackPressed(Fragment fragment)
    {
        if (fragment instanceof FragmentBackPressed)
        {
            backPressedInterface = (FragmentBackPressed) fragment;
            backPressedInterface.onBackKeyPressed();
        }

    }



}
