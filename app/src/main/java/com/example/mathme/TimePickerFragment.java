package com.example.mathme;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.text.format.DateFormat;
import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceActivity) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //return new dialog
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        SettingActivity activity = (SettingActivity) getActivity();
        assert activity != null;
        //passes the chosen time back to the settings activity
        activity.processTimePicked(hourOfDay, minute);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        SettingActivity activity = (SettingActivity) getActivity();
        assert activity != null;
        activity.timeDialogCanceled();
    }
}
