package com.example.expensetrackingsystem.MyTrip.MODEL;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.expensetrackingsystem.MyTrip.CONTRACTS.MyTripInterfaces;
import com.example.expensetrackingsystem.MyTrip.DTO.MemberDetailsDTO;
import com.example.expensetrackingsystem.MyTrip.DTO.MyTripDTO;
import com.example.expensetrackingsystem.MyTrip.VIEW.MyTripView;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyTripModel {
    final ArrayList<MyTripDTO> arrayList = new ArrayList<>();

    public void retriveMyTripInfo(final MyTripInterfaces.presenterModelCallBack callBack) {

        DatabaseReference tripList = Global.mDatabase.child("TRIP LIST");
        tripList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot keyPhone : dataSnapshot.getChildren()) {

                    String num = keyPhone.getKey();
                    if (Global.userPhone.matches(num)) {
                        ArrayList<MyTripDTO> arrayList = new ArrayList<>();
                        for (DataSnapshot time : keyPhone.getChildren()) {

                            try {
                                MyTripDTO myTripDTO = new MyTripDTO();      //creating new object of myTripDto
                                myTripDTO.setFromDate(time.child("From Date").getValue().toString());   //invoking getter and setter
                                myTripDTO.setLocationFrom(time.child("Location From").getValue().toString());
                                myTripDTO.setToDate(time.child("To Date").getValue().toString());
                                myTripDTO.setTime(time.getKey().toString());
                                myTripDTO.setLocationTo(time.child("Location To").getValue().toString());
                                arrayList.add(myTripDTO);       //ADDING mytrip dto object which has set properties into arraylist
                               //sending the arraylist to presenter through callback
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        callBack.tripDetails(arrayList);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addMemberToMyTrip(String time, ArrayList<MemberDetailsDTO> memberDetailsArray) {
        DatabaseReference myTripReference = Global.mDatabase.child("TRIP LIST").child(Global.userPhone).child(time).child("Members");
        for (int i = 0; i < memberDetailsArray.size(); i++) {
            myTripReference.child(memberDetailsArray.get(i).getPersonName()).setValue(memberDetailsArray.get(i).getPersonPhone());
        }
    }

    public void loadFriendsTrip(final MyTripInterfaces.checkFriendPresenterModelCallback callback) {
        arrayList.clear();

        Log.e("Phone", Global.userPhone);

        DatabaseReference acceptedRef = Global.mDatabase.child("USER DETAIL").child(Global.userPhone).child("Accepted Request");
        acceptedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() != 0) {
                    MyTripView.isFriend =1 ;
                    for (DataSnapshot detail : dataSnapshot.getChildren()) {


                        String numKey = detail.getKey();
                        Global.friendTripUserPhone = numKey;
                        String dateKey = detail.getValue().toString();

                        Log.e("dateKey ", dateKey);
                        Log.e("dateKey ", dateKey);

                        DatabaseReference tripDetails = Global.mDatabase.child("TRIP LIST")
                                .child(numKey)
                                .child(dateKey);
                        tripDetails.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getChildrenCount() > 0) {
                                    String time = dataSnapshot.getKey();
                                    String locFrom = (String) dataSnapshot.child("Location From").getValue();
                                    String locTo = dataSnapshot.child("Location To").getValue().toString();
                                    String dateFrom = dataSnapshot.child("From Date").getValue().toString();
                                    String dateTo = dataSnapshot.child("To Date").getValue().toString();
                                    MyTripDTO myTripDTO = new MyTripDTO();
                                    myTripDTO.setToDate(dateTo);
                                    myTripDTO.setFromDate(dateFrom);
                                    myTripDTO.setLocationTo(locTo);
                                    myTripDTO.setLocationFrom(locFrom);
                                    myTripDTO.setTime(time);
                                    arrayList.add(myTripDTO);
                                    callback.friendsTrip(arrayList);
                                }else {
                                    callback.friendsTrip(arrayList);
                                }
                            }



                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }


                } else {
                    callback.friendsTrip(arrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
