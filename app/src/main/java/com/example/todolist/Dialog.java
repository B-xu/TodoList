package com.example.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class Dialog extends DialogFragment {
    public static final String EXTRA_DATE = "extra";
    private static final String ARGS ="date";
    private Date mDate;
    private DatePicker mDatePicker;
    public static Dialog newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable(ARGS, date);
        Dialog fragment = new Dialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Date date  = (Date) getArguments().getSerializable(ARGS);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day  = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker, null);

        mDatePicker = (DatePicker) v.findViewById(R.id.date_picker);
        mDatePicker.init(year, month, day,null);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Date:")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year  = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day  = mDatePicker.getDayOfMonth();

                        Date date  = new GregorianCalendar(year, month, day).getTime();
                        sendResult(date);
                    }

                })
                .create();
    }

    private void sendResult(Date date) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);
        getTargetFragment().onActivityResult(0, getTargetRequestCode(), intent);
    }
}
