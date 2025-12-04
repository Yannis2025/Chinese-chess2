package xiangqi.ui.Game;

import xiangqi.ui.Login.LoginFrame;
import xiangqi.ui.Game.GameFrame;
import xiangqi.util.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;

public class StartGameFrame extends JFrame {
    private ImageIcon backgroundImage;
    private ImageIcon startButtonImage;
    private ImageIcon quitButtonImage;
    private JButton startButton;
    private JButton quitButton;
    private boolean isLoginFrameOpen=false;
    private LoginFrame loginFrame;
    private JPanel contentPane;

    public StartGameFrame(){
        initComponents();
        setupUI();
        setupEventListeners();
        setupKeyboardListener();
    }

    private void initComponents(){
        backgroundImage = new ImageIcon("src/resources/Background/StartGamePanel.png");
        startButtonImage = new ImageIcon("src/resources/Background/StartGameButton.png");
        quitButtonImage = new ImageIcon("src/resources/Background/QuitGameButton.png");

        Image startImage=startButtonImage.getImage();
        //括号内为一个ImageObserver类型的参数,会接受图片加载进度的通知,null就是不需要
        int startWidth=startImage.getWidth(null)/8;
        int startHeight = startImage.getHeight(null) / 8;
        //getScaledInstance() 是 java.awt.Image 类的抽象方法，用于创建原图的缩放副本。它有三个参数：
        //width：目标宽度（若为负值，则按原图比例自动计算）。height：目标高度（若为负值，则按原图比例自动计算）。
        //hints：缩放算法的优化策略（如速度优先或质量优先）
        Image scaledStartImage=startImage.getScaledInstance(startWidth,startHeight,Image.SCALE_SMOOTH);
        //它的作用是将上一步缩放好的 Image 对象（scaledStartImg），重新封装成一个 ImageIcon 对象（scaledStartIcon），以便能直接用在 JLabel、JButton 等 Swing 组件上显示。
        ImageIcon scaledStartIcon=new ImageIcon(scaledStartImage);

        Image quitImg = quitButtonImage.getImage();
        int quitWidth = quitImg.getWidth(null) / 8;
        int quitHeight = quitImg.getHeight(null) / 8;
        Image scaledQuitImg = quitImg.getScaledInstance(quitWidth, quitHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledQuitIcon = new ImageIcon(scaledQuitImg);

        //创建透明按钮
        startButton = new JButton(scaledStartIcon);
        quitButton = new JButton(scaledQuitIcon);

        // 设置按钮样式
        styleButton(startButton);
        styleButton(quitButton);

        //创建一个可以自动填充背景图片的自定义面板
        contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                //类似repaint的方法
                super.paintComponent(g);
                // 绘制背景图片
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    private void styleButton(JButton button){
        //用于“美化按钮”或实现“透明按钮”效果的经典组合。它们通常一起使用，目的是为了移除按钮上所有默认的、可能与你的界面设计冲突的装饰，从而只保留你想要显示的图标或文字。
        button.setBorderPainted(false);//不绘制边框
        button.setContentAreaFilled(false);//取消填充
        button.setFocusPainted(false);//取消点击按钮时出现的虚线或高亮边框
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));//移到按钮上是变成手形光标
        button.setOpaque(false);//透明
    }

    private void setupUI(){
        setTitle("中国象棋 - 开始界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置内容面板
        contentPane.setLayout(null);
        setContentPane(contentPane);//将自定义的contentPane设置为当前窗口
        // 获取的是一个动态的、与环境相关的、最适合当前显示状态的尺寸(即缩放后的)
        int startButtonWidth =159;
        int startButtonHeight =41;
        int quitButtonWidth =158;
        int quitButtonHeight =40;
        int startButtonX = 250;
        int startButtonY = 454;
        int quitButtonX = 590;
        int quitButtonY = 454;
        startButton.setBounds(startButtonX, startButtonY, startButtonWidth, startButtonHeight);
        quitButton.setBounds(quitButtonX, quitButtonY, quitButtonWidth, quitButtonHeight);
        contentPane.add(startButton);
        contentPane.add(quitButton);
        setSize(1000, 711);
        //禁止用户通过拖拽窗口边框来改变窗口大小
        setResizable(false);
        setLocationRelativeTo(null);
        // 让一个通常不获取焦点的容器（contentPane）变得可以获取焦点
        contentPane.setFocusable(true);
    }

    private void setupEventListeners() {
        // 开始游戏按钮点击事件
        startButton.addActionListener(e -> openLoginFrame());

        // 退出游戏按钮点击事件,System.exit(0)终止整个JVM
        quitButton.addActionListener(e -> System.exit(0));
    }

    //键盘监听器
    private void setupKeyboardListener() {
        contentPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //判断按键是否为VK_ENTER,即回车
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    openLoginFrame();
                }
            }
        });
    }

    private void openLoginFrame() {
        if (isLoginFrameOpen) return;

        isLoginFrameOpen = true;

        // 创建登录界面
        loginFrame = new LoginFrame(this);
        loginFrame.show();

        // 使开始界面不可点击但保持显示
        setEnabled(false);
    }

    public void closeLoginFrame() {
        if (loginFrame != null) {
            loginFrame.getFrame().dispose();
            loginFrame = null;
        }

        isLoginFrameOpen = false;
        startButton.setEnabled(true);
        quitButton.setEnabled(true);
        setEnabled(true);//启用面板
        contentPane.requestFocusInWindow();
    }

    public void showFrame() {
        setVisible(true);
    }

    public void openGameFrame(boolean isLoggedIn, String username, String redNickname, String blackNickname) {
        this.dispose();
        // 创建游戏界面，传递this引用以便游戏结束后返回
        GameFrame gameFrame = new GameFrame("中国象棋", isLoggedIn, username, redNickname, blackNickname, this);
        gameFrame.setVisible(true);
    }

}
