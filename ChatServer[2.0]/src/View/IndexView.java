package View;

import javax.swing.*;
import java.awt.*;

public class IndexView {

    public JList<String> list = new JList<>();
    public String[] OnlineUser = {" "};//创建一个公共的String对象，方便CSS对其修改
    public JTextArea jTextArea02 = new JTextArea();

    public IndexView() {
        JFrame frame03 = new JFrame("管理员窗口");
        //更改左上角图标
        frame03.setIconImage(new ImageIcon("image/chat.jpg").getImage());
        //设置初始窗口大小
        frame03.setSize(1000, 500);
        //按”×“退出
        frame03.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //创建并添加面板
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

        panel.setLayout(null);
        Font font_name = new Font("宋体", Font.BOLD, 20);

        //设置标题
        JTextArea jTextArea01 = new JTextArea();
        jTextArea01.setBounds(10,10,960,20);
        jTextArea01.setFont(new Font("仿宋",Font.BOLD,16));
        jTextArea01.setText("\t\t\t\t  用          户          列          表");
        jTextArea01.setEditable(false);
        panel.add(jTextArea01);

        //设置用户列表
        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFont(font_name);
        list.setListData(OnlineUser);
        JScrollPane scrollPane01 = new JScrollPane(list);
        scrollPane01.setBounds(10,30,960,290);
        panel.add(scrollPane01);

        //设置服务器消息框
        jTextArea02 = new JTextArea();
        jTextArea02.setFont(new Font("仿宋",Font.BOLD,16));
        JScrollPane scrollPane02 = new JScrollPane(jTextArea02);
        scrollPane02.setBounds(10,330,960,100);
        panel.add(scrollPane02);

    }


}
