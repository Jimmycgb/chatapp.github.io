package View;

import Common.User;
import Service.CCS;
import Service.MCT;
import Utils.Check;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *  登录界面
 */

public class LoginView {
    public static Socket socket;//创建公共socket
    public static User user_sender = new User();//创建公共user_sender，用于传输登录用户的身份
    public static JFrame frame = new JFrame("用户登陆界面");// 创建 JFrame 实例
    public LoginView(){
        //更改左上角图标
        frame.setIconImage(new ImageIcon("image/chat.jpg").getImage());
        //设置大小
        frame.setSize(500, 350);
        //设置为叉掉后退出
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //窗口默认在中间弹出
        frame.setLocationRelativeTo(null);
        //创建并添加面板
        JPanel panel_1 = new JPanel();
        frame.add(panel_1);
        //自定义面板
        placeComponents(panel_1);
        //设置窗口可见
        frame.setVisible(true);
        /*
        设置socket
         */
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"),9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void placeComponents(JPanel panel) {
        //布局格式为空，方便自定义
        panel.setLayout(null);
        //设置标签的文字格式
        Font text_label = new Font("宋体", Font.BOLD, 27);
        //设置输入和显示的文字格式
        Font text_text = new Font("宋体",Font.BOLD,20);
        //设置按钮的文字格式
        Font text_btn = new Font("黑体",Font.BOLD,15);

        /**
        用户名标签及输入框的设置
         */
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(65,40,120,50);
        userLabel.setFont(text_label);
        JTextField username = new JTextField(12);
        username.setBounds(188,48,200,35);
        username.setFont(text_text);
        panel.add(userLabel);
        panel.add(username);

        /**
        密码标签及输入框设置
         */
        JLabel pwdLabel = new JLabel("密  码:");
        pwdLabel.setBounds(65,100,120,50);
        pwdLabel.setFont(text_label);
        JPasswordField password_user = new JPasswordField(12);
        password_user.setBounds(188,108,200,35);
        password_user.setFont(text_text);
        panel.add(pwdLabel);
        panel.add(password_user);

        /**
        登录按钮设置
         */
        JButton LoginBtn = new JButton("登          录");
        LoginBtn.setBounds(65,170,327,35);
        LoginBtn.setFont(text_btn);
        /*
        创建监听器
        设置点击“登录”后的一系列操作
         */
        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取输入的用户名
                String user = username.getText();
                //获取输入的密码
                char[] pwd = password_user.getPassword();
                //当用户名输入为空时
                if(user.equals("")){
                    JOptionPane.showMessageDialog(null, "请填写用户名");
                //当密码输入为空时
                }else if (pwd.length == 0){
                    JOptionPane.showMessageDialog(null, "请输入密码");
                //用户名和密码均不为空时
                }else {
                    //创建一个key，用CheckUser方法判断是否登录成功
                    boolean key = new Check().CheckUser(user, pwd);
                    //登录成功时（key为真）
                    if (key){
                        try {
                            //传入用户名
                            user_sender.setUsername(user);
                            //创建输出流
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            //写入信息
                            oos.writeObject(user_sender);
                            //创建一个对应用户的线程
                            CCS ccs = new CCS(socket,user_sender);
                            //启动线程
                            ccs.start();
                            //将线程放入一个管理方法中，方便以后查询和调用
                            MCT.addClientConnectServerThread(user_sender.getUsername(), ccs);
                            //关闭登录界面
                            frame.dispose();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "用户名或密码错误");
                    }
                }
            }
        });
        //添加进面板
        panel.add(LoginBtn);

        /**
        注册按钮设置
         */
        JButton RegisterBtn = new JButton("注          册");
        RegisterBtn.setBounds(65,215,327,35);
        RegisterBtn.setFont(text_btn);
        /*
        创建监听器
        设置点击“注册”后的操作
         */
        RegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //创建一个新的窗口，用于注册
                new Register();
            }
        });
        //添加进面板
        panel.add(RegisterBtn);

    }

    public static void main(String[] args) {
        new LoginView();
    }

}



