package View;

import Utils.Check;
import Utils.To_Register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register {

    public Register(){
        // 创建 JFrame 实例
        JFrame frame2 = new JFrame("管理员注册界面");
        //更改左上角图标
        frame2.setIconImage(new ImageIcon("image/chat.jpg").getImage());
        //设置大小
        frame2.setSize(500, 350);
        //叉掉后关闭程序
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //窗口默认在中间弹出
        frame2.setBounds(
                new Rectangle(
                        (int) LoginView.frame.getBounds().getX()+ 50,
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

    private void placeComponents(JPanel panel){
        //构建布局及文字格式——这里不使用
        panel.setLayout(null);
        Font text_label = new Font("宋体", Font.PLAIN, 27);
        Font text_text = new Font("宋体",Font.BOLD,20);
        Font text_btn = new Font("黑体",Font.BOLD,15);

        //创建组件
        //用户名设置
        JLabel userLabel = new JLabel("用 户 名:");
        userLabel.setBounds(65,40,130,50);
        userLabel.setFont(text_label);
        JTextField username = new JTextField(12);
        username.setBounds(198,48,200,35);
        username.setFont(text_text);
        panel.add(userLabel);
        panel.add(username);
        //密码设置
        JLabel pwdLabel = new JLabel("密   码:");
        pwdLabel.setBounds(65,100,130,50);
        pwdLabel.setFont(text_label);
        JPasswordField password = new JPasswordField(12);
        password.setBounds(198,108,200,35);
        password.setFont(text_text);
        panel.add(pwdLabel);
        panel.add(password);
        //确认密码设置
        JLabel R_pwdLabel = new JLabel("确认密码:");
        R_pwdLabel.setBounds(65,160,130,50);
        R_pwdLabel.setFont(text_label);
        JPasswordField R_password = new JPasswordField(12);
        R_password.setBounds(198,168,200,35);
        R_password.setFont(text_text);
        panel.add(R_pwdLabel);
        panel.add(R_password);

        //按钮设置
        JButton LoginBtn = new JButton("确     认     注     册");
        LoginBtn.setBounds(65,220,327,35);
        LoginBtn.setFont(text_btn);
        LoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username_s = username.getText();
                char[] password_ = password.getPassword();
                char[] password_R = password.getPassword();
                String password_s = new String(password_);
                String password_Rs = new String(password_R);
                if(username_s.equals("")){
                    JOptionPane.showMessageDialog(null, "请输入用户名");
                }else if (password_s.equals("")){
                    JOptionPane.showMessageDialog(null, "请输入密码");
                }else {
                    if(password_s.equals(password_Rs)){
                        boolean key = new To_Register().CheckUserName(username_s);
                        if(key){
                            JOptionPane.showMessageDialog(null, "用户已存在");
                        }
                        else {
                            new To_Register().Register(username_s,password_);
                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "密码不一致");
                    }
                }
            }
        });
        panel.add(LoginBtn);
    }
}
