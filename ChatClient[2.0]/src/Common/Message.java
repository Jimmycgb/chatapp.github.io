package Common;

import java.io.Serializable;

/**
 *  创建消息内容
 */

public class Message implements Serializable {//不要忘记implements Serializable来序列化，否则无法用socket的IO流

    private static final long serialVersionUID = 1L;//一定要记得加这个兼容性！！！！！！！！！
    private String sender;//发送者
    private String getter;//接收者
    private String content;//内容
    private String sendTime;//时间
    private String messageType;//消息类型
    private boolean k;

    public boolean isK() {
        return k;
    }

    public void setK(boolean k) {
        this.k = k;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
