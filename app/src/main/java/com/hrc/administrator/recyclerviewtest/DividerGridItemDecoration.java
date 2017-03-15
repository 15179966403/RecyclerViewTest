package com.hrc.administrator.recyclerviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * 适用于布局效果为GirdLayout的item的间隙绘制
 */

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration{
    private static final int[] ATTRS=new int[]{
            android.R.attr.listDivider
    };
    private Drawable mDivider;

    public DividerGridItemDecoration(Context context){
        final TypedArray a=context.obtainStyledAttributes(ATTRS);
        mDivider=a.getDrawable(0);
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c,parent);
        drawVertical(c,parent);
    }

    /**
     * 根据LayoutManager的不同，获取其列数
     * @param parent recyclerView
     * @return 该布局的列数
     */
    private int getSpanCount(RecyclerView parent){
        int spancount=-1;
        RecyclerView.LayoutManager manager=parent.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            spancount=((GridLayoutManager)manager).getSpanCount();
        }else if (manager instanceof StaggeredGridLayoutManager){
            spancount=((StaggeredGridLayoutManager)manager).getSpanCount();
        }
        return spancount;
    }

    /**
     * 绘制垂直方向的divider
     * @param c onDraw方法中的Canvas
     * @param parent RecyclerView
     */
    private void drawVertical(Canvas c, RecyclerView parent){
        int count=parent.getChildCount();
        for (int i=0;i<count;i++){
            final View child=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 绘制水平方向的Divider
     * @param c onDraw方法中的Canvas
     * @param parent RecyclerView
     */
    public void drawHorizontal(Canvas c,RecyclerView parent){
        int count=parent.getChildCount();
        for (int i=0;i<count;i++){
            final View child=parent.getChildAt(i);
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getLeft() - params.leftMargin;
            final int right = child.getRight() + params.rightMargin;
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 判断是否是最后一列
     * @param parent recyclerView控件
     * @param pos 子项下标
     * @param spanCount recyclerView的列数
     * @param childCount recyclerView的子项个数
     * @return true则是最后一列，false则相反
     */
    private boolean isLastColum(RecyclerView parent,int pos,int spanCount,int childCount){
        RecyclerView.LayoutManager layoutmanager=parent.getLayoutManager();
        if (layoutmanager instanceof GridLayoutManager){
            if((pos+1)%spanCount==0){  //如果是最后一列，则不用绘制右边
                return true;
            }
        }else if (layoutmanager instanceof StaggeredGridLayoutManager){
            int mOrientation=((StaggeredGridLayoutManager)layoutmanager).getOrientation();
            if (mOrientation==StaggeredGridLayoutManager.VERTICAL){
                if((pos+1)%spanCount==0){  //如果是最后一列，则不用绘制右边
                    return true;
                }
            }else if (mOrientation==StaggeredGridLayoutManager.HORIZONTAL){
                childCount=childCount-(childCount%spanCount);
                if (pos>=childCount){ //如果是最后一列，则不用绘制右边
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否为最后一行
     * @param parent recyclerView
     * @param pos 子项下标
     * @param spanCount 列数
     * @param childCount 子项个数
     * @return true则是最后一行,false则相反
     */
    private boolean isLastRow(RecyclerView parent,int pos,int spanCount,int childCount){
        RecyclerView.LayoutManager layoutmanager=parent.getLayoutManager();
        if (layoutmanager instanceof GridLayoutManager){
            childCount=childCount-childCount%spanCount;
            if (pos>=childCount){   //如果是最后一行，则不绘制底部
                return true;
            }
        }else if (layoutmanager instanceof StaggeredGridLayoutManager){
            int mOrientation=((StaggeredGridLayoutManager)layoutmanager).getOrientation();
            if (mOrientation==StaggeredGridLayoutManager.VERTICAL){
                childCount=childCount-(childCount%spanCount);
                if (pos>=childCount){
                    return true;
                }
            }else if (mOrientation==StaggeredGridLayoutManager.HORIZONTAL){
                if ((pos+1)==spanCount){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount=getSpanCount(parent);
        int childCount=parent.getAdapter().getItemCount();
        if (isLastRow(parent,parent.getChildAdapterPosition(view),spanCount,childCount)){//如果是最后一列，则不绘制右边
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }else if (isLastColum(parent,parent.getChildAdapterPosition(view),spanCount,childCount)){//如果是最后一行，则不绘制底部
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else{
            outRect.set(0,0,mDivider.getIntrinsicWidth(),mDivider.getIntrinsicHeight());
        }
    }
}
