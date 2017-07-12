package cz.com.dosomething.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cz.com.dosomething.R;
import cz.com.dosomething.bean.User;
import cz.com.dosomething.presenter.UserLoginPresenter;
import cz.com.dosomething.view.IUserLoginView;

/**
 * Created by cz on 2017/6/13.
 */

public class LoginActivity extends BaseActivity implements IUserLoginView {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.pb_login)
    ProgressBar pbLogin;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.img_headpic)
    ImageView imgHeadpic;
    @BindView(R.id.et_loginname)
    TextInputEditText etLoginname;
    @BindView(R.id.et_loginpass)
    TextInputEditText etLoginpass;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    @OnClick({R.id.tv_login, R.id.tv_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                mUserLoginPresenter.login();
                break;
            case R.id.tv_logout:
                mUserLoginPresenter.clear();
                break;

        }
        toolbar.setTitle("do something");
    }



    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public String getUserName() {
        return etLoginname.getText().toString();
    }

    @Override
    public String getPassword() {
        return etLoginpass.getText().toString();
    }

    @Override
    public void clearUserName() {
        etLoginname.setText("");
    }

    @Override
    public void clearPassword() {
        etLoginpass.setText("");
    }

    @Override
    public void showLoading() {
        pbLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLogin.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(User user) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this,
                "login failed", Toast.LENGTH_SHORT).show();
    }
}
