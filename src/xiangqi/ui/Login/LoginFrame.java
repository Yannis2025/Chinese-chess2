/*
 * Created by JFormDesigner on Tue Nov 04 19:45:01 CST 2025
 */

package xiangqi.ui.Login;
import java.awt.*;
import javax.swing.*;

import xiangqi.ui.Game.GameFrame;
import xiangqi.ui.register.validator;
import xiangqi.ui.register.RegisterFrame;

/**
 * @author yanni
 */
public class LoginFrame  {
    //主方法,放置组件+按钮响应
    public LoginFrame() {
        initComponents();
        clickButton();
        //testClickButton();
    }
    //显示可见+exitOnClose
    public void show(){
        loginFrame.setVisible(true);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    //打开象棋界面
    private void openChessBoard(boolean isLoggedIn, String username){
        loginFrame.dispose();//关闭当前登录界面
        //先打开UserIDFrame
        SwingUtilities.invokeLater(() -> {
            UserIDFrame userIDFrame = new UserIDFrame(isLoggedIn, username);
            userIDFrame.showFrame();
        });

    }
    //打开注册界面
    private void openRegisterFrame(){
        loginFrame.dispose();
        SwingUtilities.invokeLater(()->{
            RegisterFrame registerFrame=new RegisterFrame();
            registerFrame.setVisible(true);
            registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
    //注册与登录两个按钮功能
    private void clickButton(){
        LoginButton.addActionListener(e -> {
            String username=UsernameField.getText();
            String password=new String(PasswordField.getPassword());
            if (validator.validate(username,password)){
                openChessBoard(true,username);
            }else {
                //使用JOptionPane类创建弹出对话框,用showMessageDialog方法显示错误信息
                JOptionPane.showMessageDialog(loginFrame,"Invalid username or password!");
                //this指窗体本身
            }
        });
        RegisterButton.addActionListener(e -> {
            openRegisterFrame();
        });
        GuestLoginButton.addActionListener(e -> {
            openChessBoard(false,"Guest");
        });
    }
    /*private void testClickButton(){
        LoginButton.addActionListener(e -> {
            String username=UsernameField.getText();
            String password=new String(PasswordField.getPassword());
            if (validator.testValidate(username,password)){
                openChessBoard();
            }else {
                //使用JOptionPane类创建弹出对话框,用showMessageDialog方法显示错误信息
                JOptionPane.showMessageDialog(loginFrame,"Invalid username or password!");
                //this指窗体本身
            }
        });
    }*/
    //JFormDesigner自带的,写入组件
    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - 苏云翼
        loginFrame = new JFrame();
        UsernameLabel = new JLabel();
        PasswordLabel = new JLabel();
        TitleLabel = new JLabel();
        RegisterButton = new JButton();
        LoginButton = new JButton();
        PasswordField = new JPasswordField();
        UsernameField = new JTextField();
        GuestLoginButton = new JButton();

        //======== loginFrame ========
        {
            var loginFrameContentPane = loginFrame.getContentPane();
            loginFrameContentPane.setLayout(null);

            //---- UsernameLabel ----
            UsernameLabel.setText("Username:");
            UsernameLabel.setFont(new Font("Segoe Print", Font.PLAIN, 14));
            loginFrameContentPane.add(UsernameLabel);
            UsernameLabel.setBounds(95, 90, UsernameLabel.getPreferredSize().width, 22);

            //---- PasswordLabel ----
            PasswordLabel.setText("Password:");
            PasswordLabel.setFont(new Font("Segoe Print", Font.PLAIN, 14));
            loginFrameContentPane.add(PasswordLabel);
            PasswordLabel.setBounds(new Rectangle(new Point(100, 140), PasswordLabel.getPreferredSize()));

            //---- TitleLabel ----
            TitleLabel.setText("The Chinese Chess Login System");
            TitleLabel.setFont(new Font("Segoe Print", Font.PLAIN, 18));
            loginFrameContentPane.add(TitleLabel);
            TitleLabel.setBounds(new Rectangle(new Point(90, 25), TitleLabel.getPreferredSize()));

            //---- RegisterButton ----
            RegisterButton.setText("Register");
            RegisterButton.setFont(new Font("Segoe Print", Font.PLAIN, 12));
            loginFrameContentPane.add(RegisterButton);
            RegisterButton.setBounds(new Rectangle(new Point(130, 185), RegisterButton.getPreferredSize()));

            //---- LoginButton ----
            LoginButton.setText("Login");
            LoginButton.setFont(new Font("Segoe Print", Font.PLAIN, 12));
            loginFrameContentPane.add(LoginButton);
            LoginButton.setBounds(260, 185, 80, LoginButton.getPreferredSize().height);
            loginFrameContentPane.add(PasswordField);
            PasswordField.setBounds(190, 140, 175, PasswordField.getPreferredSize().height);
            loginFrameContentPane.add(UsernameField);
            UsernameField.setBounds(190, 90, 175, UsernameField.getPreferredSize().height);

            //---- GuestLoginButton ----
            GuestLoginButton.setText("Guest Login");
            GuestLoginButton.setFont(new Font("Segoe Print", Font.PLAIN, 12));
            loginFrameContentPane.add(GuestLoginButton);
            GuestLoginButton.setBounds(165, 245, 135, 25);

            loginFrameContentPane.setPreferredSize(new Dimension(475, 325));
            loginFrame.pack();
            loginFrame.setLocationRelativeTo(loginFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - 苏云翼
    private JFrame loginFrame;
    private JLabel UsernameLabel;
    private JLabel PasswordLabel;
    private JLabel TitleLabel;
    private JButton RegisterButton;
    private JButton LoginButton;
    private JPasswordField PasswordField;
    private JTextField UsernameField;
    private JButton GuestLoginButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
