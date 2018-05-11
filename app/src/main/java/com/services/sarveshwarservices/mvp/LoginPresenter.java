package com.services.sarveshwarservices.mvp;

import com.services.sarveshwarservices.model.LoginModel;

public class LoginPresenter {

    ILoginView iLoginView;

    public void checkLogin(String email, String password) {

        if (email.length() > 0 && password.length() > 0) {
            if (email.equalsIgnoreCase("sunny")) {
                if (password.equalsIgnoreCase("password")) {
                    iLoginView.onLoginSuccess();
                } else {
                    iLoginView.onEmailError("wrong password provided");
                }
            } else {
                iLoginView.onEmailError("wrong email provided");
            }
        } else {
            iLoginView.onGeneralError("Please enter email and password");
        }
    }

    public void onViewAttached(ILoginView view) {
        iLoginView = view;
    }

    public void onViewDettached() {
        iLoginView = null;
    }

}
