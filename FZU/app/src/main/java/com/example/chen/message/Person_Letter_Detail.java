package com.example.chen.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.chen.fzu.R;

import java.util.ArrayList;
import java.util.List;

public class Person_Letter_Detail extends AppCompatActivity {

    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;

    private List<Msg> msgList = new ArrayList<Msg>();

    public class Msg {
        public static final int TYPE_RECEIVED = 0;
        public static final int TYPE_SEND = 1;

        private String content;
        private int type;

        public Msg(String content, int type) {
            this.content = content;
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public int getType() {
            return type;
        }
    }

    public class MsgAdapter extends ArrayAdapter<Msg> {
        private int resourceId;

        public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects) {
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            Msg msg = getItem(position);
            View view;
            ViewHolder viewHolder;
            if(convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
                viewHolder = new ViewHolder();
                viewHolder.leftLayout = (LinearLayout)view.findViewById(R.id.left_layout);
                viewHolder.rightLayout = (LinearLayout)view.findViewById(R.id.right_layout);
                viewHolder.leftMsg = (TextView)view.findViewById(R.id.left_msg);
                viewHolder.rightMsg = (TextView)view.findViewById(R.id.right_msg);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            if(msg.getType() == Msg.TYPE_RECEIVED) {
                viewHolder.leftLayout.setVisibility(View.VISIBLE);
                viewHolder.rightLayout.setVisibility(View.GONE);
                viewHolder.leftMsg.setText(msg.getContent());
            } else if(msg.getType() == Msg.TYPE_SEND) {
                viewHolder.rightLayout.setVisibility(View.VISIBLE);
                viewHolder.leftLayout.setVisibility(View.GONE);
                viewHolder.rightMsg.setText(msg.getContent());
            }
            return view;
        }

        class ViewHolder {
            LinearLayout leftLayout;
            LinearLayout rightLayout;
            TextView leftMsg;
            TextView rightMsg;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person__letter__detail);

        initMsgs();
        adapter = new MsgAdapter(Person_Letter_Detail.this, R.layout.msg_item, msgList);
        inputText = (EditText)findViewById(R.id.input_text);
        send = (Button)findViewById(R.id.send);
        msgListView = (ListView)findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if(!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsgs() {
        Msg msg1 = new Msg("Hello, how are you?", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Fine, thank you, and you?", Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3 = new Msg("I am fine, too!", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }

}
