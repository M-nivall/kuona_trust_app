package com.example.Varsani;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.Varsani.Artists.AppliedExhibitions;
import com.example.Varsani.Artists.MyDonationHistory;
import com.example.Varsani.Artists.MyDonations;
import com.example.Varsani.Clients.CompletedServices;
import com.example.Varsani.Clients.ContactUs;
import com.example.Varsani.Clients.Invoice;
import com.example.Varsani.Clients.MyArtWork;
import com.example.Varsani.Clients.Search;
import com.example.Varsani.Clients.ServiceItems;
import com.example.Varsani.Clients.SupplierLogin;
import com.example.Varsani.Clients.home.HomeFragment;
import com.example.Varsani.Staff.Dashboard;
import com.example.Varsani.Staff.Finance.NewDonations;
import com.example.Varsani.Staff.Models.DonationHistory;
import com.example.Varsani.Staff.SelectLogin;
import com.example.Varsani.Clients.About;
import com.example.Varsani.Clients.Adapters.AdapterProducts;
import com.example.Varsani.Clients.CartItems;
import com.example.Varsani.Clients.Feedback;
import com.example.Varsani.Clients.Help_in;
import com.example.Varsani.Clients.Login;
import com.example.Varsani.Clients.Models.UserModel;
import com.example.Varsani.Clients.Orders_hist;
import com.example.Varsani.Clients.Profile;
import com.example.Varsani.Clients.Register;
import com.example.Varsani.R;
import com.example.Varsani.Staff.StaffLogin;
import com.example.Varsani.Students.Workshops;
import com.example.Varsani.Suppliers.MyRequests;
import com.example.Varsani.Suppliers.RegSuppliers;
import com.example.Varsani.Suppliers.Requests;
import com.example.Varsani.utils.SessionHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private UserModel user;
    private SessionHandler session;
    AdapterProducts adapterProducts;
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_bar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        FloatingActionButton fab = findViewById(R.id.fab);

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        homeFragment = new HomeFragment();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (session.isLoggedIn()) {
                    Intent in = new Intent(getApplicationContext(), CartItems.class);
                    startActivity(in);
                } else {
                    Snackbar.make(view, "You must login to view cart", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        fab.setImageResource(R.drawable.ic_shopping_cart_white);

        if (session.isLoggedIn()) {
            if (user.getUser_type().equals("Client") ||
                    user.getUser_type().equals("Artist") ||
                    user.getUser_type().equals("Patron") ||
                    user.getUser_type().equals("Student")) {

                // Do nothing
            } else {
                Intent in = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(in);
                finish();
            }
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(getApplicationContext(), Profile.class));

                } else if (id == R.id.nav_workshops) {
                    startActivity(new Intent(getApplicationContext(), Workshops.class));

                } else if (id == R.id.nav_artwork) {
                    startActivity(new Intent(getApplicationContext(), MyArtWork.class));

                } else if (id == R.id.nav_my_donation) {
                    startActivity(new Intent(getApplicationContext(), MyDonations.class));

                } else if (id == R.id.nav_my_donation_history) {
                    startActivity(new Intent(getApplicationContext(), MyDonationHistory.class));

                } else if (id == R.id.nav_donation_history) {
                    startActivity(new Intent(getApplicationContext(), DonationHistory.class));

                } else if (id == R.id.nav_applied_exhibitions) {
                    startActivity(new Intent(getApplicationContext(), AppliedExhibitions.class));

                } else if (id == R.id.nav_request) {
                    startActivity(new Intent(getApplicationContext(), MyRequests.class));

                } else if (id == R.id.nav_register) {
                    startActivity(new Intent(getApplicationContext(), Register.class));

                } else if (id == R.id.nav_reg_supplier) {
                    startActivity(new Intent(getApplicationContext(), RegSuppliers.class));

                } else if (id == R.id.nav_login) {
                    startActivity(new Intent(getApplicationContext(), Login.class));

                } else if (id == R.id.nav_contact) {
                    startActivity(new Intent(getApplicationContext(), ContactUs.class));

                } else if (id == R.id.nav_supplier_login) {
                    startActivity(new Intent(getApplicationContext(), SupplierLogin.class));

                } else if (id == R.id.nav_bookings) {
                    startActivity(new Intent(getApplicationContext(), ServiceItems.class));

                } else if (id == R.id.nav_completion) {
                    startActivity(new Intent(getApplicationContext(), CompletedServices.class));

                } else if (id == R.id.nav_invoice) {
                    startActivity(new Intent(getApplicationContext(), Invoice.class));

                } else if (id == R.id.nav_orders) {
                    if (user.getUser_type().equals("Client")) {
                        startActivity(new Intent(getApplicationContext(), Orders_hist.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), Requests.class));
                    }

                } else if (id == R.id.nav_feedback) {
                    startActivity(new Intent(getApplicationContext(), Feedback.class));

                } else if (id == R.id.nav_staff_login) {
                    startActivity(new Intent(getApplicationContext(), StaffLogin.class));

                } else if (id == R.id.nav_help) {
                    startActivity(new Intent(getApplicationContext(), Help_in.class));

                } else if (id == R.id.nav_about) {
                    startActivity(new Intent(getApplicationContext(), About.class));

                } else if (id == R.id.nav_logout) {
                    alertLogout();
                }

                drawer.closeDrawer(GravityCompat.START, true);
                return false;
            }
        });

        drawer.closeDrawers();
        check();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void alertLogout() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Do you want to logout?");
        alertDialog.setCancelable(false);
        alertDialog.setButton("Yes logout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                session.logoutUser();
                Toast.makeText(getApplicationContext(),
                        "You are logged out", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
            }
        });

        alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void check() {

        navigationView.getMenu().findItem(R.id.nav_invoice).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_completion).setVisible(false);

        navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_orders).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_request).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_reg_supplier).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_bookings).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_artwork).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_my_donation).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_supplier_login).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_workshops).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_my_donation_history).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_donation_history).setVisible(false);
        navigationView.getMenu().findItem(R.id.nav_applied_exhibitions).setVisible(false);

        if (session.isLoggedIn()) {

            navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_reg_supplier).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_staff_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_supplier_login).setVisible(false);

            if (user.getUser_type().equals("Artist")) {

                navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_artwork).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_my_donation).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_workshops).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_my_donation_history).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_applied_exhibitions).setVisible(true);

            } else if (user.getUser_type().equals("Student")) {

                navigationView.getMenu().findItem(R.id.nav_profile).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_workshops).setVisible(true);

            } else if (user.getUser_type().equals("Patron")) {

                navigationView.getMenu().findItem(R.id.nav_feedback).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
                navigationView.getMenu().findItem(R.id.nav_donation_history).setVisible(true);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // UPDATED: switch → if–else
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), Search.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
