package com.example.expensetrackingsystem.TripRequest.ADAPTER;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.TripRequest.DTO.TripRequestDTO;
import com.example.expensetrackingsystem.TripRequest.VIEW.TripRequestView;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class TripRequestAdapter extends RecyclerView.Adapter {
    private final TripRequestView context;
    private final ArrayList<TripRequestDTO> array;
    private TextView tv_reqFrom;
    private Button btn_apply;

    public TripRequestAdapter(TripRequestView tripRequestView, ArrayList<TripRequestDTO> tripReqArray) {
        this.context = tripRequestView;
        this.array = tripReqArray;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trip_request_rv_containeer, parent, false);
        return new TripRequestCustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        tv_reqFrom.setText(array.get(position).getRequestFromNumber());

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    private class TripRequestCustomHolder extends RecyclerView.ViewHolder {
        public TripRequestCustomHolder(View view) {
            super(view);
            tv_reqFrom = view.findViewById(R.id.tv_reqFrom);
            btn_apply = view.findViewById(R.id.btn_accept);
            btn_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_apply.setBackgroundColor(Color.RED);
                    btn_apply.setEnabled(false);
                    btn_apply.setText("ACCEPTED");
                    Log.e("USERNAME", Global.userName);
                    Log.e("USERNAME", Global.userName);
                    int pos = getAdapterPosition();
                    DatabaseReference databaseReference = Global.mDatabase.child("TRIP LIST")
                            .child(array.get(pos).getRequestFromNumber())
                            .child(array.get(pos).getTripDate())
                            .child("Members")
                            .child(Global.userPhone);
                    databaseReference.setValue(Global.userName);




                    DatabaseReference acceptedReference = Global.mDatabase.child("USER DETAIL").child(Global.userPhone).child("Accepted Request");

                    acceptedReference.child(array.get(pos).getRequestFromNumber()).setValue(array.get(pos).getTripDate());


                    DatabaseReference db = Global.mDatabase.child("USER DETAIL").child(Global.userPhone).child("Request");
                    db.removeValue();



                }
            });
        }
    }
}
