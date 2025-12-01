/*
 * Created by JFormDesigner on Mon Dec 01 14:10:00 CST 2025
 */

package xiangqi.ui.Login;

import xiangqi.ui.Game.GameFrame;

import java.awt.*;
import javax.swing.*;

/**
 * @author yanni
 */
public class UserIDFrame extends JFrame {
    private String redID="";
    private String blackID="";
    private boolean isLoggedIn;
    private String username;


    public UserIDFrame(boolean isLoggedIn, String username) {
        this.isLoggedIn = isLoggedIn;
        this.username = username;
        initComponents();
        setupEventListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - 苏云翼
        label1 = new JLabel();
        label2 = new JLabel();
        textField1 = new JTextField();
        textField2 = new JTextField();
        label3 = new JLabel();
        CancelButton = new JButton();
        ConfirmButton = new JButton();
        DirectStartButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("\u7ea2\u65b9\u6635\u79f0:");
        label1.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
        contentPane.add(label1);
        label1.setBounds(new Rectangle(new Point(55, 50), label1.getPreferredSize()));

        //---- label2 ----
        label2.setText("\u9ed1\u65b9\u6635\u79f0:");
        label2.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
        contentPane.add(label2);
        label2.setBounds(55, 95, 84, 19);
        contentPane.add(textField1);
        textField1.setBounds(155, 50, 205, textField1.getPreferredSize().height);
        contentPane.add(textField2);
        textField2.setBounds(155, 95, 205, 20);

        //---- label3 ----
        label3.setText("(\u4e0d\u8d85\u8fc77\u4e2a\u5b57\u7b26)");
        label3.setFont(new Font("\u6977\u4f53", Font.PLAIN, 18));
        contentPane.add(label3);
        label3.setBounds(145, 140, 155, 19);

        //---- CancelButton ----
        CancelButton.setText("\u53d6\u6d88");
        CancelButton.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        contentPane.add(CancelButton);
        CancelButton.setBounds(new Rectangle(new Point(105, 180), CancelButton.getPreferredSize()));

        //---- ConfirmButton ----
        ConfirmButton.setText("\u786e\u8ba4");
        ConfirmButton.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        contentPane.add(ConfirmButton);
        ConfirmButton.setBounds(240, 180, 78, 30);

        //---- DirectStartButton ----
        DirectStartButton.setText("\u65e0\u9700\u6635\u79f0,\u76f4\u63a5\u5f00\u59cb");
        DirectStartButton.setFont(new Font("\u6977\u4f53", Font.PLAIN, 20));
        contentPane.add(DirectStartButton);
        DirectStartButton.setBounds(110, 230, 205, 30);

        contentPane.setPreferredSize(new Dimension(425, 320));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - 苏云翼
    private JLabel label1;
    private JLabel label2;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel label3;
    private JButton CancelButton;
    private JButton ConfirmButton;
    private JButton DirectStartButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    private void setupListeners(){
        CancelButton.addActionListener(e -> {

        });
    }

    private void setupEventListeners() {
        // 取消按钮,返回登录界面
        CancelButton.addActionListener(e -> {
            this.dispose();
            new LoginFrame().show();
        });

        // 确认按钮,验证并进入游戏
        ConfirmButton.addActionListener(e -> {
            if (validateInput()) {
                enterGame();
            }
        });

        // 直接开始按钮,跳过昵称输入
        DirectStartButton.addActionListener(e -> {
            // 使用默认昵称
            redID = "红方";
            blackID = "黑方";
            enterGame();
        });

        // 回车键也行
        textField1.addActionListener(e -> textField2.requestFocus());
        textField2.addActionListener(e -> {
            if (validateInput()) {
                enterGame();
            }
        });
    }
    // 验证用户输入
    private boolean validateInput() {
        String redNickname = textField1.getText().trim();
        String blackNickname = textField2.getText().trim();

        // 检查是否为空
        if (redNickname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "红方昵称不能为空！", "输入错误", JOptionPane.WARNING_MESSAGE);
            textField1.requestFocus();
            return false;
        }

        if (blackNickname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "黑方昵称不能为空！", "输入错误", JOptionPane.WARNING_MESSAGE);
            textField2.requestFocus();
            return false;
        }

        // 检查是否只包含空格
        if (redNickname.matches("^\\s+$")) {
            JOptionPane.showMessageDialog(this, "红方昵称不能只包含空格！", "输入错误", JOptionPane.WARNING_MESSAGE);
            textField1.requestFocus();
            textField1.selectAll();
            return false;
        }

        if (blackNickname.matches("^\\s+$")) {//^为match方法的开始,\\s代表空白字符,+表示一个或多个,$match方法的结束
            JOptionPane.showMessageDialog(this, "黑方昵称不能只包含空格！", "输入错误", JOptionPane.WARNING_MESSAGE);
            textField2.requestFocus();
            textField2.selectAll();
            return false;
        }

        // 检查长度是否超过7个字符
        if (redNickname.length() > 7) {
            JOptionPane.showMessageDialog(this, "红方昵称不能超过7个字符！", "输入错误", JOptionPane.WARNING_MESSAGE);
            textField1.requestFocus();
            textField1.selectAll();
            return false;
        }

        if (blackNickname.length() > 7) {
            JOptionPane.showMessageDialog(this, "黑方昵称不能超过7个字符！", "输入错误", JOptionPane.WARNING_MESSAGE);
            textField2.requestFocus();
            textField2.selectAll();
            return false;
        }
        // 检查昵称是否相同
        if (redNickname.equals(blackNickname)) {
            int choice = JOptionPane.showConfirmDialog(this, "红方和黑方昵称相同，是否继续？", "昵称相同", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choice != JOptionPane.YES_OPTION) {
                return false;
            }
        }

        // 验证通过，保存昵称
        redID = redNickname;
        blackID = blackNickname;
        return true;
    }
    // 进入游戏界面
    private void enterGame() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame("中国象棋", isLoggedIn, username);

            // 设置昵称（这里只是保存，后续你可以在游戏界面显示）
            gameFrame.setRedNickname(redID);
            gameFrame.setBlackNickname(blackID);

            gameFrame.setVisible(true);
        });
    }
    // 提供给外部调用的显示方法
    public void showFrame() {
        this.setVisible(true);
        this.setLocationRelativeTo(null); // 居中显示
    }

    // Getter方法，方便后续获取昵称
    public String getRedID() {
        return redID;
    }

    public String getBlackID() {
        return blackID;
    }


}

