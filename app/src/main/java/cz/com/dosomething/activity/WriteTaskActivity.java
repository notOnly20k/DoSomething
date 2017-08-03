package cz.com.dosomething.activity;

import android.animation.Animator;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cz.com.dosomething.R;
import cz.com.dosomething.bean.MessageEvent;
import cz.com.dosomething.bean.TaskInfo;
import cz.com.dosomething.db.TaskInfoDao;
import cz.com.dosomething.others.MyApplication;

public class WriteTaskActivity extends BaseActivity implements View.OnLayoutChangeListener, DatePickerDialog.OnDateSetListener
        , TimePickerDialog.OnTimeSetListener {
    private static final String TAG = "WriteTaskActivity";
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.write_layout)
    LinearLayout writeLayout;
    @BindView(R.id.fab_clock)
    FloatingActionButton fabClock;
    @BindView(R.id.activity_write_task)
    LinearLayout activityWriteTask;
    @BindView(R.id.vf)
    ViewFlipper vf;
    @BindView(R.id.tv_clock)
    TextView tvClock;
    @BindView(R.id.tv_c)
    TextView tvC;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.task_title)
    TextInputEditText taskTitle;
    @BindView(R.id.task_content)
    TextInputEditText taskContent;
    private boolean setclock = false;
    private Calendar c;
    private int month;
    private int day;
    private int time;
    private int min;
    private int year;
    private List<TaskInfo> list;
    private long id;
    private String sendtime;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_write_task;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getIntent().getLongExtra("id",0);
        final View v = findViewById(R.id.activity_write_task);
        v.addOnLayoutChangeListener(this);
        initToolbar();
        initView();
        initPicker();
    }



    private void initPicker() {
        tvDate.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvTime.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvC.setText("@");
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        //获取月份，0表示1月份
        month = c.get(Calendar.MONTH);
        //获取当前天数
        day = c.get(Calendar.DAY_OF_MONTH);
        //获取当前小时
        time = c.get(Calendar.HOUR_OF_DAY);
        //获取当前分钟
        min = c.get(Calendar.MINUTE);
        int i = c.get(Calendar.AM_PM);

        String m = null;
        String sx = null;
        if (i == Calendar.AM) {
            sx = "AM";
        } else {

            sx = "AM";
        }
        if (min < 10) {
            m = "0" + min;
        } else {
            m = min + "";
        }
        tvTime.setText(time + ":" + m + sx);
        sendtime =year + "年" + (month + 1) + "月" + day + "日";

    }

    @OnClick(R.id.tv_date)
    public void setDate() {
        DatePickerDialog d = DatePickerDialog.newInstance(this, year, month, day);
        d.setVersion(DatePickerDialog.Version.VERSION_2);
        d.show(getFragmentManager(), "");
    }

    @OnClick(R.id.tv_time)
    public void setTime() {
        TimePickerDialog t = TimePickerDialog.newInstance(this, time, min, false);
        t.setVersion(TimePickerDialog.Version.VERSION_2);
        t.show(getFragmentManager(), "");
    }

    private void initToolbar() {
        title.setText("创建任务");
        toolbar.inflateMenu(R.menu.creattask_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_close:
                        MyApplication.getInstance().finishActivity();
                        break;
                    case R.id.action_save:
                        if (TextUtils.isEmpty(taskTitle.getText())&&TextUtils.isEmpty(taskContent.getText())){
                            MyApplication.getInstance().finishActivity();
                        }else {
                            TaskInfo taskInfo=new TaskInfo();
                            taskInfo.setIschecked(false);
                            taskInfo.setContent(taskContent.getText().toString());
                            taskInfo.setTitle(taskTitle.getText().toString());
                            if (setclock) {
                                taskInfo.setTime(sendtime + tvTime.getText());
                            }
                           taskInfo.setCustomerId(id);
                            TaskInfoDao taskInfoDao= MyApplication.getInstance().getDaoSession().getTaskInfoDao();
                            taskInfoDao.insert(taskInfo);
                            EventBus.getDefault().post(new MessageEvent("refresh",id));
                            MyApplication.getInstance().finishActivity();
                        }
                        break;
                }
                return true;
            }

        });
    }

    private void initView() {
        fabClock.setImageResource(R.mipmap.clock);
        vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left_in));
        vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left_out));
        fabClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = fabClock.getWidth() / 2;
                Animation scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, x, x);
                scaleAnimation.setDuration(500);
                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if (setclock) {
                            fabClock.setImageResource(R.mipmap.clock);
                            vf.showNext();
                            setclock = false;
                        } else {
                            fabClock.setImageResource(R.mipmap.close);
                            vf.showNext();
                            setclock = true;
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                fabClock.startAnimation(scaleAnimation);
            }
        });
    }

    private void setCircularReveal() {
        Animator animator = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int x = writeLayout.getWidth() / 2;
            int y = writeLayout.getHeight() / 2;
            animator = ViewAnimationUtils.createCircularReveal(
                    writeLayout, x, y, 0, (float) Math.hypot(writeLayout.getWidth(), writeLayout.getHeight()));
        }
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(500);
        animator.start();
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        v.removeOnLayoutChangeListener(this);
        setCircularReveal();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        sendtime =year + "年" + (month + 1) + "月" + dayOfMonth + "日";
        if (year == this.year && month == this.month && dayOfMonth == day) {
            tvDate.setText("今天");
        } else {
            tvDate.setText(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
        }
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        //view.getSelectedTime();
        String m = null;
        String sx = null;
        if (hourOfDay > 12) {
            hourOfDay = hourOfDay - 12;
            sx = "PM";
        } else {

            sx = "AM";
        }
        if (minute < 10) {
            m = "0" + minute;
        } else {
            m = minute + "";
        }
        tvTime.setText(hourOfDay + ":" + m + sx);

    }
}
