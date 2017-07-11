package com.example.clearsky;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatRooms extends AppCompatActivity {

    private Button _addRoom;
    private TextView _roomName;
    private ListView _roomsLst;

    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfRooms = new ArrayList<>();

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rooms);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        _addRoom = (Button) findViewById(R.id.addRoom);
        _roomName = (TextView) findViewById(R.id.msgText);
        _roomsLst = (ListView) findViewById(R.id.roomsList);

        arrayAdapter =
            new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfRooms);

        _roomsLst.setAdapter(arrayAdapter);

        _addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(_roomName.getText().toString(), "");
                root.child("ChatRoom").updateChildren(map);
            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> tempLstOfRoom = new ArrayList<String>();

                for (DataSnapshot currRoom: dataSnapshot.child("ChatRoom").getChildren()) {
                    tempLstOfRoom.add(currRoom.getKey());
                }

                listOfRooms.clear();
                listOfRooms.addAll(tempLstOfRoom);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        _roomsLst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ChatRoom.class);
                intent.putExtra("room_name", ((TextView)view).getText().toString());
                intent.putExtra("user_name", User.getUserName());
                startActivity(intent);
            }
        });
    }
}
