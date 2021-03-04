package com.huaweisoft.mvp.mvp.base;

public class IBaseContract {
    public interface IBasePresenter<T extends IBaseContract.IBaseView> {

        /**
         * 对应activity中的onStop
         */
        void onStop();

        /**
         * 对应activity中的onDestroy
         */
        void onDestroy();

        /**
         * 判断 presenter 是否与 view 建立联系，防止出现内存泄露状况
         *
         * @param view view层
         * @return {@code true}: 联系已建立<br>{@code false}: 联系已断开
         */
        void attachView(T view);
    }

    public interface IBaseView {

        /**
         * 提示信息
         * @param msg 提示文字
         */
        void  showToast(String msg);

        /**
         * 带文字的加载框
         * @param loadingText
         */
        void  showLoadingWithMessage(String loadingText);

        /**
         * 默认文字的加载框
         */
        void  showLoading();

        /**
         * 正中央的提示信息
         * @param message  提示文字
         * @param duration  显示时长
         */
        void  showContent(String message, int duration);

        /**
         * 关闭加载框
         */
        void  dismissLoading();

        /**
         * 显示提示框
         * @param msg
         */
        void  showInquiryDialog(String msg);

        /**
         * 显示确定提示框
         * @param title         提示标题
         * @param message       提示内容
         * @param buttonConfirm 确定按钮文字
         * @param buttonCancel  取消按钮文字
         */
        void showConfirmDialog(String title, String message, String buttonConfirm, String buttonCancel);
    }

    public interface IBaseModel{
        public static final int STATUS_FAIL = -1;// 验证失败
        public static final int STATUS_SUCCESS = 0;// 成功
        public static final int STATUS_NORMAL = 1;// 正常状态
        public static final int STATUS_VERIFY_ING = 2;// 正常状态

        public void onStop();

        public void onDestory();
    }
}

