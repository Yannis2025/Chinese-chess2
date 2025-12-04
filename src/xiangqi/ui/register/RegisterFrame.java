/*
 * Created by JFormDesigner on Wed Nov 05 09:47:03 CST 2025
 */

package xiangqi.ui.register;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

import xiangqi.ui.Game.StartGameFrame;
import xiangqi.ui.Login.LoginFrame;
/**
 * @author yanni
 */
public class RegisterFrame extends JFrame {
    private StartGameFrame startGameFrame;
    public RegisterFrame(StartGameFrame startGameFrame) {
       this.startGameFrame=startGameFrame;
       initComponents();
       clickCancel();
       clickConfirm();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                startGameFrame.closeLoginFrame();
            }
        });
    }
    //Cancel按钮
    private void clickCancel(){
        CancelButton.addActionListener(e -> {
            this.dispose();//关闭当前界面
            startGameFrame.closeLoginFrame();
        });
    }
    //Confirm按钮
    private void clickConfirm(){
        ConfirmButton.addActionListener(e -> {
            String username=NewUsernameField.getText();
            String password=new String(NewPasswordField.getPassword());
            // 输入验证
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "用户名不能为空！");
                return;
            }

            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "密码不能为空！");
                return;
            }

            if (validator.userExists(username)){
                JOptionPane.showMessageDialog(this,"用户名已存在!");
                return;
            }
            if (save(username,password)){
                JOptionPane.showMessageDialog(this,"注册成功!");
                this.dispose();
                startGameFrame.closeLoginFrame();
            }else {
                JOptionPane.showMessageDialog(this,"注册失败,请重试!");
            }



        });
    }
    private boolean save(String username,String password){
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter("UserInformation",true));
            writer.newLine();
            writer.write(username+","+password);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            System.err.println("保存用户信息失败:"+e.getMessage());
            return false;
        }
    }
    //RegisterFrame组件放置
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - 苏云翼
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        CancelButton = new JButton();
        ConfirmButton = new JButton();
        NewUsernameField = new JTextField();
        NewPasswordField = new JPasswordField();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("Register System");
        label1.setFont(new Font("Segoe Print", Font.PLAIN, 18));
        contentPane.add(label1);
        label1.setBounds(170, 50, 180, label1.getPreferredSize().height);

        //---- label2 ----
        label2.setText("Username:");
        label2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(100, 110), label2.getPreferredSize()));

        //---- label3 ----
        label3.setText("Password:");
        label3.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
        contentPane.add(label3);
        label3.setBounds(new Rectangle(new Point(105, 155), label3.getPreferredSize()));

        //---- CancelButton ----
        CancelButton.setText("Cancel");
        contentPane.add(CancelButton);
        CancelButton.setBounds(new Rectangle(new Point(135, 215), CancelButton.getPreferredSize()));

        //---- ConfirmButton ----
        ConfirmButton.setText("Confirm");
        contentPane.add(ConfirmButton);
        ConfirmButton.setBounds(new Rectangle(new Point(270, 215), ConfirmButton.getPreferredSize()));
        contentPane.add(NewUsernameField);
        NewUsernameField.setBounds(190, 115, 195, NewUsernameField.getPreferredSize().height);
        contentPane.add(NewPasswordField);
        NewPasswordField.setBounds(190, 155, 195, 20);

        contentPane.setPreferredSize(new Dimension(495, 325));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - 苏云翼
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JButton CancelButton;
    private JButton ConfirmButton;
    private JTextField NewUsernameField;
    private JPasswordField NewPasswordField;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
