package com.example.feedbackapp.Common;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

public class ClickSpan extends ClickableSpan
{

	private OnClickListener mListener;
	private HashMap<Integer, Object> tag;

	public ClickSpan(OnClickListener listener, HashMap<Integer, Object> tag) {
		mListener = listener;
		this.tag = tag;
	}

	@Override
	public void onClick(View widget) {
		if (mListener != null) {
			if (tag != null) {
				for (int key : tag.keySet()) {
					widget.setTag(key, tag.get(key));
				}
			}
			mListener.onClick(widget);
		}
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		ds.setUnderlineText(false);
	}

	public interface OnClickListener {
		void onClick(View widget);
	}

	public static void clickify(TextView view, final String clickableText, final ClickSpan.OnClickListener listener, HashMap<Integer, Object> tag) {
		view.setHighlightColor(Color.TRANSPARENT);
		CharSequence text = view.getText();
		String string = text.toString();
		ClickSpan span = new ClickSpan(listener, tag);
		int start = string.indexOf(clickableText);
		int end = start + clickableText.length();
		if (start == -1)
			return;
		if (text instanceof Spannable) {
			((Spannable) text).setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		} else {
			SpannableString s = SpannableString.valueOf(text);
			s.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			view.setText(s);
		}
		MovementMethod m = view.getMovementMethod();
		if ((m == null) || !(m instanceof LinkMovementMethod)) {
			view.setMovementMethod(LinkMovementMethod.getInstance());
		}
	}
}