package com.ouc.rpc.framework.util;

import sun.awt.LightweightFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Description: Swing工具类
 * @Author: Mr.Tong
 */
public class SwingUtils {


    public final static ActionListener windowCloserAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JComponent c = (JComponent) e.getSource();
            Window w = (Window) c.getTopLevelAncestor();
            w.dispose();
        }
    };

    /**
     * @Description: 窗口居中
     */
    public static void centerFrame(Window frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension size = frame.getSize();
        screenSize.height = screenSize.height / 2;
        screenSize.width = screenSize.width / 2;
        size.height = size.height / 2;
        size.width = size.width / 2;
        int y = screenSize.height - size.height;
        int x = screenSize.width - size.width;

        frame.setLocation(x, y);
    }


    /**
     * @Description: 展示信息
     */
    public static void showMessage(String title, String str) {
        JFrame info = new JFrame(title);

        JTextArea t = new JTextArea(str, 30, 60);
        t.setEditable(false);
        t.setLineWrap(true);
        t.setWrapStyleWord(true);

        JButton ok = new JButton("Close");
        ok.addActionListener(windowCloserAction);

        info.getContentPane().setLayout(new BoxLayout(info.getContentPane(), BoxLayout.Y_AXIS));
        info.getContentPane().add(new JScrollPane(t));

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));
        jPanel.add(Box.createHorizontalGlue());
        jPanel.add(ok);

        info.getContentPane().add(jPanel);

        ok.setAlignmentX(Component.CENTER_ALIGNMENT);

        info.pack();
        //info.setResizable(false);
        centerFrame(info);
        //info.show();
        info.setVisible(true);
    }


    /**
     * @Description: 创建表格布局
     */
    public static JPanel createTableLayout(JComponent[] labels, JComponent[] textFields) {
        JPanel textControlsPane = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        textControlsPane.setLayout(gridbag);

        c.anchor = GridBagConstraints.WEST;
        int numLabels = labels.length;

        for (int i = 0; i < numLabels; i++) {
            c.gridwidth = GridBagConstraints.RELATIVE; //next-to-last
            c.fill = GridBagConstraints.NONE;      //reset to default
            c.weightx = 0.0;                       //reset to default
            c.insets = new Insets(2, 2, 2, 2);
            gridbag.setConstraints(labels[i], c);
            textControlsPane.add(labels[i]);
            c.gridwidth = GridBagConstraints.REMAINDER;     //end row
            if (i == numLabels - 1) c.gridheight = GridBagConstraints.REMAINDER;     //end row
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1.0;
            c.weighty = 1.0;
            c.insets = new Insets(2, 2, 2, 2);
            gridbag.setConstraints(textFields[i], c);
            textControlsPane.add(textFields[i]);
        }
        return textControlsPane;
    }


    public static void showInfoMessage(String title, String str) {
        // 加载自定义图标
        ImageIcon customIcon = new ImageIcon("/Users/ethan/Desktop/2023/code/semantic/src/main/resources/info.png");

        // 设置自定义图标的大小
        Image image = customIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        customIcon.setImage(scaledImage);

        // 显示带有自定义大小图标的提示框
        JOptionPane.showMessageDialog(null, str, title, JOptionPane.PLAIN_MESSAGE, customIcon);

    }

    public static void showErrorMessage(String title, String str) {
        // 加载自定义图标
        ImageIcon customIcon = new ImageIcon("/Users/ethan/Desktop/2023/code/semantic/src/main/resources/error.png");

        // 设置自定义图标的大小
        Image image = customIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        customIcon.setImage(scaledImage);

        // 显示带有自定义大小图标的提示框
        JOptionPane.showMessageDialog(null, str, title, JOptionPane.PLAIN_MESSAGE, customIcon);
    }

    public static void showWarningMessage(String title, String str) {
        // 加载自定义图标
        ImageIcon customIcon = new ImageIcon("/Users/ethan/Desktop/2023/code/semantic/src/main/resources/warn.png");

        // 设置自定义图标的大小
        Image image = customIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        customIcon.setImage(scaledImage);

        // 显示带有自定义大小图标的提示框
        JOptionPane.showMessageDialog(null, str, title, JOptionPane.PLAIN_MESSAGE, customIcon);
    }

    public static void showQuestionMessage(String title, String str) {
        // 加载自定义图标
        ImageIcon customIcon = new ImageIcon("/Users/ethan/Desktop/2023/code/semantic/src/main/resources/question.png");

        // 设置自定义图标的大小
        Image image = customIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        customIcon.setImage(scaledImage);

        // 显示带有自定义大小图标的提示框
        JOptionPane.showMessageDialog(null, str, title, JOptionPane.PLAIN_MESSAGE, customIcon);

    }


}
