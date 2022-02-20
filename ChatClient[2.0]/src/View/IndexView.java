package View;

import Common.Message;
import Common.MessageType;
import Common.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 聊天主界面
 */

public class IndexView {

    public Socket socket;//创建公共socket
    public User user;//创建公共user
    public String send_choose;//表示用户选择的聊天对象
    public String send_choose_chatRoom;//表示用户选择的聊天室
    public JTextArea chatText;//创建公共的显示信息的文本域，方便在CCS中使用，做到及时刷新的效果
    public JList<String> list;//创建公共的显示用户的列表，方便在需要时刷新
    public JList<String> list2;//创建公共的显示聊天室的列表，方便在需要时刷新
    public String[] OnlineUser = {" "};//创建一个公共的String对象，方便CSS对其修改
    public String[] ChatRoom = {" "};//创建一个公共的String对象，方便CSS对其修改
    public JFrame jFrame = new JFrame();//创建一个公共的窗口，用于在用户创建聊天室时弹出
    public JFrame jFrame2 = new JFrame();//创建一个公共的窗口，用于在用户申请加入聊天室时弹出
    public static JFrame frame03 = new JFrame();//创建一个公共静态的窗口用于显示主界面

    public IndexView(User user, Socket socket) {//传入登入用户及其对应socket
        //传入登入用户及其对应socket
        this.user = user;
        this.socket = socket;
        frame03.setTitle(user.getUsername() + "的聊天窗口");
        //更改左上角图标
        frame03.setIconImage(new ImageIcon("image/chat.jpg").getImage());
        //设置初始窗口大小
        frame03.setSize(1000, 750);
        //按”×“不能直接结束程序
        frame03.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //重写“×”的逻辑，在关闭时要先传一个退出请求给服务器，让服务器和客户端可以关闭用户对应线程及socket
        frame03.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    //创建一个message来存储信息
                    Message message = new Message();
                    //设置信息形式
                    message.setMessageType(MessageType.EXIT);
                    //设置发送者
                    message.setSender(user.getUsername());
                    //创建一个输出流
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    //写入消息
                    oos.writeObject(message);
                    //退出程序
                    System.exit(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //创建并添加面板
        //JScrollPane scrollPane = new JScrollPane();
        JPanel panel = new JPanel();
        frame03.add(panel);
        //调用初始窗口的函数
        placeComponents_INDEX(panel);
        //窗口居中
        frame03.setLocationRelativeTo(null);
        //让窗口在启动程序时就出现
        frame03.setVisible(true);

    }

    public void placeComponents_INDEX(JPanel panel) {
        //布局格式为空，方便自定义
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
        scrollPane01.setBounds(250, 10, 720, 490);
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
        scrollPane02.setBounds(250, 510, 720, 140);
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
        list.setListData(OnlineUser);
        /*
        创建监听器
        当元素被选中时，获取文本，设置为send_choose，方便后面发送消息时获取接收者
         */
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                send_choose = list.getSelectedValue();
            }
        });
        //添加进面板
        JScrollPane scrollPane03 = new JScrollPane(list);
        //设置位置及大小
        scrollPane03.setBounds(10, 30, 230, 260);
        panel.add(scrollPane03);
        //创建好用户列表后先刷新一次
        try {
            //创建一个message来存储信息
            Message message = new Message();
            //设置信息形式
            message.setMessageType(MessageType.USER_ONLINE);
            //设置发送者
            message.setSender(user.getUsername());
            //创建一个输出流
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //写入消息
            oos.writeObject(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //设置标签
        JLabel jLabel = new JLabel("在线用户列表");
        jLabel.setFont(new Font("仿宋", Font.BOLD, 16));
        jLabel.setBounds(10, 10, 229, 20);
        jLabel.setOpaque(true);
        jLabel.setBackground(Color.lightGray);
        panel.add(jLabel);

        /**
         *  设置聊天室列表
         */
        list2 = new JList<>();
        //只允许单选
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //文字格式
        list2.setFont(font_name);
        //设置列表中的文本，初始为空
        list2.setListData(ChatRoom);
        /*
        创建监听器
        当元素被选中时，获取文本，设置为send_choose，方便后面发送消息时获取接收者
         */
        list2.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                send_choose_chatRoom = list2.getSelectedValue();
            }
        });
        //添加进面板
        JScrollPane scrollPane04 = new JScrollPane(list2);
        //设置位置及大小
        scrollPane04.setBounds(10, 355, 230, 225);
        panel.add(scrollPane04);

        //创建好聊天列表后先刷新一边
        Message message = new Message();
        message.setMessageType(MessageType.CHAT_ROOM);
        message.setSender(user.getUsername());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //设置标签
        JLabel jLabel2 = new JLabel("聊天室列表");
        jLabel2.setFont(new Font("仿宋", Font.BOLD, 16));
        jLabel2.setBounds(10, 335, 229, 20);
        jLabel2.setOpaque(true);
        jLabel2.setBackground(Color.lightGray);
        panel.add(jLabel2);


        /**
         * 设置发送信息按钮
         */
        JButton send = new JButton();
        //设置位置及大小
        send.setBounds(900, 660, 70, 30);
        //文字格式
        send.setFont(font_btn);
        //设置文本
        send.setText("发送");
        /*
        创建监听器
        设置点击“发送”后的一系列操作
         */
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //创建一个message来存储信息
                Message message = new Message();
                //设置发送者
                message.setSender(LoginView.user_sender.getUsername());
                //设置要发送的文本
                message.setContent(sendText.getText());
                //设置信息形式
                message.setMessageType(MessageType.ONE_TO_ALL);
                try {
                    //创建输出流
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    //写入信息
                    oos.writeObject(message);
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
                //将发送的话语显示到聊天区域
                chatText.append("你对大家说：" + sendText.getText() + "\n");
                chatText.paintImmediately(250, 510, 720, 140);
                //将输出框清空
                sendText.setText("");
            }
        });
        //添加进面板
        panel.add(send);

        /**
         * 设置”创建私聊窗口“按钮
         */
        JButton PM = new JButton();
        PM.setBounds(10,300,110,25);
        PM.setFont(font_btn);
        PM.setText("私聊");
        PM.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e){
                    //将创建请求打包到message，Sender为创建者，Getter为接收者
                    Message message = new Message();
                    message.setSender(LoginView.user_sender.getUsername());
                    message.setGetter(send_choose);
                    message.setMessageType(MessageType.CREATE_ONE_TO_ONE);
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(message);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    new PMSView(message, socket);
                }
            });
        panel.add(PM);

        /**
         * 设置“刷新在线用户”按钮
         */
        JButton flashBtn = new JButton();
        //设置位置及大小
        flashBtn.setBounds(130,300,110,25);
        //设置文字格式
        flashBtn.setFont(font_btn);
        //设置文本
        flashBtn.setText("刷新");
        /*
        创建监听器
        设置点击"刷新在线用户"后的一系列操作
        */
        flashBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                try {
                    //创建一个message来存储信息
                    Message message = new Message();
                    //设置信息形式
                    message.setMessageType(MessageType.USER_ONLINE);
                    //设置发送者
                    message.setSender(user.getUsername());
                    //创建一个输出流
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    //写入消息
                    oos.writeObject(message);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        //添加进面板
        panel.add(flashBtn);

        /**
         *  设置进入聊天室按钮
         */
        JButton EnterBtn = new JButton();
        EnterBtn.setBounds(10,625,110,25);
        EnterBtn.setFont(font_btn);
        EnterBtn.setText("进入");
        EnterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //先创建一个窗口，要求用户输入密码
                jFrame2 = new JFrame();
                jFrame2.setTitle("加入聊天室: " + send_choose_chatRoom);
                jFrame2.setIconImage(new ImageIcon("image/chat.jpg").getImage());
                jFrame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrame2.setSize(500,350);
                jFrame2.setLocationRelativeTo(null);
                jFrame2.setVisible(true);
                //先设置一个中间面板
                JPanel panel1 = new JPanel();
                panel1.setLayout(null);
                //密码标签及密码框
                JLabel jLabel = new JLabel("密码:");
                jLabel.setBounds(65,70,120,50);
                jLabel.setFont(new Font("宋体", Font.BOLD, 27));
                JTextField jTextField = new JTextField(12);
                jTextField.setBounds(188,78,200,35);
                jTextField.setFont(font_text);
                panel1.add(jLabel);
                panel1.add(jTextField);
                //“加入按钮”
                JButton EnterBtn = new JButton("加          入");
                EnterBtn.setBounds(65,140,327,35);
                EnterBtn.setFont(font_text);
                EnterBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //返回值与存储聊天室的HashMap的key对应---聊天室名称 + “ ” + 密码
                        String roomName_pwd = send_choose_chatRoom + " " + jTextField.getText();
                        Message message = new Message();
                        message.setMessageType(MessageType.ENTER_CHAT_ROOM);
                        message.setSender(user.getUsername());
                        message.setContent(roomName_pwd);
                        try {
                            //创建一个输出流
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            //写入消息
                            oos.writeObject(message);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                panel1.add(EnterBtn);
                jFrame2.add(panel1);

            }
        });
        panel.add(EnterBtn);

        /**
         *  设置创建聊天室按钮
         */
        JButton CreateBtn = new JButton();
        CreateBtn.setBounds(130,625,110,25);
        CreateBtn.setFont(font_btn);
        CreateBtn.setText("创建");
        CreateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //创建一个窗口，要求用户设置所创建聊天室的名称和密码
                jFrame = new JFrame();
                jFrame.setTitle("创建聊天室");
                jFrame.setIconImage(new ImageIcon("image/chat.jpg").getImage());
                jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrame.setSize(500,350);
                jFrame.setLocationRelativeTo(null);
                jFrame.setVisible(true);
                //创建一个中间面板
                JPanel panel1 = new JPanel();
                panel1.setLayout(null);
                //“聊天室”标签及输入框
                JLabel jLabel1 = new JLabel("聊天室:");
                jLabel1.setBounds(65,40,120,50);
                jLabel1.setFont(new Font("宋体", Font.BOLD, 27));
                JTextField jTextField1 = new JTextField(12);
                jTextField1.setBounds(188,48,200,35);
                jTextField1.setFont(font_text);
                panel1.add(jLabel1);
                panel1.add(jTextField1);
                //密码标签及输入框
                JLabel jLabel2 = new JLabel("密码:");
                jLabel2.setBounds(65,100,120,50);
                jLabel2.setFont(new Font("宋体", Font.BOLD, 27));
                JTextField jTextField2 = new JTextField(12);
                jTextField2.setBounds(188,108,200,35);
                jTextField2.setFont(font_text);
                panel1.add(jLabel2);
                panel1.add(jTextField2);
                //创建按钮
                JButton CreateBtn = new JButton("创          建");
                CreateBtn.setBounds(65,170,327,35);
                CreateBtn.setFont(font_text);
                CreateBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //一个字符串传给服务器，让服务器将其存储于聊天室的一级HashMap，字符串格式为 聊天室名称 + “ ” + 密码
                        String roomName_pwd = jTextField1.getText() + " " + jTextField2.getText();
                        Message message = new Message();
                        message.setMessageType(MessageType.CREATE_CHAT_ROOM);
                        message.setSender(user.getUsername());
                        message.setContent(roomName_pwd);
                        try {
                            //创建一个输出流
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            //写入消息
                            oos.writeObject(message);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                panel1.add(CreateBtn);
                jFrame.add(panel1);

            }
        });
        panel.add(CreateBtn);

        /**
         *  设置刷新聊天室按钮
         */
        JButton flashBtn2 = new JButton();
        flashBtn2.setBounds(10,590,230,25);
        flashBtn2.setFont(font_btn);
        flashBtn2.setText("刷新聊天室列表");
        flashBtn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //向服务器发送刷新聊天室列表请求
                Message message = new Message();
                message.setMessageType(MessageType.CHAT_ROOM);
                message.setSender(user.getUsername());
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(flashBtn2);

    }
}