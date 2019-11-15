package com.example.text.helper;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.Builder;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import com.example.text.R;

import androidx.annotation.NonNull;

/**
 * <p>对话框帮助类</p>
 * <p>The dialog helper class.</p>
 *
 * @author jinzhenhua
 * @version 1.0 , create at 2019/02/26 15:08
 */
public class DialogHelper {
	
	private DialogHelper() {
		// Default private constructor.
	}
	
	/**
	 * 创建进度对话框并返回
	 */
	public static Dialog loadingDialog(Context context) {
		Dialog dialog = new Dialog(context, R.style.dialog);
		dialog.setContentView(R.layout.common_layout_dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		
		return dialog;
	}
	
	/**
	 * 创建并返回Material风格的确认对话框
	 */
	public static MaterialDialog confirmDialog(@NonNull Context context, SingleButtonCallback confirm, SingleButtonCallback cancel) {
		MaterialDialog.Builder builder = new Builder(context);
		if (null != confirm) {
			builder.onPositive(confirm);
		}
		if (null != cancel) {
			builder.onNegative(cancel);
		}
		builder.cancelable(false);
		
		return builder.build();
	}
	
	/**
	 * 创建Inquiry对话框并返回
	 */
	public static MaterialDialog inquiryDialog(Context context, OnClickListener clickListener) {
		MaterialDialog.Builder builder = new Builder(context);
		builder.cancelable(false);
		builder.customView(R.layout.dialog_common_inquiry, false);
		
		View view = builder.build().getCustomView();
		if (null != view) {
			view.findViewById(R.id.dialog_common_inquiry_btn_confirm).setOnClickListener(clickListener);
			view.findViewById(R.id.dialog_common_inquiry_btn_cancel).setOnClickListener(clickListener);
		}
		
		return builder.build();
	}
}
