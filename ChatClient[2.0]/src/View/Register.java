package View;

import Utils.To_Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  注册界面
 */

public class Register {

    public Register() {
        // 创建 JFrame 实例
        JFrame frame2 = new JFrame("注册界面");
        //更改左上角图标
        frame2.setIconImage(new ImageIcon("image/chat.jpg").getImage());
        //设置大小
        frame2.setSize(500, 350);
        //叉掉后关闭程序
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //窗口默认在中间弹出
        frame2.setBounds(
                new Rectangle(
                        (int) LoginView.frame.getBounds().getX() + 50,
                        (int) LoginView.frame.getBounds().getY() + 50,
                        (int) LoginView.frame.getBounds().getWidth(),
                        (int) LoginView.frame.getBounds().getHeight()
                ));
        //创建并添加面板
        JPanel panel_2 = new JPanel();
        frame2.add(panel_2);
        //自定义面板
        placeComponents(panel_2);
        //设置窗口可见
        frame2.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        //布局格式为空，方便自定义
        panel.setLayout(null);
        //设置标签的文字格式
        Font text_label = new Font("宋体", Font.BOLD, 27);
        //设置输入和显示的文字格式
        Font text_text = new Font("宋体", Font.BOLD, 20);
        //设置按钮的文字格式
        Font text_btn = new Font("黑体", Font.BOLD, 15);

        /**
         用户名标签及输入框的设置
         */
        JLabel userLabel = new JLabel("用 户 名:");
        userLabel.setBounds(65, 40, 130, 50);
        userLabel.setFont(text_label);
        JTextField username = new JTextField(12);
        username.setBounds(198, 48, 200, 35);
        username.setFont(text_text);
        panel.add(userLabel);
        panel.add(username);

        /**
         密码标签及输入框设置
         */
        JLabel pwdLabel = new JLabel("密   码:");
        pwdLabel.setBounds(65, 100, 130, 50);
        pwdLabel.setFont(text_label);
        JPasswordField password = new JPasswordField(12);
        password.setBounds(198, 108, 200, 35);
        password.setFont(text_text);
        panel.add(pwdLabel);
        panel.add(password);

        /**
         确认密码标签及输入框设置
         */
        JLabel R_pwdLabel = new JLabel("确认密码:");
        R_pwdLabel.setBounds(65, 160, 130, 50);
        R_pwdLabel.setFont(text_label);
        JPasswordField R_password = new JPasswordField(12);
        R_password.setBounds(198, 168, 200, 35);
        R_password.setFont(text_text);
        panel.add(R_pwdLabel);
        panel.add(R_password);

        /**
         确认注册按钮设置
         */
        JButton LoginBtn = new JButton("确     认     注     册");
        LoginBtn.setBounds(65, 220, 327, 35);
        LoginBtn.setFont(text_btn);
        /*
        创建监听器
        设置点击“确认注册”后的一系列操作
         */
        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取输入的用户名
                String username_s = username.getText();
                //获取输入的密码
                char[] password_ = password.getPassword();
                //获取输入的确认密码
                char[] password_R = password.getPassword();
                //将输入的密码转换为String
                String password_s = new String(password_);
                //将输入的确认密码转换为String
                String password_Rs = new String(password_R);
                //当用户名输入为空时
                if (username_s.equals("")) {
                    JOptionPane.showMessageDialog(null, "请输入用户名");
                //当输入的密码为空时
                } else if (password_s.equals("")) {
                    JOptionPane.showMessageDialog(null, "请输入密码");
                } else {
                    if (password_s.equals(password_Rs)) {
                        //创建一个key，用CheckUserName方法判断是否注册成功
                        boolean key = new To_Register().CheckUserName(username_s);
                        //注册不成功时（key为真）
                        if (key) {
                            JOptionPane.showMessageDialog(null, "用户已存在");
                        } else {
                            new To_Register().Register(username_s, password_);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "密码不一致");
                    }
                }
            }
        });
        panel.add(LoginBtn);
    }
}
