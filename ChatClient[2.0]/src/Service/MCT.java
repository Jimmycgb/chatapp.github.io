package Service;

import java.util.HashMap;

/**
 * Management_Client_Thread
 * 管理客户端连接服务端的线程
 */

public class MCT {
    //我们把多个线程放入到一个HashMap集合，key就是用户ID，value就是线程
    private static HashMap<String, CCS> hm = new HashMap<>();
    //将某个线程加入到集合中
    public static void addClientConnectServerThread
    (String userID, CCS ccs){
        hm.put(userID,ccs);
    }
    //通过userID可以得到对应线程
    public static CCS getCCS(String userID){
        return hm.get(userID);
    }

}
