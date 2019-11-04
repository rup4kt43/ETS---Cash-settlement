package com.example.expensetrackingsystem.MyExpenseHistory.INTERFACE;

import com.example.expensetrackingsystem.MyExpenseHistory.DTO.MyExpenseHisotryDTO;

import java.util.ArrayList;

public interface MyExpenseHistoryInterface  {

    interface view{

        void loadTripDates(ArrayList<String> dateList);

        void loadExpenseDetails(ArrayList<MyExpenseHisotryDTO> myExpenseArray,Double total);
    }

    interface presenter{

        void retriveMyExpense(String dates);

        void retriveTripDates();
    }
    
    interface presenterModelCallBack{
        
        void dateResponseCallback(ArrayList<String> dateList);
    }

    interface expenseListPresenterModelCallback{
        void expenseResponseCallback(ArrayList<MyExpenseHisotryDTO> myExpenseArray);
    }


}
