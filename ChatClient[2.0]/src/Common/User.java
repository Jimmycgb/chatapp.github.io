package Common;

import java.io.Serializable;

/**
 *  创建用户身份
 */

public class User implements Serializable {//不要忘记implements Serializable来序列化，否则无法用socket的IO流

    private static final long serialVersionUID = 1L;//一定要记得加这个兼容性！！！！！！！！！
    private String username;//创建一个用户名

    public User(){}

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
