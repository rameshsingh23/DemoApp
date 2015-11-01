package com.smaple.socialevening;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class ChallengeActivity extends AppCompatActivity {

    ViewPager mViewPager;
    GroupPagerAdapter adapter;
    String[] challenges = {"Challeneg 1", "Challeneg 2", "Challeneg 3", "Challeneg 4", "Challeneg 5",
                            "Challeneg 6", "Challeneg 7", "Challeneg 8", "Challeneg 9", "Challeneg 10"};

    int selectedPosition = 0;
    Button mChallengeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        mViewPager = (ViewPager)findViewById(R.id.myviewpager);
        mChallengeButton = (Button)findViewById(R.id.challengeButton);
        adapter = new GroupPagerAdapter();
        mViewPager.setAdapter(adapter);
        mChallengeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallengeActivity.this, ChallengeNotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(ChallengeActivity.this, 0, intent, 0);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.notificationlogo)
                        .setContentTitle("New Challenge")
                        .setContentIntent(pendingIntent)
                        .setContentText("Received a new challenge : " + challenges[selectedPosition]);

                NotificationManager mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mManager.notify(selectedPosition, mBuilder.build());
            }
        });
    }

    class GroupPagerAdapter extends PagerAdapter{

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return challenges.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            selectedPosition = position;
            LayoutInflater inflater = (LayoutInflater) ChallengeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.challenge_row, null);
            TextView challenge = (TextView)view.findViewById(R.id.challengeText);
            //Button challengeButton = (Button)view.findViewById(R.id.challengeButton);
            challenge.setText("Challenge them to " + challenges[position]);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
