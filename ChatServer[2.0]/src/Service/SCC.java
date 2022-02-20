package Service;

import Common.Message;
import Common.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Server_Connect_Client
 * 将服务器与客户端连接的线程
 * 执行服务端连接客户端的线程的指令
 */

public class SCC extends Thread {
    //创建socket和username对象
    private Socket socket;
    private String username;

    //创建构造器，传入socket和userID对象
    public SCC(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    //创建一个get方法
    public Socket getSocket() {
        return socket;
    }

    /*
    重写run方法
     */
    @Override
    public void run() {
        //写一个死循环，让服务器一直接受Message类的信息
        while (true) {
            try {
                //创建一个输入流，并强转为Message类
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();

                //当收到创建私聊请求时
                if (message.getMessageType().equals(MessageType.CREATE_ONE_TO_ONE)) {
                    //获得接收者的线程
                    SCC scc = MST.getSCC(message.getGetter());
                    //创建一个对应接收者的输出流
                    ObjectOutputStream oos = new ObjectOutputStream(scc.getSocket().getOutputStream());
                    oos.writeObject(message);

                    //当收到群发请求时
                } else if (message.getMessageType().equals(MessageType.ONE_TO_ALL)) {
                    //获得存储在线用户的HashMap
                    HashMap<String, SCC> hm = MST.getHm();
                    //迭代器历遍
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        String onlineUser = iterator.next().toString();
                        if (!onlineUser.equals(message.getSender())) {
                            ObjectOutputStream oos =
                                    new ObjectOutputStream(hm.get(onlineUser).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }

                    //当收到退出请求时
                } else if (message.getMessageType().equals(MessageType.EXIT)) {
                    //获得退出申请者的线程
                    SCC scc = MST.getSCC(message.getSender());
                    //将信息再次转发回去，方便客户端关闭socket
                    ObjectOutputStream oos = new ObjectOutputStream(scc.getSocket().getOutputStream());
                    oos.writeObject(message);
                    //移除用户所对应的线程
                    MST.remove(message.getSender());
                    //刷新服务器在线用户列表和消息框
                    SCR.exit_flash(message);
                    //关闭服务器给其的socket
                    socket.close();
                    break;

                    //当收到刷新用户列表请求时
                } else if (message.getMessageType().equals(MessageType.USER_ONLINE)) {
                    //通过线程管理方法得到在线用户
                    String UserOnline = MST.GetOnlineUsers();
                    //打包好信息并发送给申请者
                    message.setContent(UserOnline);
                    SCC scc = MST.getSCC(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(scc.getSocket().getOutputStream());
                    oos.writeObject(message);

                    //当收到私聊发起者的信息时
                } else if (message.getMessageType().equals(MessageType.ONE_TO_ONE_F)){
                    //将信息转发送给接收者
                    SCC scc = MST.getSCC(message.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(scc.getSocket().getOutputStream());
                    oos.writeObject(message);

                    //当收到私聊接收者的信息时
                } else if (message.getMessageType().equals(MessageType.ONE_TO_ONE_M)){
                    //获得接收者的线程
                    SCC scc = MST.getSCC(message.getGetter());
                    //创建一个对应接收者的输出流
                    ObjectOutputStream oos = new ObjectOutputStream(scc.getSocket().getOutputStream());
                    oos.writeObject(message);

                    //到收到创建聊天室请求时
                } else if (message.getMessageType().equals(MessageType.CREATE_CHAT_ROOM)){
                    //调用创建聊天室的方法，并将一个布尔结果放入信息
                    message.setK
                            (MST.Create_Chat_Room
                                    (message.getSender(), MST.getSCC(message.getSender()), message.getContent()));
                    //将信息传会给申请者
                    SCC scc = MST.getSCC(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(scc.getSocket().getOutputStream());
                    oos.writeObject(message);

                    //当收到进入聊天室请求时
                } else if (message.getMessageType().equals(MessageType.ENTER_CHAT_ROOM)){
                    //调用加入聊天室的方法，并将一个布尔结果放入信息
                    message.setK
                            (MST.Enter_Chat_Room
                                    (message.getSender(), MST.getSCC(message.getSender()), message.getContent()));
                    //将信息传会给申请者
                    SCC scc = MST.getSCC(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(scc.getSocket().getOutputStream());
                    oos.writeObject(message);

                    //当收到刷新聊天室列表请求时
                } else if (message.getMessageType().equals(MessageType.CHAT_ROOM)){
                    //调用获得聊天室列表方法
                    String ChatRoom = MST.GetRoom();
                    //将信息打包发回给申请者
                    message.setContent(ChatRoom);
                    SCC scc = MST.getSCC(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(scc.getSocket().getOutputStream());
                    oos.writeObject(message);

                    //当收到聊天室内发送信息请求时
                } else if (message.getMessageType().equals(MessageType.CHAT_ROOM_MESSAGE)){
                    //创建一个HashMap来接收指定聊天室中的所有用户
                    HashMap<String, SCC> hm = MST.getRoomHm(message.getGetter());
                    //迭代器历遍，key时聊天室的在线用户名
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        String ChatUser = iterator.next().toString();
                        if (!ChatUser.equals(message.getSender())) {
                            ObjectOutputStream oos =
                                    new ObjectOutputStream(hm.get(ChatUser).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }

                    //当收到查看指定聊天室内用户列表时
                } else if (message.getMessageType().equals(MessageType.CHAT_ROOM_USER)){
                    String ChatRoomUser = MST.getRoomUser(MST.getRoomHm(message.getContent()));
                    message.setContent(ChatRoomUser);
                    SCC scc = MST.getSCC(message.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(scc.getSocket().getOutputStream());
                    oos.writeObject(message);

                    //当收到退出聊天室请求时
                }else if (message.getMessageType().equals(MessageType.EXIT_CHAT_ROOM)){
                    MST.getRoomHm(message.getContent()).remove(message.getSender());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
