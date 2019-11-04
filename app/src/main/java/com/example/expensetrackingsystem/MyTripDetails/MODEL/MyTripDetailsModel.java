package com.example.expensetrackingsystem.MyTripDetails.MODEL;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.expensetrackingsystem.MyTripDetails.DTO.ExpensesDTO;
import com.example.expensetrackingsystem.MyTripDetails.DTO.MyTripDetailsDTO;
import com.example.expensetrackingsystem.MyTripDetails.INTERFACES.MyTripDetailsInterfaces;
import com.example.expensetrackingsystem.MyTripDetails.VIEW.MyTripDetailsView;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyTripDetailsModel {
    public void retriveMembersList(String time, final MyTripDetailsInterfaces.presenterModelCallback callback) {
        String phone;
        if (Global.tripSelection == 0) {
            phone = Global.userPhone;
        } else {
            phone = Global.friendTripUserPhone;
            Log.e("Global", Global.cardSelectedDate);
        }
        final ArrayList<MyTripDetailsDTO> memberList = new ArrayList<>();
        DatabaseReference members = Global.mDatabase.child("TRIP LIST").child(phone).child(Global.cardSelectedDate).child("Members");
        members.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot details : dataSnapshot.getChildren()) {
                    MyTripDetailsDTO myTripDetailsDTO = new MyTripDetailsDTO();
                    String phone = details.getKey();
                    String name = (String) details.getValue();

                    myTripDetailsDTO.setPersonName(name);
                    myTripDetailsDTO.setPersonPhone(phone);

                    memberList.add(myTripDetailsDTO);

                }
                callback.memberList(memberList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveNewExpense(ArrayList<ExpensesDTO> addExpenseArray, String time) {
        String phone;
        if (Global.tripSelection == 0) {
            phone = Global.userPhone;
        } else {
            phone = Global.friendTripUserPhone;
            Log.e("Global", Global.cardSelectedDate);
        }
        Log.e("TIme", time);
        Log.e("userphone", Global.userPhone);
        DatabaseReference expenseRef = Global.mDatabase.child("TRIP LIST").child(phone).child(Global.cardSelectedDate).child("Expenses")
                .child(Global.userName);


        for (int i = 0; i < addExpenseArray.size(); i++) {
            DatabaseReference expenseNameRef = expenseRef.child(addExpenseArray.get(i).getExpenseName());
            expenseNameRef.setValue(addExpenseArray.get(i).getExpenseAmount());

        }

    }

    public void retriveMyExpense(String time, final MyTripDetailsInterfaces.dialogPresenterModelCallback callback) {
        DatabaseReference expenseRef = Global.mDatabase.child("TRIP LIST").child(Global.userPhone).child(time).child("Expenses")
                .child(Global.userName);
        final ArrayList<ExpensesDTO> myExpenseArray = new ArrayList<>();


        expenseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ExpensesDTO expensesDTO = new ExpensesDTO();
                    String expenseName = child.getKey();
                    String expenseAmount = (String) child.getValue();
                    expensesDTO.setExpenseName(expenseName);
                    expensesDTO.setExpenseAmount(expenseAmount);
                    myExpenseArray.add(expensesDTO);

                }
                callback.myExpenseArray(myExpenseArray,Global.userName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void loadFriendExpense(final String personName, final MyTripDetailsInterfaces.dialogPresenterModelCallback callback) {
        final ArrayList<ExpensesDTO> expenseList = new ArrayList<>();

        String phone;
        if (Global.tripSelection == 0) {
            phone = Global.userPhone;
        } else {
            phone = Global.friendTripUserPhone;
            Log.e("Global", Global.cardSelectedDate);
        }

        Log.e("time", Global.cardSelectedDate);
        DatabaseReference friendExpense = Global.mDatabase.child("TRIP LIST").child(phone).child(Global.cardSelectedDate).child("Expenses").child(personName);
        friendExpense.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                expenseList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    ExpensesDTO expensesDTO = new ExpensesDTO();
                    expensesDTO.setExpenseName(item.getKey());
                    expensesDTO.setExpenseAmount(item.getValue().toString());
                    expenseList.add(expensesDTO);
                }
                callback.myExpenseArray(expenseList,personName);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
