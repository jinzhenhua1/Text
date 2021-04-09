package com.jzh.basemodule.view.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.jzh.basemodule.R;
import com.jzh.basemodule.bean.FileBean;

import java.io.File;
import java.util.List;

/**
 * Created by baiaj on 2016/8/22.
 * 意见反馈上传图片预览页面Adapter
 */
public class ShowImagePagerAdapter<T extends FileBean> extends PagerAdapter implements OnPhotoTapListener {

    private Context mContext;
    private List<T> fileList;
    private LayoutInflater inflater;
    // 点击事件回调接口
    private IOnBackListener mBackListener = null;

    private RequestOptions requestOptions;

    @Override
    public void onPhotoTap(ImageView view, float x, float y) {

    }

    public interface IOnBackListener {
        void onBack();
    }


    public ShowImagePagerAdapter(Context context, List<T> fileList) {
        this.mContext = context;
        this.fileList = fileList;
        this.inflater = LayoutInflater.from(mContext);
        requestOptions = new RequestOptions()
//                .error(R.mipmap.es_glide_pic_error)  //加载错误时显示该图
//                .placeholder(R.mipmap.es_glide_pic_loading) //加载时显示该图
//                .override(300,300) //指定大小  Target.SIZE_ORIGINAL代表原始大小
//                .circleCrop() //圆形图片
                .skipMemoryCache(true)//禁用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);//缓存方式
    }


    @Override
    public int getCount() {
        return fileList == null ? 0 : fileList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
         View view = inflater.inflate(R.layout.activity_show_image_item, null);
         PhotoView photoView = view.findViewById(R.id.show_image_activity_photo_view);

        String path = fileList.get(position).getPath();
        // 调用Picasso加载图片
        if (TextUtils.isEmpty(path)) {
            photoView.setImageResource(R.mipmap.ic_full_image_failed);
        } else {
//            path = "http://121.201.118.85:8079/logmedia/photo/" + name;
            if(path.startsWith("http")){
                Glide.with(mContext).load(path).apply(requestOptions).into(photoView);
            }else{
                File file = new File(path);
                Glide.with(mContext).load(file).apply(requestOptions).into(photoView);
            }
        }
        container.addView(view);
        photoView.setOnPhotoTapListener(this);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setOnBackListener(IOnBackListener backListener) {
        if (backListener != null) {
            this.mBackListener = backListener;
        }
    }

    // 单击图片事件


    // 单击图片之外的view的事件


    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}
