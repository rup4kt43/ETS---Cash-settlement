package com.example.expensetrackingsystem.MyTrip.ADAPTER;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetrackingsystem.MyExpenseHistory.DTO.MyExpenseHisotryDTO;
import com.example.expensetrackingsystem.MyTrip.DTO.MemberDetailsDTO;
import com.example.expensetrackingsystem.MyTrip.DTO.MyTripDTO;
import com.example.expensetrackingsystem.MyTrip.VIEW.MyTripView;
import com.example.expensetrackingsystem.R;
import com.example.expensetrackingsystem.Utilities.Global;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyTripCustomAdapter extends RecyclerView.Adapter {
    private final MyTripView context;
    private final ArrayList<MyTripDTO> myTripDetails;
    private TextView locFrom, locTo, DateFrom, DateTo;
    private LinearLayout ll_details;
    public static int count = 0;
    String date;
    int selectedPos;
    String positiveMessage = "OK";
    String msg = "Set this trip as the current trip";

    private CardView cv_details, cv_addMember;
    private ArrayList<MemberDetailsDTO> memberDetailsArray;


    public MyTripCustomAdapter(MyTripView myTripView, ArrayList<MyTripDTO> tripDetails, int selectedPos) {
        this.context = myTripView;
        this.myTripDetails = tripDetails;
        memberDetailsArray = new ArrayList<>();
        this.selectedPos = selectedPos;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mytrip_rv_containeer, parent, false);
        return new myTripCustomHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (selectedPos != 256) {
            if (selectedPos == position) {
                ll_details.setBackgroundColor(-290);
                setText(position);
            } else {
                setText(position);
            }
        } else {
            setText(position);
        }

    }

    private void setText(int position) {
        locFrom.setText(String.valueOf(myTripDetails.get(position).getLocationFrom()));
        locTo.setText(String.valueOf(myTripDetails.get(position).getLocationTo()));
        DateFrom.setText(String.valueOf(myTripDetails.get(position).getFromDate()));
        DateTo.setText(String.valueOf(myTripDetails.get(position).getToDate()));
    }

    @Override
    public int getItemCount() {
        return myTripDetails.size();
    }

    private class myTripCustomHolder extends RecyclerView.ViewHolder {
        public myTripCustomHolder(View view) {
            super(view);
            locFrom = view.findViewById(R.id.tv_locationFrom);
            locTo = view.findViewById(R.id.tv_locationTo);
            DateFrom = view.findViewById(R.id.tv_dateFrom);
            DateTo = view.findViewById(R.id.tv_dateTo);
            ll_details = view.findViewById(R.id.ll_details);


            cv_addMember = view.findViewById(R.id.cv_addMember);
            cv_addMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    createDialogueBox(pos);
                }
            });


            cv_details = view.findViewById(R.id.cv_details);
            cv_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String locFrom = myTripDetails.get(pos).getLocationFrom();
                    String locTo = myTripDetails.get(pos).getLocationTo();
                    String dateFrom = myTripDetails.get(pos).getFromDate();
                    String dateTo = myTripDetails.get(pos).getToDate();
                    String time = myTripDetails.get(pos).getTime();
                    Global.cardSelectedDate = time;


                    context.switchToMyTripDetails(locFrom, locTo, dateFrom, dateTo, time);

                }
            });

            //on Long press listener to change the coolor and set selected
            cv_details.setOnLongClickListener(new View.OnLongClickListener() {


                @Override
                public boolean onLongClick(final View view) {


                    final int pos = getAdapterPosition();
                    if (selectedPos == pos) {
                        positiveMessage = "CONFIRM";
                        msg = "Complete Trip?";
                    }

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(msg);
                    builder.setPositiveButton(positiveMessage, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if (msg.matches("Set this trip as the current trip")) {
                                if (count == 0) {
                                    view.setBackgroundColor(-290);
                                    if (MyTripView.isFriend == 1) {
                                        DatabaseReference selectedTrip = Global.mDatabase.child("SelectedTrip").child(Global.friendTripUserPhone).child(Global.userName);
                                        selectedTrip.setValue(myTripDetails.get(pos).getTime());
                                    } else {
                                        DatabaseReference selectedTrip = Global.mDatabase.child("SelectedTrip").child(Global.userPhone).child(Global.userName);
                                        selectedTrip.setValue(myTripDetails.get(pos).getTime());

                                        count = 1;
                                    }
                                } else {

                                    Toast.makeText(context, "Please Confirm or cancel the previous selected current Trip!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                final ArrayList<MyExpenseHisotryDTO> myExpenseArray = new ArrayList<>();
                                DatabaseReference expenses = Global.mDatabase.child("TRIP LIST")
                                        .child(Global.userPhone)
                                        .child(myTripDetails.get(pos).getTime())
                                        .child("Expenses");
                                expenses.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot nameKey : dataSnapshot.getChildren()) {
                                            String name = nameKey.getKey();
                                            for (DataSnapshot detail : nameKey.getChildren()) {
                                                MyExpenseHisotryDTO myExpenseHisotryDTO = new MyExpenseHisotryDTO();
                                                myExpenseHisotryDTO.setParticulars(detail.getKey());
                                                myExpenseHisotryDTO.setPrice(detail.getValue().toString());
                                                myExpenseHisotryDTO.setName(name);
                                                myExpenseArray.add(myExpenseHisotryDTO);

                                            }

                                            addToCompletedTrip(myExpenseArray, pos);


                                            //LEft for testing
                                            /*myTripDetails.remove(pos);
                                            notifyDataSetChanged();*/


                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    builder.show();


                    return true;
                }
            });

           /* cv_addMember = view.findViewById(R.id.cv_addMember);
            cv_addMember.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    createDialogueBox(pos);

                }
            });*/


        }
    }

    private void addToCompletedTrip(ArrayList<MyExpenseHisotryDTO> myExpenseArray, int pos) {

        DatabaseReference completedTrip = Global.mDatabase.child("Completed Trip")
                .child(Global.userPhone).child(myTripDetails.get(pos).getTime());

        for (int i = 0; i < myExpenseArray.size(); i++) {
            completedTrip.child(myExpenseArray.get(i).getName()).child(myExpenseArray.get(i).getParticulars()).setValue(myExpenseArray.get(i).getPrice());
        }

        DatabaseReference myTrip = Global.mDatabase.child("TRIP LIST").child(Global.userPhone)
                .child(myTripDetails.get(pos).getTime());
        myTrip.removeValue();


    }

    private void createDialogueBox(final int pos) {

        final EditText personName, personPhone;
        Button btn_apply, btn_add, btn_cancel;

        final Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.add_member_custom_dialog);

        personName = dialog.findViewById(R.id.et_personName);
        personPhone = dialog.findViewById(R.id.et_personPhone);

        btn_add = dialog.findViewById(R.id.btn_add);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);


        //save file to database and retrive to set on recycler v iew

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = personName.getText().toString();
                String phone = personPhone.getText().toString();


                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(context, "Field cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference userDetail = Global.mDatabase.child("USER DETAIL").child(phone).child("Request");
                    userDetail.child(Global.userPhone).setValue(myTripDetails.get(pos).getTime());
                    dialog.dismiss();
                    Toast.makeText(context, "Member Request Sent.", Toast.LENGTH_SHORT).show();

                   /* MemberDetailsDTO memberDetailsDTO = new MemberDetailsDTO();
                    memberDetailsDTO.setPersonName(name);
                    memberDetailsDTO.setPersonPhone(phone);
                    memberDetailsArray.add(memberDetailsDTO);
                    personName.setText("");
                    personPhone.setText("");*/
                }
            }
        });


        btn_apply = dialog.findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!memberDetailsArray.isEmpty()) {
                    context.addMemberToMyTrip(myTripDetails.get(pos).getTime(), memberDetailsArray);
                    dialog.dismiss();
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
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = d.getWidth() * 1;

        dialog.show();
    }
}
