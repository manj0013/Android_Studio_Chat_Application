package com.cst2335.test_application;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
public class ChatRoomActivity extends AppCompatActivity {

    private ArrayList<Message> elements = new ArrayList<>();
    private Message message;
    private MyListAdapter myListAdapter;

    private EditText editText;
    private SQLiteDatabase db;
//    private long new_Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        Button send;
        Button receive;
        send = findViewById(R.id.Send);
        receive = findViewById(R.id.Receive);
        editText = findViewById(R.id.Msg_Box);
        FrameLayout frameLayout= findViewById(R.id.frameLayout);
        //data from database
        import_Data();

        send.setOnClickListener(view -> {
            String msg = editText.getText().toString();
            int msg_Type = 0;
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyOpener.col_Message, msg);
            contentValues.put(MyOpener.col_Send_Rec, msg_Type);
            long id = db.insert(MyOpener.table_Name, null, contentValues);
            message = new Message(msg, msg_Type, id);
            elements.add(message);
            myListAdapter.notifyDataSetChanged();
            editText.getText().clear();
        });
        receive.setOnClickListener(view -> {
            String msg = editText.getText().toString();
            int msg_Type = 1;
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyOpener.col_Message, msg);
            contentValues.put(MyOpener.col_Send_Rec, msg_Type);
            long id = db.insert(MyOpener.table_Name, null, contentValues);
            message = new Message(msg, msg_Type, id);
            elements.add(message);
            myListAdapter.notifyDataSetChanged();
            editText.getText().clear();
        });
        ListView myList = findViewById(R.id.listview);
        myList.setAdapter(myListAdapter = new MyListAdapter());
        myList.setOnItemLongClickListener((list,view,position,id)->{
            startActivity(Intent intent );
            return true;
        });
    }
    protected void delete_Msg(Message msg) {
        db.delete(MyOpener.table_Name, MyOpener.col_Id + "= ?", new String[]{Long.toString(msg.getId())});
    }

    protected void print_Cursor(Cursor cursor, int version) {
        Log.e("Database Version", String.valueOf(db.getVersion()));
        Log.e(" Column name", String.valueOf(cursor.getColumnNames()));
        Log.e("Number Of Columns", String.valueOf(cursor.getColumnCount()));
        Log.e("Number Of Rows", String.valueOf(cursor.getCount()));

    }
    public void import_Data() {
        MyOpener myOpener = new MyOpener(this);
        db = myOpener.getWritableDatabase();

        String[] columns = {MyOpener.col_Id, MyOpener.col_Message, MyOpener.col_Send_Rec};

        Cursor result = db.query(false, MyOpener.table_Name, columns, null, null, null, null, null, null);
        print_Cursor(result,1);

        int msg_Column_index = result.getColumnIndex(MyOpener.col_Message);
        int msg_Type_Column_index = result.getColumnIndex(MyOpener.col_Send_Rec);
        int id_Column_index = result.getColumnIndex(MyOpener.col_Id);

        while (result.moveToNext()) {
            String msg = result.getString(msg_Column_index);
            int msg_Type = result.getInt(msg_Type_Column_index);
            long id = result.getLong(id_Column_index);
            //
            elements.add(new Message(msg, msg_Type, id));
        }


    }

    private class MyListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Message getItem(int position) {

            //return message.getMessage();
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            //RecyclerView.ViewHolder holder;
            int msg_Type = elements.get(position).get_Msg_Type();
            LayoutInflater inflater = getLayoutInflater();
            View newView = null;
            TextView textView = null;

            if (msg_Type == 0) {
                newView = inflater.inflate(R.layout.msg_image, viewGroup, false);
                textView = newView.findViewById(R.id.text_view_send);

            } else if (msg_Type== 1) {
                newView = inflater.inflate(R.layout.msg_image_right, viewGroup, false);
                textView = newView.findViewById(R.id.text_view_receive);

            }
            textView.setText(getItem(position).getMsg());

            return newView;
        }



    }
}