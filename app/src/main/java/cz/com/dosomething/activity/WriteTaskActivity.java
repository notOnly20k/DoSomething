package cz.com.dosomething.activity;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import butterknife.BindView;
import cz.com.dosomething.R;

public class WriteTaskActivity extends BaseActivity implements View.OnLayoutChangeListener {
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
    private boolean setclock = false;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_write_task;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fabClock.setImageResource(R.drawable.login);
        final View v = findViewById(R.id.activity_write_task);
        v.addOnLayoutChangeListener(this);
        vf.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_left_in));
        vf.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_left_out));
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
                            fabClock.setImageResource(R.drawable.login);
                            Log.e(TAG, "onAnimationStart:showNext " );
                            vf.showNext();
                            setclock = false;
                        } else {
                            fabClock.setImageResource(R.drawable.logout);
                            Log.e(TAG, "onAnimationStart:showNext " );
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
            int x=writeLayout.getWidth()/2;
            int y=writeLayout.getHeight()/2;
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


}
