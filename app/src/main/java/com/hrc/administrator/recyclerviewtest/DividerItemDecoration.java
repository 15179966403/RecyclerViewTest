package com.hrc.administrator.recyclerviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/3/14.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration{
    private static int[] ATTRS=new int[]{
            android.R.attr.listDivider
    };
    /**
     * 当前列表的orientation为horizontl
     */
    public static final int HORIZONTAL_LIST= LinearLayoutManager.HORIZONTAL;
    /**
     * 当前列表的orientation为vertical
     */
    public static final int VERTICAL_LIST=LinearLayoutManager.VERTICAL;
    //绘制的drawable
    private Drawable mDivider;
    //记录当前的orientation
    private int mOrientation;

    public DividerItemDecoration(Context context,int orientation){
        final TypedArray a=context.obtainStyledAttributes(ATTRS);
        mDivider=a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    //设置布局方式，如果不是横排或者竖排，则抛出异常
    private void setOrientation(int orientation) {
        if (orientation!=DividerItemDecoration.HORIZONTAL_LIST&&orientation!=DividerItemDecoration.VERTICAL_LIST){
            throw new IllegalArgumentException("invoild orientation");
        }
        mOrientation=orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation==VERTICAL_LIST){
            drawVertical(c,parent);
        }else{
            drawHorizontal(c,parent);
        }
    }

    /**
     * 当为垂直布局时，采用该方法绘制
     * @param c onDraw方法中的Canvas
     * @param parent onDraw方法中的RecyclerView
     */
    public void drawHorizontal(Canvas c,RecyclerView parent){
        final int top=parent.getPaddingTop();
        final int bottom=parent.getHeight()-parent.getPaddingBottom();
        int count=parent.getChildCount();
        for (int i=0;i<count;i++){
            final View child=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left=child.getWidth()+params.leftMargin;
            final int right=left+mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 当为水平布局时，采用该方法绘制
     * @param c onDraw方法中的Canvas
     * @param parent onDraw方法中的RecyclerView
     */
    public void drawVertical(Canvas c,RecyclerView parent){
        final int left=parent.getPaddingLeft();
        final int right=parent.getWidth()-parent.getPaddingRight();
        int count=parent.getChildCount();
        for (int i=0;i<count;i++){
            final View child=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top=child.getBottom()+params.bottomMargin;
            final int bottom=top+mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation==VERTICAL_LIST){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }
    }
}
