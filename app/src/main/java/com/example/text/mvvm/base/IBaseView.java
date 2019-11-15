package com.example.text.mvvm.base;

public interface IBaseView {

    /**
     * 显示错误信息
     * @param msg
     */
    public void  showError(String msg);

    public void  showContent(String message, int duration);

    /**
     * 取消加载进度
     */
    public void  dismissLoading();

    /**
     * 显示加载进度
     * @param loadingText 提示信息
     */
    public void  showLoadingDialog(String loadingText);

    /**
     * 显示加载进度
     */
    public void showLoading();

    /**
     * 显示提示框
     * @param msg
     */
    public void  showInquiryDialog(String msg);

    /**
     * 显示确定提示框
     * @param title         提示标题
     * @param message       提示内容
     * @param buttonConfirm 确定按钮文字
     * @param buttonCancel  取消按钮文字
     */
    public void showConfirmDialog(String title, String message, String buttonConfirm, String buttonCancel);

    /**
     * 退出程序
     */
    void exit();

    /**
     * 给 ViewDataBinding注入ViewModel
     */
    default void bindViewModel() {
    }


    /**
     * Inject Dagger component
     */
//    default void initInjector(AppComponent appComponent) {
//    }
}