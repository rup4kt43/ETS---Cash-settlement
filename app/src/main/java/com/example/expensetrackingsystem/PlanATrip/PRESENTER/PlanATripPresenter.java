package com.example.expensetrackingsystem.PlanATrip.PRESENTER;

import com.example.expensetrackingsystem.PlanATrip.INTERFACES.PlanATripInterface;
import com.example.expensetrackingsystem.PlanATrip.MODEL.PlanATripModel;
import com.example.expensetrackingsystem.PlanATrip.VIEW.PlanATripView;
import com.example.expensetrackingsystem.Utilities.Global;

public class PlanATripPresenter implements PlanATripInterface.presenter {
    PlanATripInterface.view view;
    PlanATripModel model;

    public PlanATripPresenter(PlanATripView planATripView) {
        this.view = planATripView;
        model = new PlanATripModel();

    }

    @Override
    public void alterCalendar(String msg) {
        switch (msg) {
            case "start":
                view.switchStartDate();
                break;
            case "end":

                view.switchEndDate();
                break;
            default:
        }
    }

    @Override
    public void createATrip(String starting, String destination, String startDate, String endDate) {
        if (starting.isEmpty() || destination.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            view.showToast("Required fields must be provided!!");
            Global.createTripState = 1;         //value 1 is for failed to create the trip
            PlanATripView.progressDialog.dismiss();
        } else {
            Global.createTripState = 0;     //value 0 is for successful creation of the trip
            model.createATrip(starting, destination, startDate, endDate);
        }
    }
}
