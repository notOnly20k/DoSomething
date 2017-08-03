package cz.com.dosomething.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cz on 2017/7/12.
 */
@Entity
public class TaskInfo  {
    @Id
    private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    private String title;
    private String time;
    private String content;
    private boolean ischecked;
    private long customerId;
    @Generated(hash = 978293787)
    public TaskInfo(Long id, String title, String time, String content,
            boolean ischecked, long customerId) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
        this.ischecked = ischecked;
        this.customerId = customerId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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
    public long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
