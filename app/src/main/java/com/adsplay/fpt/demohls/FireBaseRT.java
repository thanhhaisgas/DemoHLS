package com.adsplay.fpt.demohls;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by haint on 08/01/2018.
 */

public class FireBaseRT {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference("ads");
    private static String value = "";

    public static String getDelivery(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                Log.d("Linhtinh", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Linhtinh", "onCancelled: "+databaseError.getMessage());
            }
        });
        return value;
    }
}
