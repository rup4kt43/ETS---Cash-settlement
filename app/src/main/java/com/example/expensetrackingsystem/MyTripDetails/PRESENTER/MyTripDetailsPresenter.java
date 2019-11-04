package com.example.expensetrackingsystem.MyTripDetails.PRESENTER;

import com.example.expensetrackingsystem.MyTripDetails.DTO.ExpensesDTO;
import com.example.expensetrackingsystem.MyTripDetails.DTO.MyTripDetailsDTO;
import com.example.expensetrackingsystem.MyTripDetails.INTERFACES.MyTripDetailsInterfaces;
import com.example.expensetrackingsystem.MyTripDetails.MODEL.MyTripDetailsModel;
import com.example.expensetrackingsystem.MyTripDetails.VIEW.MyTripDetailsView;

import java.util.ArrayList;

public class MyTripDetailsPresenter implements MyTripDetailsInterfaces.presenter {
    private MyTripDetailsInterfaces.view view;
    private MyTripDetailsModel model;


    public MyTripDetailsPresenter(MyTripDetailsView myTripDetailsView) {
        this.view = myTripDetailsView;
        model = new MyTripDetailsModel();

    }

    @Override
    public void retriveMembers(String time) {
        model.retriveMembersList(time, new MyTripDetailsInterfaces.presenterModelCallback() {
            @Override
            public void memberList(ArrayList<MyTripDetailsDTO> memberList) {
                view.loadMemberList(memberList);
            }
        });
    }

    @Override
    public ExpensesDTO addNewExpense(String expenseName, String expenseAmount) {
        if (expenseAmount.isEmpty() || expenseName.isEmpty()) {
            view.showToast("Sorry! The fields cannot be empty");
        } else {

            ExpensesDTO expensesDTO = new ExpensesDTO();
            expensesDTO.setExpenseAmount(expenseAmount);
            expensesDTO.setExpenseName(expenseName);
            return expensesDTO;
        }
        return null;
    }

    @Override
    public void saveNewExpenseToFirebase(ArrayList<ExpensesDTO> addExpenseArray, String time) {
        model.saveNewExpense(addExpenseArray,time);
    }

    @Override
    public void retriveMyExpense(String time) {
        model.retriveMyExpense(time, new MyTripDetailsInterfaces.dialogPresenterModelCallback() {
            @Override
            public void myExpenseArray(ArrayList<ExpensesDTO> myExpenses,String personName) {
                view.loadMyExpenses(myExpenses, personName);
            }
        });

    }

    public void loadFriendExpense(String personName) {
        model.loadFriendExpense(personName, new MyTripDetailsInterfaces.dialogPresenterModelCallback() {
            @Override
            public void myExpenseArray(ArrayList<ExpensesDTO> myExpenses,String personName) {
                view.loadMyFriendExpense(myExpenses,personName);


            }
        });
    }
}
