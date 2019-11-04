package com.example.expensetrackingsystem.Home.PRESENTER;

import com.example.expensetrackingsystem.Home.DTO.ExpenseForChartDTO;
import com.example.expensetrackingsystem.Home.INTERFACES.HomeInterface;
import com.example.expensetrackingsystem.Home.MODEL.HomeModel;
import com.example.expensetrackingsystem.Home.VIEW.HomeView;

import java.util.ArrayList;

public class HomePresenter implements HomeInterface.presenter {
    HomeInterface.view view;
    HomeModel model;
    public HomePresenter(HomeView homeView) {
        this.view = homeView;
        model = new HomeModel();
    }

    @Override
    public void retriveUserDetail() {
        model.retriveUserDetail(new HomeInterface.chartPresenterModelCallback() {
            @Override
            public void chartData(ArrayList<ExpenseForChartDTO> expenseForChartArray) {
                view.loadChart(expenseForChartArray);
            }
        });
    }


}
