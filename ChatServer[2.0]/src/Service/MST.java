package Service;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Management_Server_Thread
 * 管理服务端连接客户端的线程
 */

public class MST {

    //hm-创建一个HashMap用于存放全部线程
    private static HashMap<String,SCC> hm = new HashMap<>();
    //hm-创建HashMap的get方法
    public static HashMap<String, SCC> getHm() {
        return hm;
    }
    //hm-创建添加线程对象到hm集合的方法
    public static void addClientThread(String userID, SCC scc) {
        hm.put(userID, scc);
    }
    //hm-创建通过用户名找到用户所对应开的线程的方法
    public static SCC getSCC(String userID){
        return hm.get(userID);
    }
    //hm-创建返回在线用户列表的方法
    public static String GetOnlineUsers() {
        //遍历HasMap的 key ——> 也就是用户名
        Iterator<String> iterator = hm.keySet().iterator();
        String OnlineUserList = "";
        while (iterator.hasNext()) {
            OnlineUserList += iterator.next().toString() + " ";
        }
        return OnlineUserList;
    }

    //hm-移除线程方法
    public static void remove(String userID) {
        hm.remove(userID);
    }

    //创建一个嵌套HashMap--CCrm用于存放指定线程，实现建立群聊功能
    private static HashMap<String, HashMap<String,SCC>> CCRhm = new HashMap<>();

    //创建聊天室方法
    public static boolean Create_Chat_Room(String userID, SCC scc, String roomName_pwd){
        //先查看是否存在同名同密码的聊天室
        Iterator<String> iterator = CCRhm.keySet().iterator();
        while(iterator.hasNext()){
            if (iterator.next().equals(roomName_pwd)){
                return false;
            }
        }
        //如果不存在，就创建一个HashMap--Room，里面存储用户名和对应线程
        HashMap<String, SCC> Room = new HashMap<>();
        Room.put(userID, scc);
        //将Room放入CCRhm
        CCRhm.put(roomName_pwd, Room);
        return true;
    }

    //加入聊天室方法
    public static boolean Enter_Chat_Room(String userID, SCC scc, String roomName_pwd){
        //遍历HasMap的 key ——> 也就是聊天室名称+密码
        Iterator<String> iterator = CCRhm.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            //当找到对应聊天室时
            if(key.equals(roomName_pwd)){
                //将申请者的用户名和对应线程放入Room中
                CCRhm.get(key).put(userID,scc);
                return true;
            }
        }
        return false;
    }

    //获得聊天室列表的方法
    public static String GetRoom(){
        String GetRoomUser = "";
        //遍历HasMap的 key ——> 也就是聊天室名称+密码
        Iterator<String> iterator = CCRhm.keySet().iterator();
        while (iterator.hasNext()){
            GetRoomUser += iterator.next().toString() + " ";
            }
        return GetRoomUser;
    }

    //获得指定聊天室的HashMap--Room的方法
    public static HashMap<String, SCC> getRoomHm(String roomName_pwd){
        Iterator<String> iterator = CCRhm.keySet().iterator();
        while(iterator.hasNext()){
            //这里用key来存储聊天室名称加秘密，因为一个循环中iterator.next()只能用一次，不然会一次循环移动Room两次
            String key = iterator.next();
            if(key.equals(roomName_pwd)){
                return CCRhm.get(key);
            }
        }
        return null;
    }

    //获得指定聊天室内用户列表的方法
    public static String getRoomUser(HashMap<String, SCC> hm){
        String ChatRoomUser = "";
        Iterator<String> iterator = hm.keySet().iterator();
        while (iterator.hasNext()) {
            ChatRoomUser += iterator.next().toString() + " ";
        }
        return ChatRoomUser;
    }

}
