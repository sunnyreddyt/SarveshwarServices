package com.services.sarveshwarservices.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.services.sarveshwarservices.R;
import com.services.sarveshwarservices.utils.ABUtil;

public class MVPActivity extends AppCompatActivity implements ILoginView {

    ABUtil abUtil;
    LoginPresenter loginPresenter;
    EditText emailEditext, passwordEditext;
    TextView submitTextView;
    ImageView backImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);

        abUtil = ABUtil.getInstance(MVPActivity.this);
        loginPresenter = new LoginPresenter();
        emailEditext = (EditText) findViewById(R.id.emailEditext);
        passwordEditext = (EditText) findViewById(R.id.passwordEditext);
        submitTextView = (TextView) findViewById(R.id.submitTextView);
        backImageView = (ImageView) findViewById(R.id.backImageView);

        submitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginPresenter.checkLogin(emailEditext.getText().toString(), passwordEditext.getText().toString());
            }
        });

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getPresenter().onViewAttached(this); //resuming - attach view to presenter

    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onViewDettached();//pausing, detach view from presenter
    }

    public LoginPresenter getPresenter() {
        return loginPresenter;
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(MVPActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmailError(String error) {

        Toast.makeText(MVPActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordError(String error) {

        Toast.makeText(MVPActivity.this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGeneralError(String error) {

        Toast.makeText(MVPActivity.this, error, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showProgress(boolean show) {
        abUtil.showSmileProgressDialog(MVPActivity.this);

    }

    @Override
    public void dismissProgress(boolean show) {
        abUtil.dismissSmileProgressDialog();
    }
}
