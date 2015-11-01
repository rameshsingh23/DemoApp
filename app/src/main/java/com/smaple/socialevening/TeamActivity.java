package com.smaple.socialevening;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TeamActivity extends AppCompatActivity {

    ListView groupList;
    TypedArray icons;
    String[] groups;
    SocialGroupAdapter groupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        groupList = (ListView)findViewById(R.id.groupList);
        groups = getResources().getStringArray(R.array.social_group);
        icons = getResources().obtainTypedArray(R.array.social_group_icons);
        groupAdapter = new SocialGroupAdapter(this);
        groupList.setAdapter(groupAdapter);

        groupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TeamActivity.this, ChallengeActivity.class);
                startActivity(intent);
            }
        });
    }

    class SocialGroupAdapter extends BaseAdapter {

        LayoutInflater inflater;
        Context mContext;

        public SocialGroupAdapter(Context context){
            mContext = context;
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return groups.length;
        }

        @Override
        public Object getItem(int position) {
            return groups[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder itemHolder;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.group_list_row, parent, false);
                itemHolder = new ItemHolder();
                itemHolder.groupImage = (ImageView)convertView.findViewById(R.id.groupImage);
                itemHolder.groupName = (TextView)convertView.findViewById(R.id.groupName);
                itemHolder.join = (Button)convertView.findViewById(R.id.joinButton);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder)convertView.getTag();
            }

            itemHolder.groupName.setText(groups[position]);
            Drawable d = icons.getDrawable(position);
            itemHolder.groupImage.setImageBitmap(GraphicsUtil.getRoundedShape(((BitmapDrawable)d).getBitmap(), 60, 60));
            itemHolder.join.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "Thanks for joining team.", Toast.LENGTH_LONG).show();
                }
            });
            return convertView;
        }

        class ItemHolder {
            ImageView groupImage;
            TextView groupName;
            Button join;
        }
    }
}
