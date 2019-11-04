package com.example.expensetrackingsystem.MyExpenseHistory.VIEW;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackingsystem.MyExpenseHistory.ADAPTERS.MyExpenseHistoryAdapter;
import com.example.expensetrackingsystem.MyExpenseHistory.DTO.MyExpenseHisotryDTO;
import com.example.expensetrackingsystem.MyExpenseHistory.INTERFACE.MyExpenseHistoryInterface;
import com.example.expensetrackingsystem.MyExpenseHistory.PRESENTER.MyExpensePresenter;
import com.example.expensetrackingsystem.MyTrip.VIEW.MyTripView;
import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.Utilities.Global;

import java.util.ArrayList;

public class MyExpenseHistoryView extends AppCompatActivity implements MyExpenseHistoryInterface.view {
    MyExpensePresenter presenter;
    private ArrayList<String> tripDates;
    private Spinner spinner;
    private MyExpenseHistoryAdapter adapter;
    private RecyclerView recyclerView;
    private TextView totalAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myexpensehistory);
        spinner = findViewById(R.id.sp_dateList);

        totalAmount = findViewById(R.id.tv_total);

        getSupportActionBar().setTitle("My Expense History");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Initiating the presenter
        presenter = new MyExpensePresenter(this);
        presenter.retriveTripDates();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String date = (String) adapterView.getSelectedItem();
                presenter.retriveMyExpense(date);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    public void loadTripDates(ArrayList<String> dateList) {
        this.tripDates = dateList;


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, dateList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public void loadExpenseDetails(ArrayList<MyExpenseHisotryDTO> myExpenseArray, Double total) {
        Log.e("Expense Array Size", String.valueOf(myExpenseArray.size()));
        Log.e("Expense Array Size", String.valueOf(myExpenseArray.size()));
        adapter = new MyExpenseHistoryAdapter(this, myExpenseArray);
        recyclerView.setAdapter(adapter);
        totalAmount.setText(String.valueOf(total));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MyExpenseHistoryView.this.finish();
                break;
            default:
                return true;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
