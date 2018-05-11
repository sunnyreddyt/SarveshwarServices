package com.services.sarveshwarservices;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.services.sarveshwarservices.service.ImageDownloadIntentService;


public class IntentServiceActivity extends Activity implements View.OnClickListener {

    EditText urlText;
    ProgressBar pd;
    ImageView imgView;
    SampleResultReceiver resultReceiever;
    String defaultUrl = "http://developer.android.com/assets/images/dac_logo.png";
   // String defaultUrl =  "https://www.google.co.in/imgres?imgurl=https%3A%2F%2Fimg.tamindir.com%2Fti_e_ul%2Fcanerdil%2Fp%2Fyagmur-damlalari-temasi_3_1024x640.jpg&imgrefurl=https%3A%2F%2Fwww.tamindir.com%2Fwindows%2Fyagmur-damlalari-temasi%2F&docid=9y3SEzsWQqBdiM&tbnid=siEy122ttyJbeM%3A&vet=1&w=1024&h=640&bih=637&biw=1366&ved=2ahUKEwits_fE2_3aAhXHso8KHUy9AvUQxiAoAXoECAEQFQ&iact=c&ictx=1";
    ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        resultReceiever = new SampleResultReceiver(new Handler());
        urlText = (EditText) findViewById(R.id.urlText);
        pd = (ProgressBar) findViewById(R.id.downloadPD);
        imgView = (ImageView) findViewById(R.id.imgView);
        backImageView = (ImageView) findViewById(R.id.backImageView);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private class SampleResultReceiver extends ResultReceiver {

        public SampleResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            switch (resultCode) {
                case ImageDownloadIntentService.DOWNLOAD_ERROR:
                    Toast.makeText(getApplicationContext(), "error in download",
                            Toast.LENGTH_SHORT).show();
                    pd.setVisibility(View.INVISIBLE);
                    break;

                case ImageDownloadIntentService.DOWNLOAD_SUCCESS:
                    String filePath = resultData.getString("filePath");
                    Bitmap bmp = BitmapFactory.decodeFile(filePath);
                    if (imgView != null && bmp != null) {
                        imgView.setImageBitmap(bmp);
                        Toast.makeText(getApplicationContext(),
                                "image download via IntentService is done",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "error in decoding downloaded file",
                                Toast.LENGTH_SHORT).show();
                    }
                    pd.setIndeterminate(false);
                    pd.setVisibility(View.INVISIBLE);

                    break;
            }
            super.onReceiveResult(resultCode, resultData);
        }

    }

    @Override
    public void onClick(View v) {

        Intent startIntent = new Intent(IntentServiceActivity.this,
                ImageDownloadIntentService.class);
        startIntent.putExtra("receiver", resultReceiever);
        startIntent.putExtra("url",
                TextUtils.isEmpty(urlText.getText()) ? defaultUrl : urlText
                        .getText().toString());
        startService(startIntent);
        pd.setVisibility(View.VISIBLE);
        pd.setIndeterminate(true);

    }
}