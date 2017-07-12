package cz.com.dosomething.bean;

import java.util.List;

/**
 * Created by cz on 2017/7/12.
 */


public class Task {
    private String type;
    private int num;
    private List<TaskInfo>list;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<TaskInfo> getList() {
        return list;
    }

    public void setList(List<TaskInfo> list) {
        this.list = list;
    }
}
