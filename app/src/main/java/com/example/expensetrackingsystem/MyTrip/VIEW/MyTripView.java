package com.example.expensetrackingsystem.MyTrip.VIEW;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackingsystem.MyTrip.ADAPTER.MyTripCustomAdapter;
import com.example.expensetrackingsystem.MyTrip.CONTRACTS.MyTripInterfaces;
import com.example.expensetrackingsystem.MyTrip.DTO.MemberDetailsDTO;
import com.example.expensetrackingsystem.MyTrip.DTO.MyTripDTO;
import com.example.expensetrackingsystem.MyTrip.PRESENTER.MyTripPresenter;
import com.example.expensetrackingsystem.MyTripDetails.VIEW.MyTripDetailsView;
import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyTripView extends AppCompatActivity implements MyTripInterfaces.view {

    MyTripPresenter presenter;
    RecyclerView recyclerView;
    MyTripCustomAdapter myTripCustomAdapter;
    LinearLayout emptyLayout;
    String date;
    int selectedPos = 256;
    public static int isFriend = 0;
    private ArrayList<MyTripDTO> tripDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytrip);


        //Firebase Call
        DatabaseReference ref = Global.mDatabase.child("SelectedTrip").child(Global.userPhone);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        date = ds.getValue().toString().replace("+", "");
                        Log.e("Date", date);
                        Log.e("Date", date);

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        getSupportActionBar().setTitle("My Trip");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        emptyLayout = findViewById(R.id.ll_empty);


        //Initiating the presenter object to call presenter method through its object
        presenter = new MyTripPresenter(this);

        //Retriving myTrip info that is Date from, Date to , start location and destination from firebase
        presenter.retriveMyTripInfo();

        //Refrencing widgets
        recyclerView = findViewById(R.id.rv_myTrip);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void loadMyTrip(ArrayList<MyTripDTO> tripDetails) {

        Log.e("TripDetails", String.valueOf(tripDetails.size()));
        if (!tripDetails.isEmpty()) {
            emptyLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);


            if (date != null) {
                for (int i = 0; i < tripDetails.size(); i++) {
                    String temp = tripDetails.get(i).getTime().replace("+", "");


                    if (date.matches(temp)) {
                        selectedPos = i;
                        Log.e("SLEC", String.valueOf(selectedPos));
                    }
                }
            } else {
                Toast.makeText(this, "No currently selected trip", Toast.LENGTH_SHORT).show();
            }


            myTripCustomAdapter = new MyTripCustomAdapter(this, tripDetails, selectedPos);


            setAdapter();
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }


    }

    @Override
    public void addMemberToMyTrip(String time, ArrayList<MemberDetailsDTO> memberDetailsArray) {


        presenter.addMemberToMyTrip(time, memberDetailsArray);
    }

    private void setAdapter() {
        recyclerView.setAdapter(myTripCustomAdapter);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.accepted_trip_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MyTripView.this.finish();
                break;
            case R.id.action_my_trip:
                Global.tripSelection = 0;
                presenter.retriveMyTripInfo();
                break;
            case R.id.action_friends_trip:
                Global.tripSelection = 1;        //assigning 1 since checking friendsTrip
                loadFriendsTrip();
                break;
            default:
                return true;
        }
        return true;
    }

    private void loadFriendsTrip() {
        presenter.loadFriendsTrip();
    }

    public void switchToMyTripDetails(String locFrom, String locTo, String dateFrom, String dateTo, String time) {
        Intent i = new Intent(MyTripView.this, MyTripDetailsView.class);
        i.putExtra("locFrom", locFrom);
        i.putExtra("locTo", locTo);
        i.putExtra("dateFrom", dateFrom);
        i.putExtra("dateTo", dateTo);
        i.putExtra("time", time);

        startActivity(i);
    }
}
