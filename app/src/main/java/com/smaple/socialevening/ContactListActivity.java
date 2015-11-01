package com.smaple.socialevening;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity{

    private static int CONTACT_LOAD = 1000;
    List<String> nameList = new ArrayList<String>();
    List<String> phoneList = new ArrayList<String>();
    ListView mContactList;
    Button mSubmit;
    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        mContactList = (ListView)findViewById(R.id.contactList);
        mSubmit = (Button)findViewById(R.id.button1);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactNumber = "";
                for (int i = 0; i < nameList.size(); i++) {
                    if (contactAdapter.mCheckStates.get(i) == true) {
                        contactNumber += phoneList.get(i) + ";";
                    }
                }
                if (!contactNumber.isEmpty()) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    intent.putExtra("address", contactNumber);
                    intent.putExtra("sms_body", "Download link");
                    intent.setType("vnd.android-dir/mms-sms");
                    startActivity(intent);
                }
            }
        });
        new LoadContact().execute(null, null, null);
    }

    class LoadContact extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params) {
            loadContacts();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            contactAdapter = new ContactAdapter(ContactListActivity.this);
            mContactList.setAdapter(contactAdapter);
        }
    }

    private void loadContacts(){

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);
        while (cursor.moveToNext()) {
            String name =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            nameList.add(name);
            phoneList.add(phoneNumber);
        }
        /*ContentResolver resolver = this.getContentResolver();
        Cursor cur = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0)
        {
            while (cur.moveToNext())
            {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                nameList.add(name);
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = resolver.query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id},
                            null);
                    while (pCur.moveToNext())
                    {
                        String phoneNumber = pCur.getString(pCur.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phoneList.add(phoneNumber);
                    }
                    pCur.close();
                }
            }
        }*/
    }

    class ContactAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener{
        private SparseBooleanArray mCheckStates;
        private Context mContext;
        LayoutInflater inflater;

        public ContactAdapter(Context context){
            mContext = context;
            mCheckStates = new SparseBooleanArray(nameList.size());
            inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return nameList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.contact_list_row, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView)convertView.findViewById(R.id.textView1);
                holder.checkbox = (CheckBox)convertView.findViewById(R.id.checkBox1);
                holder.checkbox.setTag(position);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.name.setText(nameList.get(position));
            holder.checkbox.setChecked(mCheckStates.get(position));
            holder.checkbox.setOnCheckedChangeListener(this);
            return convertView;
        }

        class ViewHolder{
            TextView name;
            CheckBox checkbox;
        }

        public boolean isChecked(int position) {
            return mCheckStates.get(position, false);
        }

        public void setChecked(int position, boolean isChecked) {
            mCheckStates.put(position, isChecked);
        }

        public void toggle(int position) {
            setChecked(position, !isChecked(position));
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mCheckStates.put((Integer) buttonView.getTag(), isChecked);
        }
    }
}
