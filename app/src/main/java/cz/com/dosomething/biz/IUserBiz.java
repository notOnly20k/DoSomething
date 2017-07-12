package cz.com.dosomething.biz;

/**
 * Created by cz on 2017/6/17.
 */

public interface IUserBiz {
    public void login(String username, String password, OnLoginListener loginListener);
}
