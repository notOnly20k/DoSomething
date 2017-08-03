package cz.com.dosomething.bean;

/**
 * Created by cz on 2017/8/3.
 */

public class MessageEvent {
    private String s;
    private long id;

    public MessageEvent(String s, long id) {
        this.s = s;
        this.id = id;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
