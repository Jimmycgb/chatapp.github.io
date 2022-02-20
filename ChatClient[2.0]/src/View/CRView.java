package View;

import Common.Message;
import Common.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Chat_Room_View
 * 群聊（聊天室）界面
 */

public class CRView {

    private Socket socket;
    public String username;
    public String roomName_pwd;
    public static JTextArea chatText;//创建公共的显示信息的文本域，方便在CCS中使用，做到及时刷新的效果
    public static JList<String> list;//创建公共的显示用户的列表，方便在需要时刷新
    public static String[] RoomUser = {" "};//创建一个公共的String对象，方便CSS对其修改
    public JFrame frame05 = new JFrame();

    public CRView(String username, String roomName_pwd, Socket socket){

        this.username = username;
        this.roomName_pwd = roomName_pwd;
        this.socket = socket;

        frame05.setTitle(roomName_pwd + "（群聊）");
        frame05.setIconImage(new ImageIcon("image/chat.jpg").getImage());
        frame05.setSize(1000, 750);
        frame05.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //修改关闭的执行程序，在点击关闭后，应先让服务器删除所在聊天室内自己的用户名和线程，再销毁窗口
        frame05.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    //创建一个message来存储信息
                    Message message = new Message();
                    //设置信息形式
                    message.setMessageType(MessageType.EXIT_CHAT_ROOM);
                    message.setSender(username);
                    message.setContent(roomName_pwd);
                    //创建一个输出流
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                    //销毁窗口
                    frame05.dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        JPanel panel = new JPanel();
        frame05.add(panel);
        placeComponents_CR(panel);
        frame05.setLocationRelativeTo(null);
        frame05.setVisible(true);
    }

    public void placeComponents_CR(JPanel panel){

        panel.setLayout(null);
        //设置输入和显示的文字格式
        Font font_text = new Font("仿宋", Font.PLAIN, 20);
        //设置用户列表的文字格式
        Font font_name = new Font("宋体", Font.BOLD, 20);
        //设置按钮的文字格式
        Font font_btn = new Font("宋体", Font.PLAIN, 15);

        /**
         设置显示聊天消息的文本域
         */
        chatText = new JTextArea();
        //文字格式
        chatText.setFont(font_text);
        //不能编辑
        chatText.setEditable(false);
        //添加进面板
        JScrollPane scrollPane01 = new JScrollPane(chatText);
        //设置位置及大小
        scrollPane01.setBounds(10, 10, 750, 490);
        panel.add(scrollPane01);

        /**
         创建发送消息的文本域
         */
        JTextArea sendText = new JTextArea();
        //文字格式
        sendText.setFont(font_text);
        //添加进面板
        JScrollPane scrollPane02 = new JScrollPane(sendText);
        //设置位置及大小
        scrollPane02.setBounds(10, 510, 750, 130);
        panel.add(scrollPane02);

        /**
         设置用户列表
         */
        list = new JList<>();
        //只允许单选
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //文字格式
        list.setFont(font_name);
        //设置列表中的文本，初始为空
        list.setListData(RoomUser);
        //添加进面板
        JScrollPane scrollPane03 = new JScrollPane(list);
        //设置位置及大小
        scrollPane03.setBounds(770, 10, 190, 630);
        panel.add(scrollPane03);

        //列表创建好之后先刷新一次
        Message message = new Message();
        message.setSender(username);
        message.setContent(roomName_pwd);
        message.setMessageType(MessageType.CHAT_ROOM_USER);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 设置发送信息按钮
         */
        JButton send = new JButton();
        //设置位置及大小
        send.setBounds(690, 650, 70, 30);
        //文字格式
        send.setFont(font_btn);
        //设置文本
        send.setText("发送");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.setSender(username);
                message.setGetter(roomName_pwd);
                message.setContent(sendText.getText());
                message.setMessageType(MessageType.CHAT_ROOM_MESSAGE);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                chatText.append("你说: " + sendText.getText() + "\n");
                chatText.paintImmediately(10, 10, 750, 490);
                sendText.setText("");
            }
        });
        //添加进面板
        panel.add(send);

        /**
         * 设置“刷新在线用户”按钮
         */
        JButton flashBtn = new JButton();
        //设置位置及大小
        flashBtn.setBounds(890,650,70,30);
        //设置文字格式
        flashBtn.setFont(font_btn);
        //设置文本
        flashBtn.setText("刷新");
        flashBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message = new Message();
                message.setSender(username);
                message.setContent(roomName_pwd);
                message.setMessageType(MessageType.CHAT_ROOM_USER);
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                } catch (IOException x) {
                    x.printStackTrace();
                }
            }
        });
        //添加进面板
        panel.add(flashBtn);

    }

}
