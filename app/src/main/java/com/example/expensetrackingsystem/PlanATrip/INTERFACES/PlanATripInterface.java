package com.example.expensetrackingsystem.PlanATrip.INTERFACES;

public interface PlanATripInterface {

    interface  view{
         void switchStartDate();

         void switchEndDate();

         void showToast(String msg);

    }

    interface presenter{
        void alterCalendar(String msg);

        void createATrip(String starting,String destination,String startDate,String endDate);
    }
}

