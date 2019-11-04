package com.example.expensetrackingsystem.Home.INTERFACES;

import com.example.expensetrackingsystem.Home.DTO.ExpenseForChartDTO;

import java.util.ArrayList;

public interface HomeInterface {

    interface view{

        void loadChart(ArrayList<ExpenseForChartDTO> expenseForChartArray);

    }
    interface presenter{
        void retriveUserDetail();


    }

    interface chartPresenterModelCallback{
        void chartData(ArrayList<ExpenseForChartDTO> expenseForChartArray);
    }
}
