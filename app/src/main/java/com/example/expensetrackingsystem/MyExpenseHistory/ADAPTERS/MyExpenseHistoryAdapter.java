package com.example.expensetrackingsystem.MyExpenseHistory.ADAPTERS;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackingsystem.MyExpenseHistory.DTO.MyExpenseHisotryDTO;
import com.example.expensetrackingsystem.MyExpenseHistory.VIEW.MyExpenseHistoryView;
import com.example.expensetrackingsystem.R;

import java.util.ArrayList;

public class MyExpenseHistoryAdapter extends RecyclerView.Adapter{
    private final MyExpenseHistoryView context;
    private final ArrayList<MyExpenseHisotryDTO> array;
    TextView sn,particulars,price;

    public MyExpenseHistoryAdapter(MyExpenseHistoryView myExpenseHistoryView, ArrayList<MyExpenseHisotryDTO> myExpenseArray) {
        this.context = myExpenseHistoryView;
        this.array= myExpenseArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.myexpensehistory_containeer,parent,false);

        return new MyExpenseHistoryCustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        sn.setText(String.valueOf(position+1));
        particulars.setText(array.get(position).getParticulars());
        price.setText(array.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return array.size();


    }

    private class MyExpenseHistoryCustomHolder extends RecyclerView.ViewHolder {
        public MyExpenseHistoryCustomHolder(View view) {
            super(view);
            particulars = view.findViewById(R.id.tv_particulars);
            sn = view.findViewById(R.id.tv_sn);
            price = view.findViewById(R.id.tv_price);
        }
    }
}
