package View;

import Service.SCR;
import Utils.Check;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {
    // 创建 JFrame 实例
    public static JFrame frame = new JFrame("管理员登陆界面");
    public LoginView(){
        //更改左上角图标
        frame.setIconImage(new ImageIcon("image/chat.jpg").getImage());
        //设置大小
        frame.setSize(500, 350);
        //叉掉后关闭程序
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
    }

    private static void placeComponents(JPanel panel) {
        //构建布局及文字格式——这里不使用
        panel.setLayout(null);
        Font text_label = new Font("宋体", Font.BOLD, 27);
        Font text_text = new Font("宋体",Font.BOLD,20);
        Font text_btn = new Font("黑体",Font.BOLD,15);

        //创建组件
        //用户名设置
        JLabel userLabel = new JLabel("用户名:");
        userLabel.setBounds(65,40,120,50);
        userLabel.setFont(text_label);
        JTextField username = new JTextField(12);
        username.setBounds(188,48,200,35);
        username.setFont(text_text);
        panel.add(userLabel);
        panel.add(username);
        //密码设置
        JLabel pwdLabel = new JLabel("密  码:");
        pwdLabel.setBounds(65,100,120,50);
        pwdLabel.setFont(text_label);
        JPasswordField password_user = new JPasswordField(12);
        password_user.setBounds(188,108,200,35);
        password_user.setFont(text_text);
        panel.add(pwdLabel);
        panel.add(password_user);

        //按钮设置
        //登录按钮
        JButton LoginBtn = new JButton("登          录");
        LoginBtn.setBounds(65,170,327,35);
        LoginBtn.setFont(text_btn);
        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = username.getText();
                char[] pwd = password_user.getPassword();
                if(user.equals("")){
                    JOptionPane.showMessageDialog(null, "请填写用户名");
                }else if (pwd.length == 0){
                    JOptionPane.showMessageDialog(null, "请输入密码");
                }else {
                    boolean key = new Check().CheckUser(user, pwd);
                    if (key){
                        new SCR().start();
                        frame.dispose();
                    }else {
                        JOptionPane.showMessageDialog(null, "用户名或密码错误");
                    }
                }
            }
        });
        panel.add(LoginBtn);
        //注册按钮
        JButton RegisterBtn = new JButton("注          册");
        RegisterBtn.setBounds(65,215,327,35);
        RegisterBtn.setFont(text_btn);
        RegisterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new View.Register();
            }
        });
        panel.add(RegisterBtn);
    }


    public static void main(String[] args) {
        new LoginView();
    }

}



