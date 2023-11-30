package com.labactivity.logmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewRecords extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    List<MainModel> mainModelList;

    ImageButton timeoutBtnCV, deleteBtnCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);


        recyclerView = findViewById(R.id.rv_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainModelList = new ArrayList<>();
        mainAdapter = new MainAdapter(mainModelList, this);
        recyclerView.setAdapter(mainAdapter);

        retrieveData();


    }

    private void retrieveData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Records");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mainModelList.clear();
                for (DataSnapshot dateSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot recordSnapshot : dateSnapshot.getChildren()) {
                        MainModel mainModel = recordSnapshot.getValue(MainModel.class);

                        if (mainModel != null) {
                            // Add log statements to inspect the data
                            Log.d("ViewRecords", "Date: " + mainModel.getDate());
                            Log.d("ViewRecords", "Name: " + mainModel.getName());
                            Log.d("ViewRecords", "ID_Num: " + mainModel.getId_Num());
                            Log.d("ViewRecords", "Time_In: " + mainModel.getTime_In());
                            Log.d("ViewRecords", "Time_Out: " + mainModel.getTime_Out());

                            mainModelList.add(mainModel);
                        }
                    }
                }
                mainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ViewRecords", "Error: " + error.getMessage());
                Toast.makeText(ViewRecords.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}