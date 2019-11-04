package com.example.expensetrackingsystem.MyExpenseHistory.MODEL;

import androidx.annotation.NonNull;

import com.example.expensetrackingsystem.MyExpenseHistory.DTO.MyExpenseHisotryDTO;
import com.example.expensetrackingsystem.MyExpenseHistory.INTERFACE.MyExpenseHistoryInterface;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyExpenseModel {
    public void retriveMyExpense(String date, final MyExpenseHistoryInterface.expenseListPresenterModelCallback callback) {
        final ArrayList<MyExpenseHisotryDTO> myExpenseArray = new ArrayList<>();

        DatabaseReference expenseRef = Global.mDatabase.child("Completed Trip").child(Global.userPhone).child(date).child(Global.userName);

        expenseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot particulars: dataSnapshot.getChildren()){         //Date SnapShot
                    MyExpenseHisotryDTO myExpenseHisotryDTO = new MyExpenseHisotryDTO();
                    myExpenseHisotryDTO.setParticulars(particulars.getKey());
                    myExpenseHisotryDTO.setPrice(particulars.getValue().toString());
                    myExpenseArray.add(myExpenseHisotryDTO);
                }
                callback.expenseResponseCallback(myExpenseArray);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void retriveTripDates(final MyExpenseHistoryInterface.presenterModelCallBack callBack) {
        final ArrayList<String> dateString = new ArrayList<>();
        DatabaseReference phoneRef = Global.mDatabase.child("Completed Trip").child(Global.userPhone);

        phoneRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dateSnap: dataSnapshot.getChildren()){         //Date SnapShot
                    String date = dateSnap.getKey();
                    dateString.add(date);
                }
                callBack.dateResponseCallback(dateString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO
            }
        });
    }
}
