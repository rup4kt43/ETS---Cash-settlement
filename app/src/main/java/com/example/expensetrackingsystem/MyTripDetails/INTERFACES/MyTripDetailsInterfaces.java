package com.example.expensetrackingsystem.MyTripDetails.INTERFACES;

import com.example.expensetrackingsystem.MyTripDetails.DTO.ExpensesDTO;
import com.example.expensetrackingsystem.MyTripDetails.DTO.MyTripDetailsDTO;

import java.util.ArrayList;

public interface MyTripDetailsInterfaces {

    interface view {
        void loadMemberList(ArrayList<MyTripDetailsDTO> memberList);
        
        void showToast(String msg);

        void loadMyExpenses(ArrayList<ExpensesDTO> myExpense,String personName);

        void loadMyFriendExpense(ArrayList<ExpensesDTO> myExpenses,String personName);
    }

    interface presenter {

        void retriveMembers(String time);


        ExpensesDTO addNewExpense(String expenseName, String expenseAmount);

        void saveNewExpenseToFirebase(ArrayList<ExpensesDTO> addExpenseArray, String time);

        void retriveMyExpense(String time);
    }

    interface  presenterModelCallback{
        void memberList(ArrayList<MyTripDetailsDTO> memberList);
    }

    interface dialogPresenterModelCallback{
        void myExpenseArray(ArrayList<ExpensesDTO> myExpenses,String name);
    }


}
