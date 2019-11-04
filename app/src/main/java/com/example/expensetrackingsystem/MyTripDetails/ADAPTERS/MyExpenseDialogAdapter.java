package com.example.expensetrackingsystem.MyTripDetails.ADAPTERS;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackingsystem.MyTripDetails.DTO.ExpensesDTO;
import com.example.expensetrackingsystem.MyTripDetails.VIEW.MyTripDetailsView;
import com.example.expensetrackingsystem.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyExpenseDialogAdapter extends RecyclerView.Adapter {
    private final MyTripDetailsView context;
    private final ArrayList<ExpensesDTO> expenseArray;
    private TextView tv_name;
    private TextView tv_amount;
    private TextView sn;

    public MyExpenseDialogAdapter(MyTripDetailsView myTripDetailsView, ArrayList<ExpensesDTO> expenseArray) {
        this.context = myTripDetailsView;
        this.expenseArray = expenseArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myexpense_dialog_rv_containeer,parent,false);
        return new myExpenseCustomHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        tv_name.setText(expenseArray.get(position).getExpenseName());
        tv_amount.setText(expenseArray.get(position).getExpenseAmount());
        sn.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return expenseArray.size();
    }

    private class myExpenseCustomHolder extends RecyclerView.ViewHolder {
        public myExpenseCustomHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_expense_name);
            tv_amount = view.findViewById(R.id.tv_expense_amount);
            sn = view.findViewById(R.id.tv_sn);
        }
    }
}
