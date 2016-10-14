package com.itheima.swipelayouthm92;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ccy on 2016/10/6.
 */
public class SwipeLayout extends FrameLayout{

	private ViewDragHelper helper;
	private View longView;
	private View shortView;
	private int shortWidth;
	private int longWidth;
	private int height;

	public SwipeLayout(Context context) {
		this(context,null);
	}

	public SwipeLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public SwipeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		helper = ViewDragHelper.create(this, callback);
	}

	private ViewDragHelper.Callback callback=new ViewDragHelper.Callback(){

		@Override
		public boolean tryCaptureView(View child, int pointerId) {
			return true;
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx) {
			if(child==longView){
				if(left<-shortWidth){
					left=-shortWidth;
				}else if(left>0){
					left=0;
				}
			}else{
				if(left<longWidth-shortWidth){
					left=longWidth-shortWidth;
				}else if(left>longWidth){
					left=longWidth;
				}
			}
			return left;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
			super.onViewPositionChanged(changedView, left, top, dx, dy);
			//当滑动long的时候,让short跟着走,反之亦然
			if(changedView==longView){
				//这个方法在将隐藏的界面滑动出来的时候,会造成没有即时刷新而不显示界面的问题
				shortView.offsetLeftAndRight(dx);
			}else{
				longView.offsetLeftAndRight(dx);
			}
			invalidate();
			//判断如果sl被滑开了,则存储sl
//			if(longView.getLeft()<0){
//				SwipeLayoutManager.getInstance().setSwipeLayout(SwipeLayout.this);
//			}

		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel) {
			super.onViewReleased(releasedChild, xvel, yvel);
			//在抬起手的瞬间将sl记住
			SwipeLayoutManager.getInstance().setSwipeLayout(SwipeLayout.this);
			if(longView.getLeft()<-shortWidth*0.5f){
				open();
			}else{
				close(true);
			}
		}
	};

	public void close(boolean isSmooth) {
		if(isSmooth) {
			if (helper.smoothSlideViewTo(longView, 0, 0)) {
				invalidate();
			}
		}else{
			//直接将条目关闭,即将条目直接强行摆放到初始位置
			longView.layout(0,0,longWidth,height);
			shortView.layout(longWidth,0,longWidth+shortWidth,height);
		}
	}

	private void open() {

		if(helper.smoothSlideViewTo(longView,-shortWidth,0)){
			invalidate();
		}
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
		if(helper.continueSettling(true)){
			invalidate();
		}else{
			if(longView.getLeft()==0) {
				SwipeLayoutManager.getInstance().clearSwipeLayout();
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN:
				//判断是否有sl被滑开,如果记录的sl和当前sl不一致,则关闭记录的sl
				SwipeLayoutManager manager = SwipeLayoutManager.getInstance();
				SwipeLayout lastSl = manager.getSwipeLayout();
				if(lastSl!=null){
					if(lastSl!=SwipeLayout.this){
						lastSl.close(true);
						return true;
					}
				}
				break;
			case MotionEvent.ACTION_MOVE:
				manager = SwipeLayoutManager.getInstance();
				lastSl = manager.getSwipeLayout();
				if(lastSl!=null){
					if(lastSl!=SwipeLayout.this) {
						//请求父ListView不要拦截事件
						requestDisallowInterceptTouchEvent(true);
						return true;
					}
				}
		}
		helper.processTouchEvent(event);
		return true;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return helper.shouldInterceptTouchEvent(ev);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		//将两个孩子摆放到对应的位置
		longView.layout(0,0,longWidth,height);
		shortView.layout(longWidth,0,longWidth+shortWidth,height);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		//TODO 健壮性处理
		shortView = getChildAt(0);
		longView = getChildAt(1);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		longWidth = longView.getMeasuredWidth();
		height = longView.getMeasuredHeight();
		shortWidth = shortView.getMeasuredWidth();

	}

	//记住上一个被滑开的sl,判断当前滑动的sl是否和上一个一致
	// 如果不一致,关闭上一个被滑开的sl
	//再次滑动当前的sl,滑开后,记住当前被滑开的sl


}
