package com.jzh.mvp.mvp.listener;

/**
 * @author jinzhenhua
 * @version 1.0  2019-11-15 17:01:28
 */
public interface IListener {
    /**
     * Inquiry dialog click confirm
     */
    default void onInquiryConfirm() {
    }

    /**
     * Inquiry dialog click cancel
     */
    default void onInquiryCancel() {
    }

    /**
     * 点击消息框确定按钮事件
     */
    default void onConfirmCallback() {
    }

    /**
     * 点击消息框取消按钮事件
     */
    default void onCancelCallback() {
    }
}
