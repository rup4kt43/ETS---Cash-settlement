package com.example.expensetrackingsystem.TripRequest.DTO;

public class TripRequestDTO {
    String requestFromNumber;
    String tripDate;


    public String getRequestFromNumber() {
        return requestFromNumber;
    }

    public void setRequestFromNumber(String requestFromNumber) {
        this.requestFromNumber = requestFromNumber;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }
}
