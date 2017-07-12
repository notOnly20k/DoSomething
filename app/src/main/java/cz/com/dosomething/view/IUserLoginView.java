package cz.com.dosomething.view;

import cz.com.dosomething.bean.User;

/**
 * Created by cz on 2017/6/17.
 */

public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
