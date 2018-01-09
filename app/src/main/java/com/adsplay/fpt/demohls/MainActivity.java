package com.adsplay.fpt.demohls;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends Activity implements View.OnClickListener {

    private SimpleExoPlayerView simpleExoPlayerView;
    private PlayerManager playerManager;
    private Button btnChannel1, btnChannel2;

    //fb
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        simpleExoPlayerView = findViewById(R.id.videoView);
        btnChannel1=findViewById(R.id.btnChannel1);
        btnChannel2=findViewById(R.id.btnChannel2);
        playerManager = new PlayerManager(this, simpleExoPlayerView);
        playerManager.initializePlayer(simpleExoPlayerView);
        btnChannel1.setOnClickListener(this);
        btnChannel2.setOnClickListener(this);
    }

    private void runAds(final String video, String channel){
        myRef.child(channel).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                playerManager.prepareVideo(video, dataSnapshot.child("ads").getValue(String.class));
                Log.d("Linhtinh", "Value is: " + dataSnapshot.child("ads").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Linhtinh", "onCancelled: "+databaseError.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerManager.playVideo();
    }

    @Override
    protected void onPause() {
        playerManager.pauseVideo();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        playerManager.stopVideo();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnChannel1:
                runAds(AdsConfig.VIDEO_URL_HLS, AdsConfig.CHANNEL_1);
                playerManager.playVideo();
                break;
            case R.id.btnChannel2:
                runAds(AdsConfig.VIDEO_URL_HLS2, AdsConfig.CHANNEL_2);
                playerManager.playVideo();
                break;
        }
    }
}
