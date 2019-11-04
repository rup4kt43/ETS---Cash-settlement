package com.example.expensetrackingsystem.TripRequest.VIEW;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.TripRequest.ADAPTER.TripRequestAdapter;
import com.example.expensetrackingsystem.TripRequest.DTO.TripRequestDTO;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TripRequestView extends AppCompatActivity {
    ArrayList<TripRequestDTO> tripReqArray;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tripReqArray = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_request_view);


        recyclerView = findViewById(R.id.rv_trip_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retriveRequest();

        Log.e("USERNAME",Global.userName);
        Log.e("USERNAME",Global.userName);


    }

    private void retriveRequest() {
        Global.tripReqArray.clear();
        DatabaseReference databaseReference = Global.mDatabase.child("USER DETAIL").child(Global.userPhone).child("Request");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot detail : dataSnapshot.getChildren()) {
                    String reqFrom = detail.getKey();

                    String reqDate = (String) detail.getValue();

                    TripRequestDTO tripRequestDTO = new TripRequestDTO();
                    tripRequestDTO.setRequestFromNumber(reqFrom);
                    tripRequestDTO.setTripDate(reqDate);
                    Global.tripReqArray.add(tripRequestDTO);


                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setAdapter() {
        Log.e("Array Sizeeee", String.valueOf(tripReqArray.size()));
        Log.e("Array Sizeeee", String.valueOf(tripReqArray.size()));

        TripRequestAdapter tripRequestAdapter = new TripRequestAdapter(this, Global.tripReqArray);
        recyclerView.setAdapter(tripRequestAdapter);
    }
}
