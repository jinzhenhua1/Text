package com.example.text.view.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.text.R;

import java.util.List;

/**
 * <p></p>
 * <p></p>
 *
 * @author jinzhenhua
 * @version 1.0  ,create at:2020/8/19 10:00
 */
class TestAdapterHelperAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public TestAdapterHelperAdapter(@Nullable List<String> data){
        super(R.layout.item_recyclerview,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        /**
         * 给指定的组件添加内容
         * helper.setImageResource  设置图片资源
         * helper.setVisible    设置是否可见
         * helper.getView() 直接获取view对象
         */
        helper.setText(R.id.item_tv_name,item);

        //调用这个方法，可以给该子view设置点击事件，具体的事件是在adapter.setOnItemClickListener中实现
        //如果给多个子view添加了该事件，则需要在实现方法中区分是哪个子view。
        helper.addOnClickListener(R.id.item_tv_name);
    }
}
