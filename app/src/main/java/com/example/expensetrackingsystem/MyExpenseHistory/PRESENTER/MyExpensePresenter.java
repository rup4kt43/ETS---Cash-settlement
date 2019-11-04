package com.example.expensetrackingsystem.MyExpenseHistory.PRESENTER;

import com.example.expensetrackingsystem.MyExpenseHistory.DTO.MyExpenseHisotryDTO;
import com.example.expensetrackingsystem.MyExpenseHistory.INTERFACE.MyExpenseHistoryInterface;
import com.example.expensetrackingsystem.MyExpenseHistory.MODEL.MyExpenseModel;
import com.example.expensetrackingsystem.MyExpenseHistory.VIEW.MyExpenseHistoryView;

import java.util.ArrayList;

public class MyExpensePresenter implements MyExpenseHistoryInterface.presenter {

    MyExpenseHistoryInterface.view view;
    MyExpenseModel model;

    public MyExpensePresenter(MyExpenseHistoryView myExpenseHistoryView) {

        this.view = myExpenseHistoryView;
        model = new MyExpenseModel();
    }

    @Override
    public void retriveMyExpense(String date) {
        model.retriveMyExpense(date, new MyExpenseHistoryInterface.expenseListPresenterModelCallback() {
            @Override
            public void expenseResponseCallback(ArrayList<MyExpenseHisotryDTO> myExpenseArray) {

                Double totalPrice=0.0;
                for(int i=0;i<myExpenseArray.size();i++){
                    totalPrice = totalPrice+ Double.parseDouble(myExpenseArray.get(i).getPrice());
                }
                view.loadExpenseDetails(myExpenseArray,totalPrice);

            }
        });
    }

    @Override
    public void retriveTripDates() {
        model.retriveTripDates(new MyExpenseHistoryInterface.presenterModelCallBack() {
            @Override
            public void dateResponseCallback(ArrayList<String> dateList) {

                view.loadTripDates(dateList);
            }
        });
    }
}
