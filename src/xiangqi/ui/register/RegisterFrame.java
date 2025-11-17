/*
 * Created by JFormDesigner on Wed Nov 05 09:47:03 CST 2025
 */

package xiangqi.ui.register;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import xiangqi.ui.Login.LoginFrame;
/**
 * @author yanni
 */
public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        initComponents();
       clickCancel();
       clickConfirm();
    }
    //Cancel按钮
    private void clickCancel(){
        CancelButton.addActionListener(e -> {
            this.dispose();//关闭当前界面
            LoginFrame loginFrame=new LoginFrame();//返回登录界面
            loginFrame.show();
        });
    }
    //Confirm按钮
    private void clickConfirm(){
        ConfirmButton.addActionListener(e -> {
            String username=NewUsernameField.getText();
            String password=new String(NewPasswordField.getPassword());
            save(username,password);

            this.dispose();
            LoginFrame loginFrame=new LoginFrame();
            loginFrame.show();
        });
    }
    private void save(String username,String password){
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter("UserInformation",true));
            writer.newLine();
            writer.write(username+","+password);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
