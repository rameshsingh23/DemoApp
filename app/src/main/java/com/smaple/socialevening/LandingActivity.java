package com.smaple.socialevening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LandingActivity extends AppCompatActivity {

    Button mAroundYou;
    Button mTeamMission;
    Button mRewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mTeamMission = (Button)findViewById(R.id.teamMission);
        mRewards = (Button)findViewById(R.id.rewards);
        mAroundYou = (Button)findViewById(R.id.aroundYou);
        mTeamMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LandingActivity.this, "0/3 challenges completed", Toast.LENGTH_SHORT).show();
            }
        });

        mAroundYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, TeamActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_group:
                createGroup();
                return true;
            case R.id.add_teammate:
                addTeamMate();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createGroup() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    private void addTeamMate(){
        Intent intent = new Intent(this, ContactListActivity.class);
        startActivity(intent);
    }
}
