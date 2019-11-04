package com.example.expensetrackingsystem.PlanATrip.VIEW;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensetrackingsystem.PlanATrip.INTERFACES.PlanATripInterface;
import com.example.expensetrackingsystem.PlanATrip.PRESENTER.PlanATripPresenter;
import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.Utilities.Global;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlanATripView extends AppCompatActivity implements PlanATripInterface.view {

    TextView tv_startdate, tv_enddate;
    CalendarView calendar;
    String temp_date;
    EditText et_starting, et_destination;
    String startDate;
    String endDate;
    PlanATripPresenter presenter;
    Button btn_createtrip;
    public static ProgressDialog progressDialog;
    LinearLayout startLayout, endLayout;
    int count = 0;

    TextView sDate, eDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_atrip_view);

        //Initiating the presenter object
        presenter = new PlanATripPresenter(this);

        //Initiating the progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.... Saving Trip Details");

        //Refrencing widgets
        startLayout = findViewById(R.id.ll_startDate);
        endLayout = findViewById(R.id.ll_endDate);

        tv_startdate = findViewById(R.id.tv_startDate);
        tv_enddate = findViewById(R.id.tv_endDate);


        sDate = findViewById(R.id.tv_selected_start_date);
        eDate = findViewById(R.id.tv_selected_end_date);

        //Getting the current date
        Date date = Calendar.getInstance().getTime();
        Log.e("date", date.toString());
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyy");
        String currentDate = df.format(date);

        sDate.setText(currentDate);
        eDate.setText(currentDate);

        calendar = findViewById(R.id.calendar);
        btn_createtrip = findViewById(R.id.btn_createTrip);

        et_starting = findViewById(R.id.et_starting);
        et_destination = findViewById(R.id.et_destination);


        //NOTE start--> to alter the calendar to start date .i.e make start date text view selected (set background)
        //NOTE end--> to alter the calendar to end date .i.e make end date text view selected (set background)

        //Adding action to textview
        startLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling method from presenter
                presenter.alterCalendar("start");
            }
        });

        endLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling method from presenter
                presenter.alterCalendar("end");
            }
        });
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {

                temp_date = i2 + "-" + (i1 + 1) + "-" + i;


                //Simple logic to alter onDate Change listener-->actually should be called from presenter

                if (count == 0) {
                    sDate.setText(temp_date);
                } else {
                    eDate.setText(temp_date);
                }

            }
        });


        btn_createtrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                final String starting = et_starting.getText().toString();
                final String destination = et_destination.getText().toString();
                startDate = sDate.getText().toString();
                endDate = eDate.getText().toString();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.createATrip(starting, destination, startDate, endDate);
                        progressDialog.dismiss();

                        if (Global.createTripState == 0) {  //0 for successful creation of trip state
                            PlanATripView.this.finish();

                        }

                    }
                }, 2000);

            }
        });


    }


    @Override
    public void switchStartDate() {
        count = 0;

        //When startDate is selected changing its background color to green and text color to white
        startLayout.setBackgroundColor(Color.parseColor("#59A239"));      //GREEN COLOR CODE
        tv_startdate.setTextColor(Color.parseColor("#FFFFFF"));     //WHITE COLOR CODE
        sDate.setTextColor(Color.parseColor("#FFFFFF"));

        //When startDate is selected changing endDate background to white and text color to black
        endLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));     //WHITE COLOR CODE
        tv_enddate.setTextColor(Color.parseColor("#000000"));       //BLACK COLOR CODE
        eDate.setTextColor(Color.parseColor("#000000"));


    }

    @Override
    public void switchEndDate() {

        count = 1;

        this.endDate = temp_date;  //saving current date to endDate string

        //When endDate is selected changing its background color to green and text color to white
        endLayout.setBackgroundColor(Color.parseColor("#59A239")); //GREEN COLOR CODE
        tv_enddate.setTextColor(Color.parseColor("#FFFFFF"));   //WHITE COLOR CODE
        eDate.setTextColor(Color.parseColor("#FFFFFF"));


        //When endDate is selected changing the start date background to white and text color to black
        startLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));   //WHITE COLOR CODE
        tv_startdate.setTextColor(Color.parseColor("#000000")); //BLACK COLOR CODE
        sDate.setTextColor(Color.parseColor("#000000"));

    }

    @Override
    public void showToast(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
