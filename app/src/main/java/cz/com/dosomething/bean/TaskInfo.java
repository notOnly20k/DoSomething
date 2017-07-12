package cz.com.dosomething.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Created by cz on 2017/7/12.
 */
@Entity
public class TaskInfo  {
    private String title;
    private Date time;
    private String content;
    private boolean ischecked;

    @Generated(hash = 822021756)
    public TaskInfo(String title, Date time, String content, boolean ischecked) {
        this.title = title;
        this.time = time;
        this.content = content;
        this.ischecked = ischecked;
    }

    @Generated(hash = 2022720704)
    public TaskInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean ischecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public boolean getIschecked() {
        return this.ischecked;
    }
}
