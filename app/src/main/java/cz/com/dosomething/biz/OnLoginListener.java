package cz.com.dosomething.biz;

import cz.com.dosomething.bean.User;

/**
 * Created by cz on 2017/6/17.
 */

public interface OnLoginListener {
    void loginSuccess(User user);

    void loginFailed();
}
