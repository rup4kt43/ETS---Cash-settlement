package com.example.expensetrackingsystem.Home.MODEL;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.expensetrackingsystem.Home.DTO.ExpenseForChartDTO;
import com.example.expensetrackingsystem.Home.INTERFACES.HomeInterface;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeModel {

    Integer totalAmt = 0;

    public void retriveUserDetail(final HomeInterface.chartPresenterModelCallback callback) {
        DatabaseReference userDetailRef = Global.mDatabase.child("USER DETAIL");


        userDetailRef.addValueEventListener(new ValueEventListener() {  //for looping
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot phone : dataSnapshot.getChildren()) {       //children --> phone number
                    String num = phone.getKey();    //get phone number
                    Log.e("phojne", num);
                    for (DataSnapshot detail : phone.getChildren()) {  //get detail


                        if (detail.getKey().matches("userEmail")) {
                            String email = (String) detail.getValue();
                            if (email.matches(Global.userEmail)) {
                                Global.userPhone = num;       // setting the phone number to global vairable userphone

                                DatabaseReference databaseReference = Global.mDatabase.child("USER DETAIL").child(Global.userPhone);
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot detail : dataSnapshot.getChildren()) {
                                            if (detail.getKey().matches("userName")) {
                                                Global.userName = detail.getValue().toString();

                                                Log.e("Username", Global.userName);
                                                Log.e("Username", Global.userName);
                                                retriveSelectedTripExpense(callback);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void retriveSelectedTripExpense(final HomeInterface.chartPresenterModelCallback callback) {

        final ArrayList<ExpenseForChartDTO> chartExpenseArray = new ArrayList<>();

        DatabaseReference db = Global.mDatabase.child("TRIP LIST").child(Global.userPhone).child("Mon Sep 02 09:01:48 GMT+05:45 2019").child("Expenses");

        Log.e("userPhone", Global.userPhone);
        Log.e("userPhone", Global.userPhone);
        DatabaseReference selected = Global.mDatabase.child("SelectedTrip").child(Global.userPhone);
        selected.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot date : dataSnapshot.getChildren()) {
                    if (Global.userName.matches(date.getKey())) {
                        String dateKey = date.getValue().toString();

                        DatabaseReference expenseRef = Global.mDatabase.child("TRIP LIST").child(Global.userPhone)
                                .child(dateKey).child("Expenses");
                        expenseRef.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                chartExpenseArray.clear();
                                for (DataSnapshot user : dataSnapshot.getChildren()) {
                                    ExpenseForChartDTO expenseForChartDTO = new ExpenseForChartDTO();
                                    String username = user.getKey();
                                    for (DataSnapshot exp : user.getChildren()) {

                                        int price = Integer.parseInt(exp.getValue().toString());
                                        totalAmt = totalAmt + price;
                                    }

                                    expenseForChartDTO.setName(username);
                                    expenseForChartDTO.setAmount(totalAmt);
                                    chartExpenseArray.add(expenseForChartDTO);
                                    totalAmt = 0;
                                }
                                callback.chartData(chartExpenseArray);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
