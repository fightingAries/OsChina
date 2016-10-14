package com.itheima.swipelayouthm92;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ccy on 2016/10/6.
 */
public class MsgAdapter extends BaseAdapter{
	private List<Msg> msgList;

	public MsgAdapter(List<Msg> msgList) {
		this.msgList=msgList;
	}

	@Override
	public int getCount() {
		return msgList==null?0:msgList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=View.inflate(parent.getContext(),R.layout.lv_swipelayout_item,null);
			holder=new ViewHolder();
			holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_content= (TextView) convertView.findViewById(R.id.tv_content);
			holder.tv_delete= (TextView) convertView.findViewById(R.id.tv_delete);
			convertView.setTag(holder);
		}else{
			holder= (ViewHolder) convertView.getTag();
		}
		Msg msg = msgList.get(position);
		holder.tv_name.setText(msg.getName());
		holder.tv_content.setText(msg.getContent());
		holder.tv_delete.setTag(position);
		holder.tv_delete.setOnClickListener(listener);
		return convertView;
	}

	private View.OnClickListener listener=new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			int position= (int) v.getTag();
			SwipeLayoutManager.getInstance().getSwipeLayout().close(false);
			msgList.remove(position);
			notifyDataSetChanged();
		}
	};

	private class ViewHolder{
		private TextView tv_name;
		private TextView tv_content;
		private TextView tv_delete;
	}
}
