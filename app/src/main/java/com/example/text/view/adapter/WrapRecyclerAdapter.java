package com.example.text.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/8/18 16:56
 */
class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
//    private ArrayList<String> datas;
    private RecyclerView.Adapter mRealAdapter;//真实的列表适配器
    private ArrayList<View> mHeaderViews; // 头部
    private ArrayList<View> mFooterViews; // 底部


    public WrapRecyclerAdapter(RecyclerView.Adapter mRealAdapter,ArrayList<View> mHeaderViews,ArrayList<View> mFooterViews){
        this.mRealAdapter = mRealAdapter;
        this.mHeaderViews = mHeaderViews;
        this.mFooterViews = mFooterViews;
    }

    /**
     *
     * @param parent
     * @param position  重写了getItemViewType方法，这里传来的是下表，可直接根据下标来确定创建什么view
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        //头部
        if(position < mHeaderViews.size()){
            return createFooterHeaderViewHolder(mHeaderViews.get(position));
        }else if(position < mHeaderViews.size() + mRealAdapter.getItemCount()){
            //中部
            return mRealAdapter.onCreateViewHolder(parent, mRealAdapter.getItemViewType(position - mHeaderViews.size()));
        }else{
            //尾部
            return createFooterHeaderViewHolder(mFooterViews.get(position - mHeaderViews.size() - mRealAdapter.getItemCount()));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(position < mHeaderViews.size()){
            return;
        }else if(position < mHeaderViews.size() + mRealAdapter.getItemCount()){
            //中部
            mRealAdapter.onBindViewHolder(holder, position - mHeaderViews.size());
        }else{
            //尾部
            return;
        }
    }

    @Override
    public int getItemCount() {
        return mRealAdapter.getItemCount() + mHeaderViews.size() + mFooterViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    /**
     * 创建头尾的viewholder，头尾的viewholder不需要有东西，内容都在view里面
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder createFooterHeaderViewHolder(View view) {
        return new RecyclerView.ViewHolder(view) {};
    }

    /**
     * 添加底部View
     * @param view
     */
    public void addFooterView(View view) {
        if (!mFooterViews.contains(view)) {
            mFooterViews.add(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加头部View
     * @param view
     */
    public void addHeaderView(View view) {
        if (!mHeaderViews.contains(view)) {
            mHeaderViews.add(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除底部View
     * @param view
     */
    public void removeFooterView(View view) {
        if (mFooterViews.contains(view)) {
            mFooterViews.remove(view);
            notifyDataSetChanged();
        }
    }

    /**
     * 移除头部View
     * @param view
     */
    public void removeHeaderView(View view) {
        if (mHeaderViews.contains(view)) {
            mHeaderViews.remove(view);
            notifyDataSetChanged();
        }
    }

}
