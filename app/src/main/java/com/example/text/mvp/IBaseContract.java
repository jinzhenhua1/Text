package com.example.text.mvp;

public class IBaseContract {
    interface IBasePresenter {

        public void onStop();

        public void onDestory();
    }

    interface IBaseView {

        public void  showError(String msg);

        public void  showLoading(String loadingText);

        public void  showContent();

        public void  dismissLoading();

        public void  showDialog(String msg);
    }

    interface IBaseModel{
        public static final int STATUS_FAIL = -1;// 验证失败
        public static final int STATUS_SUCCESS = 0;// 成功
        public static final int STATUS_NORMAL = 1;// 正常状态
        public static final int STATUS_VERIFY_ING = 2;// 正常状态
    }
}

