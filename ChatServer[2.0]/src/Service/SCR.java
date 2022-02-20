package Service;

import Common.Message;
import Common.User;
import View.IndexView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server_Connect_Running
 * 启动服务器
 * 让服务器一直接受客户端的登录数据
 */

public class SCR extends Thread{
    //创建一个服务器Socket
    public ServerSocket ss = null;
    //创建服务器主页
    public static IndexView indexView = new IndexView();
    //创建一个无参构造器
    public SCR(){}
    //创建一个方法，当用户退出时调用它来刷新在线用户列表
    public static void exit_flash(Message message){
        //获得在线用户对象
        String s = MST.GetOnlineUsers();
        //将其中的“群发”元素删除
        String s_ = s.replace("群发"," ");
        //以空格为分割放入字符串数组
        String[] OnlineUser = s_.split(" ");
        //刷新在线用户列表
        indexView.list.setListData(OnlineUser);
        //刷新服务器消息框
        indexView.jTextArea02.append(message.getSender() + "下线了" + "\n");
        indexView.jTextArea02.paintImmediately(10,330,960,100);
    }

    /*
        重写run方法
         */
    @Override
    public void run() {
        try {
            //设置监听窗口
            ss = new ServerSocket(9999);
            //写个死循环让服务器一直接受User类的信息
            while (true){
                Socket socket = ss.accept();
                //创建输入流，并将其类型强转为User类
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User u = (User) ois.readObject();
                //创建一个线程（与用户名一一对应），开启线程
                SCC scc = new SCC(socket, u.getUsername());
                scc.start();
                //加入线程管理系统
                MST.addClientThread(u.getUsername(),scc);
                //获得在线用户对象
                String s = MST.GetOnlineUsers();
                //将其中的“群发”元素删除
                String s_ = s.replace("群发"," ");
                //以空格为分割放入字符串数组
                String[] OnlineUser = s_.split(" ");
                //刷新在线用户列表
                indexView.list.setListData(OnlineUser);
                //刷新服务器消息框
                indexView.jTextArea02.append(u.getUsername() + "上线了" + "\n");
                indexView.jTextArea02.paintImmediately(10,330,960,100);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
