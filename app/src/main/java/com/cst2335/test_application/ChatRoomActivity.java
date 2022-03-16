package com.cst2335.test_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<String> elements = new ArrayList<>(Arrays.asList());
    private Message message = new Message();
    private MyListAdapter myListAdapter;
    private Button send;
    private Button receive;
    private EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        send = findViewById(R.id.Send);
        receive = findViewById(R.id.Receive);
        editText = findViewById(R.id.Msg_Box);


        send.setOnClickListener(view -> {
            elements.add(message.getMessage());
            message.setMessageType(1);
            editText.getText().clear();
            myListAdapter.notifyDataSetChanged();
        });
        receive.setOnClickListener(view -> {
            elements.add(message.getMessage());
            message.setMessageType(0);
            editText.getText().clear();
            myListAdapter.notifyDataSetChanged();
        });
        ListView myList = (ListView) findViewById(R.id.listview);
        myList.setAdapter(myListAdapter = new MyListAdapter());
        myList.setOnItemLongClickListener((list,view,pos, id) -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Do you want to delete this message")
                    .setMessage("The selected row is:" + pos + "/nThe database id is:" + id)
                    .setPositiveButton("yes",(click, arg)->{
                        elements.remove(pos);
                        myListAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("no", (click, arg)->{}).create().show();
            return true;
        });
    }

    private class Message {
        // 1 for send, 0 for receive
        private int msgType;
        public String getMessage() {
            return editText.getText().toString();
        }

        public void setMessageType(int i){
            msgType = i;
        }
        public int getMessageType(){
            return msgType;
        }

    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int position) {

            //return message.getMessage();
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            //RecyclerView.ViewHolder holder;
            LayoutInflater inflater = getLayoutInflater();
            View newView = view;
            TextView textView = null;
            if(view==null) {
                if (message.getMessageType() == 1) {
                    newView = inflater.inflate(R.layout.msg_image, viewGroup, false);
                    textView = newView.findViewById(R.id.text_view_send);
                    textView.setText((String) getItem(position));
//                    holder = new ;

                } else if (message.getMessageType() == 0) {
                    newView = inflater.inflate(R.layout.msg_image_right, viewGroup, false);
                    textView = newView.findViewById(R.id.text_view_receive);
                    textView.setText((String) getItem(position));

                }
            }

            return newView;
        }
    }
}