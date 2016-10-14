package com.itheima.swipelayouthm92;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ccy on 2016/10/6.
 */
public class GooView extends View{

	private Paint paint;
	private Path path;

	public GooView(Context context) {
		this(context,null);
	}

	public GooView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public GooView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setColor(Color.BLUE);
		path = new Path();
	}

	//绘制静态圆
	//固定圆圆心和半径
	private PointF stableCenter=new PointF(300f,300f);
	private float stableRadius=9f;
	//拖拽圆圆心和半径
	private PointF dragCenter=new PointF(200f,200f);
	private float dragRadius=9f;
	//固定圆的两个附着点
	private PointF[] stablePoints = new PointF[]{
			new PointF(200f, 300f),
			new PointF(200f, 350f),
	};
	//拖拽圆的两个附着点
	private PointF[] dragPoints = new PointF[]{
			new PointF(100f, 300f),
			new PointF(100f, 350f),
	};
	//控制点:取两组附着点的中点的连线的中点
	private PointF controlPoint = new PointF(150f, 325f);

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//绘制"腰"
		//1,找到一个起点
		path.moveTo(stablePoints[0].x,stablePoints[0].y);
		//2,绘制一条曲线
		path.quadTo(controlPoint.x,controlPoint.y,dragPoints[0].x,dragPoints[0].y);
		//3,绘制一条直线
		path.lineTo(dragPoints[1].x,dragPoints[1].y);
		//4,绘制一条曲线
		path.quadTo(controlPoint.x,controlPoint.y,stablePoints[1].x,stablePoints[1].y);
		//5,自动闭合
		canvas.drawPath(path,paint);
		canvas.drawCircle(stableCenter.x,stableCenter.y,stableRadius,paint);
		canvas.drawCircle(dragCenter.x,dragCenter.y,dragRadius,paint);
	}
}
