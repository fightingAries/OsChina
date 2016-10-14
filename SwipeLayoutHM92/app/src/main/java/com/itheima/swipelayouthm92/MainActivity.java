package com.itheima.swipelayouthm92;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private ListView lv_msg_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		init();
		GooView gooView=new GooView(this);
		setContentView(gooView);
	}

	private void init() {
		initView();
		initData();
	}

	private void initData() {
		List<Msg> msgList=new ArrayList<Msg>();
		for (int i = 0; i < Cheeses.NAMES.length; i++) {
			Msg msg=new Msg(Cheeses.NAMES[i],Cheeses.CHEESE_STRINGS[i]);
			msgList.add(msg);
		}
		lv_msg_list.setAdapter(new MsgAdapter(msgList));
	}

	private void initView() {
		lv_msg_list = (ListView) findViewById(R.id.lv_msg_list);
	}
}
