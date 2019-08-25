package com.example.exit;



import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity<myRef, mListView> extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView zxing;
    String text;
    String[] split;
    String uid;
    String name;
    String gender;
    String f;
    private EditText bus;
    int a;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mRef = database.getReference().child("Buses").child("WB05C").child("Passengers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bus = (EditText) findViewById(R.id.editText4);

    }

    public void scan(View view) {

        zxing = new ZXingScannerView(getApplicationContext());
        setContentView(zxing);
        zxing.setResultHandler(this);
        zxing.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();

        zxing.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        text = result.getText();

        send(text);

        setContentView(R.layout.activity_main);
        zxing.resumeCameraPreview(this);

    }
    String text1;

    public void send( String te1) {

    //    Toast.makeText(getApplicationContext(), text1, Toast.LENGTH_LONG).show();
    text1=te1;

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(text1))
                {
                    mRef.child(text1).removeValue();
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }
}



