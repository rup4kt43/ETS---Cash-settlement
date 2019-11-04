package com.example.expensetrackingsystem.MyTrip.CONTRACTS;

import com.example.expensetrackingsystem.MyTrip.DTO.MemberDetailsDTO;
import com.example.expensetrackingsystem.MyTrip.DTO.MyTripDTO;
import com.example.expensetrackingsystem.MyTrip.DTO.NumAndTimeDTO;

import java.util.ArrayList;

public interface MyTripInterfaces {

    interface view {
        void loadMyTrip(ArrayList<MyTripDTO> tripDetails);

        void addMemberToMyTrip(String time, ArrayList<MemberDetailsDTO> memberDetailsArray);
    }

    interface presenter {

        void retriveMyTripInfo();

        void addMemberToMyTrip(String time, ArrayList<MemberDetailsDTO> memberDetailsArray);

        void loadFriendsTrip();
    }


    interface presenterModelCallBack {
        void tripDetails(ArrayList<MyTripDTO> myTripDetails);

    }

    interface checkFriendPresenterModelCallback {
        void friendsTrip(ArrayList<MyTripDTO> myTrip);
    }
}
