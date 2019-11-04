package com.example.expensetrackingsystem.MyTripDetails.VIEW;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackingsystem.MyTripDetails.ADAPTERS.MyExpenseDialogAdapter;
import com.example.expensetrackingsystem.MyTripDetails.ADAPTERS.MyTripDetailsAdapter;
import com.example.expensetrackingsystem.MyTripDetails.DTO.ExpensesDTO;
import com.example.expensetrackingsystem.MyTripDetails.DTO.MyTripDetailsDTO;
import com.example.expensetrackingsystem.MyTripDetails.INTERFACES.MyTripDetailsInterfaces;
import com.example.expensetrackingsystem.MyTripDetails.PRESENTER.MyTripDetailsPresenter;
import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyTripDetailsView extends AppCompatActivity implements MyTripDetailsInterfaces.view {

    private TextView tv_dateFrom, tv_dateTo, tv_locFrom, tv_locTo, tv_self_name, tv_memCount;
    private MyTripDetailsPresenter presenter;
    private RecyclerView recyclerView;
    LinearLayout emptyLayout;
    ArrayList<ExpensesDTO> addExpenseArray;
    String expenseDialogTitle;
    double total = 0.00;
    double totalSplit;
    Dialog dialog_split, dialog;

    double personalTotal;

    public static int expenseFlag = 0;
    String time;
    private ArrayList<ExpensesDTO> expenseArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip_details_view);

        //Arraylist to save new expense on add listener
        addExpenseArray = new ArrayList<>();

        getSupportActionBar().setTitle("My Trip Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        emptyLayout = findViewById(R.id.ll_empty);

        //Initiating the object of presenter to call its method
        presenter = new MyTripDetailsPresenter(this);
        recyclerView = findViewById(R.id.rv_myTripDetails);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        Intent i = getIntent();
        String locFrom = i.getStringExtra("locFrom");
        String locTo = i.getStringExtra("locTo");
        String dateFrom = i.getStringExtra("dateFrom");
        String dateTo = i.getStringExtra("dateTo");
        time = i.getStringExtra("time");

        tv_dateFrom = findViewById(R.id.tv_dateFrom);
        tv_dateTo = findViewById(R.id.tv_dateTo);
        tv_locFrom = findViewById(R.id.tv_locationFrom);
        tv_locTo = findViewById(R.id.tv_locationTo);
        tv_self_name = findViewById(R.id.tv_self_name);

        tv_dateFrom.setText(dateFrom);
        tv_dateTo.setText(dateTo);
        tv_locFrom.setText(locFrom);
        tv_locTo.setText(locTo);
        tv_self_name.setText(Global.userName);

        tv_memCount = findViewById(R.id.tv_memCount);

        presenter.retriveMembers(time);

        retriveMyExpense();


    }

    private void retriveMyExpense() {

        presenter.retriveMyExpense(time);
    }

    @Override
    public void loadMemberList(ArrayList<MyTripDetailsDTO> memberList) {
        tv_memCount.setText(String.valueOf(memberList.size()));

        if (!memberList.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);


            MyTripDetailsAdapter myTripDetailsAdapter = new MyTripDetailsAdapter(this, memberList);
            recyclerView.setAdapter(myTripDetailsAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMyExpenses(ArrayList<ExpensesDTO> myExpense, String personName) {
        this.expenseDialogTitle = personName;
        this.expenseArray = myExpense;
    }

    @Override
    public void loadMyFriendExpense(ArrayList<ExpensesDTO> myExpenses, String personName) {

        this.expenseArray = myExpenses;
        if (expenseFlag == 0) {
            createMyExpenseCustomDialog(personName);
        }

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                MyTripDetailsView.this.finish();
                break;
            case R.id.action_add_expense:
                addExpenseArray.clear();
                createAddNewExpenseCustomDialog();
                break;
            case R.id.action_my_expense:
                Toast.makeText(this, "my epense clicked", Toast.LENGTH_SHORT).show();
                if (expenseFlag == 0) {
                    createMyExpenseCustomDialog(expenseDialogTitle);
                }
                break;
            case R.id.action_split:
                DatabaseReference databaseReference = Global.mDatabase.child("TRIP LIST").child(Global.userPhone).child(Global.cardSelectedDate).child("Expenses");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int count = (int) dataSnapshot.getChildrenCount();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String key = ds.getKey();
                            for (DataSnapshot ds2 : ds.getChildren()) {
                                String value = (String) ds2.getValue();
                                total = total + Double.parseDouble(value);

                                Log.e("total", String.valueOf(total));
                            }


                        }
                        totalSplit = total / count;
                        showSplitCustomDialog();
                        Log.e("Split", String.valueOf(totalSplit));
                        Log.e("Split", String.valueOf(totalSplit));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            case R.id.action_settlement:
                if (Global.tripSelection == 0) {
                    DatabaseReference databaseReference1 = Global.mDatabase.child("TRIP LIST").child(Global.userPhone).child(Global.cardSelectedDate)
                            .child("Expenses").child(Global.userName);

                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String key = dataSnapshot.getKey();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String value = (String) ds.getValue();
                                personalTotal = personalTotal + Double.parseDouble(value);
                            }

                            createSettlementDialog();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    DatabaseReference databaseReference1 = Global.mDatabase.child("TRIP LIST").child(Global.friendTripUserPhone).child(Global.cardSelectedDate)
                            .child("Expenses").child(Global.userName);

                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String key = dataSnapshot.getKey();
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String value = (String) ds.getValue();
                                personalTotal = personalTotal + Double.parseDouble(value);
                            }

                            createSettlementDialog();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            default:
                return true;
        }
        return true;
    }

    private void createSettlementDialog() {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.cash_settlement_custom_dialog);
        final TextView tv_cash_back, tv_total_expense;
        final EditText et_amuntPaid;
        Button btn_calculate;

        tv_cash_back = dialog.findViewById(R.id.tv_cash_back);
        tv_total_expense = dialog.findViewById(R.id.tv_total_expense);
        et_amuntPaid = dialog.findViewById(R.id.et_amountPaid);
        btn_calculate = dialog.findViewById(R.id.btn_calculate);

        tv_total_expense.setText(String.valueOf(personalTotal));
        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountPaid = et_amuntPaid.getText().toString();
                if (amountPaid.isEmpty()) {
                    Toast.makeText(MyTripDetailsView.this, "Please Enter the amount paid!!", Toast.LENGTH_SHORT).show();
                } else {
                    String cash_back = String.valueOf(Double.parseDouble(amountPaid) - personalTotal);
                    tv_cash_back.setText(cash_back);
                }
            }
        });

        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = d.getWidth() * 1;
        /*lp.height = (int) (d.getHeight() * 0.7);*/

        dialog.show();


    }

    private void showSplitCustomDialog() {

        dialog_split = new Dialog(this);
        dialog_split.setContentView(R.layout.split_custom_dialog);
        TextView tv_total = dialog_split.findViewById(R.id.tv_total);
        tv_total.setText(String.valueOf(totalSplit));

        Window dialogWindow = dialog_split.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = d.getWidth() * 1;
        /*lp.height = (int) (d.getHeight() * 0.7);*/

        dialog_split.show();
    }

    private void createMyExpenseCustomDialog(String personName) {
        expenseFlag = 1;
        double total_price = 0.0;


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.myexpense_custom_dialog);
        TextView name = dialog.findViewById(R.id.tv_name);
        name.setText(expenseDialogTitle);

        dialog.setCancelable(false);

        //Refrencing the widgets
        ImageView cross = dialog.findViewById(R.id.iv_cross);
        RecyclerView recyclerView = dialog.findViewById(R.id.dialog_rv_my_expense);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TextView total = dialog.findViewById(R.id.tv_total);


        MyExpenseDialogAdapter myExpenseDialogAdapter = new MyExpenseDialogAdapter(this, expenseArray);
        if (!expenseArray.isEmpty()) {

            for (int i = 0; i < expenseArray.size(); i++) {
                double price = Double.parseDouble(expenseArray.get(i).getExpenseAmount());
                total_price = total_price + price;
            }

            total.setText(String.valueOf(total_price));
            recyclerView.setAdapter(myExpenseDialogAdapter);
        }

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                expenseFlag = 0;
            }
        });

        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = d.getWidth() * 1;
        /*lp.height = (int) (d.getHeight() * 0.7);*/

        dialog.show();

    }


    //dialog for add new expense
    private void createAddNewExpenseCustomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.addexpense_custom_dialog);

        //Refrencing button widgets
        final EditText et_expense_name, et_expense_amount;
        Button btn_add, btn_apply, btn_cancel;
        et_expense_name = dialog.findViewById(R.id.et_expense_name);
        et_expense_amount = dialog.findViewById(R.id.et_expense_amount);
        btn_add = dialog.findViewById(R.id.btn_add);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_apply = dialog.findViewById(R.id.btn_apply);


        //Widge Action Performed .i.e onClicked
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String expenseName = et_expense_name.getText().toString();
                String expenseAmount = et_expense_amount.getText().toString();
                ExpensesDTO expensesDTO = presenter.addNewExpense(expenseName, expenseAmount);
                et_expense_amount.setText("");
                et_expense_name.setText("");
                addExpenseArray.add(expensesDTO);
            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!addExpenseArray.isEmpty()) {        //checking whether if the array is empty or not

                    //if not empty saving it in database
                    presenter.saveNewExpenseToFirebase(addExpenseArray, time);
                    // retriveMyExpense();
                    dialog.dismiss();
                    expenseFlag = 0;

                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = d.getWidth() * 1;

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mytripdetails_menu, menu);
        return true;
    }

    public void loadFriendExpense(String personName) {
        this.expenseDialogTitle = personName;
        presenter.loadFriendExpense(personName);

    }
}
