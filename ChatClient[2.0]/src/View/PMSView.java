package View;

import Common.Message;
import Common.MessageType;
import javafx.scene.SnapshotParametersBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Private_Chat_Sender_View
 * 私聊界面(发起者)
 */

public class PMSView {
    private Socket socket;
    public String sender;
    public String getter;
    public static JTextArea chatText;//创建公共的显示信息的文本域，方便在CCS中使用，做到及时刷新的效果
    public JFrame frame04 = new JFrame();

    public PMSView(Message message, Socket socket) {
        this.sender = message.getSender();
        this.getter = message.getGetter();
        this.socket = socket;
        frame04.setTitle(getter);
        frame04.setIconImage(new ImageIcon("image/chat.jpg").getImage());
        frame04.setSize(800, 750);
        frame04.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        frame04.add(panel);
        placeComponents_PM(panel);
        frame04.setLocationRelativeTo(null);
        frame04.setVisible(true);
    }

    public void placeComponents_PM(JPanel panel) {
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
         * 设置发送信息按钮
         */
        JButton send = new JButton();
        //设置位置及大小
        send.setBounds(690, 660, 70, 30);
        //文字格式
        send.setFont(font_btn);
        //设置文本
        send.setText("发送");
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //创建一个message来存储信息
                    Message message = new Message();
                    //设置发送者
                    message.setSender(sender);
                    //设置接收者
                    message.setGetter(getter);
                    //设置要发送的文本
                    message.setContent(sendText.getText());
                    //设置信息形式
                    message.setMessageType(MessageType.ONE_TO_ONE_F);
                    //创建输出流
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    //写入信息
                    oos.writeObject(message);
                    //将发送的话语显示到聊天区域
                    chatText.append("你对" + message.getGetter() + "说：" + sendText.getText() + "\n");
                    chatText.paintImmediately(250, 510, 720, 140);
                    //将输出框清空
                    sendText.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //添加进面板
        panel.add(send);
    }
}
