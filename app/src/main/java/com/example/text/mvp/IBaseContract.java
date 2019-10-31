package com.example.text.mvp;

public class IBaseContract {
    public interface IBasePresenter {

        public void onStop();

        public void onDestory();

        /**
         * 判断 presenter 是否与 view 建立联系，防止出现内存泄露状况
         *
         * @return {@code true}: 联系已建立<br>{@code false}: 联系已断开
         */
        public boolean isViewAttach();
    }

    public interface IBaseView {

        public void  showError(String msg);

        public void  showLoading(String loadingText);

        public void  showContent();

        public void  dismissLoading();

        public void  showDialog(String msg);
    }

    public interface IBaseModel{
        public static final int STATUS_FAIL = -1;// 验证失败
        public static final int STATUS_SUCCESS = 0;// 成功
        public static final int STATUS_NORMAL = 1;// 正常状态
        public static final int STATUS_VERIFY_ING = 2;// 正常状态
    }
}

