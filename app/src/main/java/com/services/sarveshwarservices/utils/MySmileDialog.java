package com.services.sarveshwarservices.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.services.sarveshwarservices.R;


/**
 * Created by ctel-cpu-36 on 12/22/2016.
 */
public class MySmileDialog extends Dialog {
    LVCircularSmile mLVCircularSmile;

    public MySmileDialog(Context context) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);

        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
        mLVCircularSmile = new LVCircularSmile(context);
        mLVCircularSmile.setLayoutParams(params);

        layout.addView(mLVCircularSmile, params);
        addContentView(layout, params);
    }

    @Override
    public void show() {
        super.show();

        mLVCircularSmile.startAnim();
   }

    @Override
    public void dismiss(){
        super.dismiss();
        mLVCircularSmile.stopAnim();

    }
}