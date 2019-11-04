package com.example.expensetrackingsystem.Home.VIEW;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.expensetrackingsystem.Home.DTO.ExpenseForChartDTO;
import com.example.expensetrackingsystem.Home.INTERFACES.HomeInterface;
import com.example.expensetrackingsystem.Home.PRESENTER.HomePresenter;
import com.example.expensetrackingsystem.MyExpenseHistory.VIEW.MyExpenseHistoryView;
import com.example.expensetrackingsystem.MyTrip.VIEW.MyTripView;
import com.example.expensetrackingsystem.PlanATrip.VIEW.PlanATripView;
import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.TripRequest.VIEW.TripRequestView;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class HomeView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeInterface.view {
    HomePresenter presenter;
    CardView myExpenseHistory;
    TextView navHeaderEmail, navHeaderName;
    PieChartView pieChartView;
    List<SliceValue> pieData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        pieChartView = findViewById(R.id.chart);
        pieData = new ArrayList<>();








      /*  friendReq = findViewById(R.id.cv_friendRequest);
        friendReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeView.this, Global.userPhone, Toast.LENGTH_SHORT).show();
            }
        });*/
        setSupportActionBar(toolbar);
        toolbar.setTitle("HOME");
        myExpenseHistory = findViewById(R.id.cv_myExpenseHistory);
        myExpenseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeView.this, MyExpenseHistoryView.class));
            }
        });

        CardView cardView = findViewById(R.id.cv_plan_a_trip);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeView.this, PlanATripView.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);

        navHeaderEmail = headerView.findViewById(R.id.navheader_email);
        navHeaderEmail.setText(Global.userEmail);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //Initiating presenter object
        presenter = new HomePresenter(this);

        presenter.retriveUserDetail();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myTrip) {

            startActivity(new Intent(HomeView.this, MyTripView.class));
            // Handle the camera action
        } else if (id == R.id.nav_request) {
            startActivity(new Intent(HomeView.this, TripRequestView.class));

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loadChart(ArrayList<ExpenseForChartDTO> expenseForChartArray) {
        pieData.clear();
        int color = Color.RED;
        for (int i = 0; i < expenseForChartArray.size(); i++) {
            int value = expenseForChartArray.get(i).getAmount();
            if (i == 0) {
                color = Color.RED;
            }
            if (i == 1) {
                color = Color.BLUE;
            }
            if (i == 2) {
                color = Color.YELLOW;
            }
            if (i == 3) {
                color = Color.GREEN;
            }
            if (i == 4) {
                color = Color.WHITE;
            }
            Log.e("Value", String.valueOf(value));
            Log.e("Value", String.valueOf(value));
            pieData.add(new SliceValue(value, color).setLabel(expenseForChartArray.get(i).getName().concat(" NPR : " + expenseForChartArray.get(i).getAmount())));

        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(10);
        pieChartData.setHasCenterCircle(true).setCenterText1("EXPENSES").setCenterText1FontSize(12).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);


    }


}
