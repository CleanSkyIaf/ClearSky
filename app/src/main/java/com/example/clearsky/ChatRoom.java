package com.example.clearsky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatRoom extends AppCompatActivity {

    private Button _sendButton;
    private TextView _msgText;
    private TextView _conversationChat;

    private String _chatMsg, _chatUserName;

    private String _userName, _roomName;

    private DatabaseReference root;
    private String temp_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        _sendButton = (Button) findViewById(R.id.sendButton);
        _msgText = (TextView) findViewById(R.id.msgText);
        _conversationChat = (TextView)findViewById(R.id.conversationText);

        _userName = getIntent().getExtras().getString("user_name");
        _roomName = getIntent().getExtras().getString("room_name");

        setTitle(" Room - " + _roomName);

        root = FirebaseDatabase.getInstance().getReference().child("ChatRoom").child(_roomName);

        _sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference msgRoot = root.child(temp_key);

                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", _userName);
                map2.put("msg", _msgText.getText().toString());

                msgRoot.updateChildren(map2);

                _msgText.setText("");
            }
        });

        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateChatConversation(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateChatConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateChatConversation(DataSnapshot dataSnapshot) {
        for (Iterator itr = dataSnapshot.getChildren().iterator(); itr.hasNext();) {
            _chatMsg = (String)((DataSnapshot)itr.next()).getValue();
            _chatUserName = (String)((DataSnapshot)itr.next()).getValue();

            _conversationChat.append(_chatUserName + " : " + _chatMsg + "\n ");
        }
    }
}
