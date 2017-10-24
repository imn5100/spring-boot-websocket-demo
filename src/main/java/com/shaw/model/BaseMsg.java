package com.shaw.model;

import com.shaw.constants.BaseConstants;

/**
 *
 * @author shaw
 * @date 2017/2/22 0022
 */
public class BaseMsg {
    private String type;
    private String contents;
    private Integer count;

    //默认消息类型 普通为发送消息
    public BaseMsg() {
        this.setType(BaseConstants.SEND_MSG_TYPE);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
