package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.Message;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

public class MessageVO {

    private int type;

    private String content;

    private String createTime;

    public MessageVO() {
    }

    public MessageVO(@NotNull Message message) {
        this.type=message.getType();
        this.content=message.getContent();
        this.createTime=new Timestamp(message.getCreateTime().getTime()).toString().substring(0,16);
    }

    public MessageVO(int type, String content, String createTime) {
        this.type = type;
        this.content = content;
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
