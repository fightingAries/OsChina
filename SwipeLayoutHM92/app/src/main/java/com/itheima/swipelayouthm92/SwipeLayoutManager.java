package com.itheima.swipelayouthm92;

/**
 * Created by ccy on 2016/10/6.
 */
public class SwipeLayoutManager {

	private static SwipeLayoutManager manager=new SwipeLayoutManager();
	private SwipeLayoutManager(){}
	public static SwipeLayoutManager getInstance(){
		return manager;
	}

	private SwipeLayout sl;
	//记住被滑开的sl
	public void setSwipeLayout(SwipeLayout sl){
		this.sl=sl;
	}
	//取出被记录的sl
	public SwipeLayout getSwipeLayout(){
		return sl;
	}

	public void clearSwipeLayout(){
		this.sl=null;
	}
}
