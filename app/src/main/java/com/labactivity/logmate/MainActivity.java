package com.labactivity.logmate;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ImageButton;

        import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    ImageButton addRecBtnHP, viewRecBtnHP, settingsBtnHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        addRecBtnHP = findViewById(R.id.addRecBtnHP);
        viewRecBtnHP = findViewById(R.id.viewRecBtnHP);
        settingsBtnHP = findViewById(R.id.settingsBtnHP);

        addRecBtnHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addRecordIntent = new Intent(MainActivity.this, AddRecord.class);
                startActivity(addRecordIntent);

            }
        });

        viewRecBtnHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewRecordIntent = new Intent(MainActivity.this, ViewRecords.class);
                startActivity(viewRecordIntent);
            }
        });
    }
}