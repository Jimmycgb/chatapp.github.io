package Service;

import Common.Message;
import Common.MessageType;
import Common.User;
import View.CRView;
import View.IndexView;
import View.PMGView;
import View.PMSView;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Client_Connect_Server
 * 将客户端与服务器连接的线程
 * 执行客户端连接服务端的线程的指令
 */

public class CCS extends Thread {

    //创建socket和username并设置对应构造器，方便从外界传入这两个值
    private Socket socket;
    private User username;

    public CCS(Socket socket, User username) {
        this.socket = socket;
        this.username = username;
    }

    /**
     * 重写线程的run方法
     */
    @Override
    public void run() {
        //当线程启动时，创建聊天主页
        IndexView indexView = new IndexView(username, socket);
        System.out.println(username.getUsername() + "已启动客户端");
        //写一个死循环，保证线程一直在读取数据
        while (true) {
            try {
                //创建输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //读取输入流
                Message message = (Message) ois.readObject();
                /*
                判读输入消息的类型
                 */
                //当收到群聊消息类型时
                if (message.getMessageType().equals(MessageType.ONE_TO_ALL)) {
                    String content = message.getSender() + "对大家说：" + message.getContent() + "\n";
                    //将消息写入聊天文本域，并及时刷新
                    indexView.chatText.append(content);
                    indexView.chatText.paintImmediately(250, 510, 720, 140);
                    //当收到创建私聊申请时
                } else if (message.getMessageType().equals(MessageType.CREATE_ONE_TO_ONE)) {
                    new PMGView(message.getSender(), message.getGetter(), socket);
                } else if (message.getMessageType().equals(MessageType.EXIT)) {
                    socket.close();
                    break;
                    //当收到查看在线用户列表请求时
                } else if (message.getMessageType().equals(MessageType.USER_ONLINE)) {
                    indexView.OnlineUser = message.getContent().split(" ");
                    indexView.list.setListData(indexView.OnlineUser);
                    //当收到私聊发起者的信息--刷新接收者的信息
                } else if (message.getMessageType().equals(MessageType.ONE_TO_ONE_F)) {
                    String content = message.getSender() + "对你说：" + message.getContent() + "\n";
                    PMGView.chatText.append(content);
                    PMGView.chatText.paintImmediately(10, 10, 750, 490);
                    //当收到私聊接收者的信息--刷新发起者的信息
                } else if (message.getMessageType().equals(MessageType.ONE_TO_ONE_M)){
                    String content = message.getSender() + "对你说：" + message.getContent() + "\n";
                    PMSView.chatText.append(content);
                    PMSView.chatText.paintImmediately(10, 10, 750, 490);
                    //当收到创建聊天室的申请
                } else if (message.getMessageType().equals(MessageType.CREATE_CHAT_ROOM)){
                    //如果创建成功
                    if (message.isK()){
                        indexView.jFrame.dispose();
                        new CRView(message.getSender(), message.getContent(), socket);
                    }
                    //当收到加入聊天室的申请
                } else if (message.getMessageType().equals(MessageType.ENTER_CHAT_ROOM)){
                    //如果加入成功
                    if (message.isK()){
                        indexView.jFrame2.dispose();
                        new CRView(message.getSender(), message.getContent(), socket);
                    }
                    //当收到刷新聊天室列表的申请
                } else if (message.getMessageType().equals(MessageType.CHAT_ROOM)){
                    String[] s = message.getContent().split(" ");
                    int length = s.length;
                    //将信息处理，只显示聊天室名称，不显示聊天室密码
                    if ( length > 1 ){
                        for(int i = 0; i<=length; i++){
                            if ( i%2 != 0 ){
                                s[i] = " ";
                            }
                        }
                        indexView.list2.setListData(s);
                    }
                    //当接收到聊天室内的信息
                } else if (message.getMessageType().equals(MessageType.CHAT_ROOM_MESSAGE)){
                    String content = message.getSender() + "说：" + message.getContent() + "\n";
                    CRView.chatText.append(content);
                    CRView.chatText.paintImmediately(10, 10, 750, 490);
                    //当收到刷新聊天室内用户的申请
                } else if (message.getMessageType().equals(MessageType.CHAT_ROOM_USER)){
                    CRView.RoomUser = message.getContent().split(" ");
                    CRView.list.setListData(CRView.RoomUser);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
