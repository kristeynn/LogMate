package com.labactivity.logmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class AddRecord extends AppCompatActivity {

    EditText nameInpAR, idnumInpAR, dateInpAR, timeinInpAR, timeoutInpAR;
    ImageButton addRecBtnAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        nameInpAR = findViewById(R.id.nameInpAR);
        idnumInpAR = findViewById(R.id.idnumInpAR);
        dateInpAR = findViewById(R.id.dateInpAR);
        timeinInpAR = findViewById(R.id.timeinInpAR);
        timeoutInpAR = findViewById(R.id.timeoutInpTL);
        addRecBtnAR = findViewById(R.id.addRecBtnAR);

        dateInpAR.setOnClickListener(view -> showCalendar());
        timeinInpAR.setOnClickListener(view -> showClockTimeIn());
        timeoutInpAR.setOnClickListener(view -> showClockTimeOut());

        addRecBtnAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameAR = nameInpAR.getText().toString().trim();
                String idnumAR = idnumInpAR.getText().toString().trim();
                String dateAR = dateInpAR.getText().toString().trim();
                String timeInAR = timeinInpAR.getText().toString().trim();
                String timeOutAR = timeoutInpAR.getText().toString().trim();

                if (isValidInput(nameAR, idnumAR, dateAR, timeInAR)) {
                    ar_AddRecord(nameAR, idnumAR, dateAR, timeInAR, timeOutAR);
                } else {
                    Toast.makeText(AddRecord.this, "Please enter valid information.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidInput(String name, String idNum, String date, String timeIn) {
        if (name.isEmpty() || idNum.isEmpty() || date.isEmpty() || timeIn.isEmpty()) {
            // Display a message or handle the case where any of the required fields is empty
            return false;
        }
        // You can add more specific validation checks if needed
        return true;
    }

    private void showCalendar() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1;
                    dateInpAR.setText(selectedDate);
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void showClockTimeIn() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minuteOfDay) -> {
                    String selectedTime = hourOfDay + ":" + minuteOfDay;
                    timeinInpAR.setText(selectedTime);
                },
                hour, minute, false);

        timePickerDialog.show();
    }

    private void showClockTimeOut() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minuteOfDay) -> {
                    String selectedTime = hourOfDay + ":" + minuteOfDay;
                    timeoutInpAR.setText(selectedTime);
                },
                hour, minute, false);

        timePickerDialog.show();
    }

    private void ar_AddRecord(String nameAR, String idnumAR, String dateAR, String timeInAR, String timeOutAR) {
        Log.d("AddRecord", "ar_AddRecord called");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tbl_reference = database.getReference("Records");

        // Sanitize the ID_Num before using it as a key
        String sanitizedIdNum = sanitizeKey(idnumAR);

        String dateDB = dateInpAR.getText().toString();
        DatabaseReference date_reference = tbl_reference.child(dateAR);

        // Generate a unique key for the record
        String recordId = date_reference.push().getKey();

        HashMap<String, Object> record = new HashMap<>();
        record.put("RecordId", recordId);  // Add RecordId to the record
        record.put("ID_Num", sanitizedIdNum);
        record.put("Name", nameAR);
        record.put("Date", dateAR);
        record.put("Time_In", timeInAR);
        record.put("Time_Out", timeOutAR);

        Log.d("AddRecord", "Before setValue");
        date_reference.child(recordId).setValue(record).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("AddRecord", "Inside onComplete");
                if (task.isSuccessful()) {
                    Log.d("AddRecord", "Record Inserted Successfully!");
                    Toast.makeText(AddRecord.this, "Record Inserted Successfully!", Toast.LENGTH_SHORT).show();
                    nameInpAR.getText().clear();
                    idnumInpAR.getText().clear();
                    dateInpAR.getText().clear();
                    timeinInpAR.getText().clear();
                    timeoutInpAR.getText().clear();
                } else {
                    Log.e("AddRecord", "Error: " + task.getException().getMessage());
                    Toast.makeText(AddRecord.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Function to sanitize the key
    private String sanitizeKey(String originalKey) {
        // Replace invalid characters with underscores
        return originalKey.replaceAll("[/\\.#$\\[\\]]", "_");
    }
}
