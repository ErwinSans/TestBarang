package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateBarang extends AppCompatActivity {

    private DatabaseReference database;

    TextView kode;
    TextView nama;
    Button update;
    Button cancel;

    Barang barang;
    String mainKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_barang);

        update = findViewById(R.id.btnOk);
        cancel = findViewById(R.id.btnCancel);

        kode = findViewById(R.id.editNo);
        nama = findViewById(R.id.editNama);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String namaValue = extras.getString("nama");
            String idValue = extras.getString("id");
            String key = extras.getString("key");
            mainKey = key;

            kode.setText(idValue);
            nama.setText(namaValue);

        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barang = new Barang(kode.getText().toString(), nama.getText().toString());
                updateBarang(barang, mainKey);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LihatBarang.class);
                startActivity(intent);
            }
        });
    }

    private void updateBarang(Barang barang, String oldKode) {
        database = FirebaseDatabase.getInstance().getReference();
        database.child("Barang").child(oldKode).setValue(barang).addOnSuccessListener(this,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(getApplicationContext(), LihatBarang.class);
                        Toast.makeText(getApplicationContext(), "Data berhasil diperbaharui",
                                Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                });

    }
}
