package xiangqi.ui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControlPanel extends JPanel {
    private JButton undoButton;      // 悔棋按钮 (back.jpg)
    private JButton musicButton;     // 音乐开关按钮 (turnon.png/turnoff.png)
    private JButton newGameButton;   // 新局按钮 (new.jpg)
    private boolean musicOn = true;
    private JLabel blackNicknameLabel;
    private JLabel redNicknameLabel;
    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(158, 900)); // 和棋盘等高（900像素）

        initComponents();
        setupLayout();
    }
    // 设置昵称
    public void setNicknames(String blackNickname, String redNickname) {
        if (blackNickname != null && !blackNickname.trim().isEmpty()) {
            blackNicknameLabel.setText(blackNickname.trim());
        }
        if (redNickname != null && !redNickname.trim().isEmpty()) {
            redNicknameLabel.setText(redNickname.trim());
        }
    }
    private void initComponents() {
        blackNicknameLabel = new JLabel("黑方");
        redNicknameLabel = new JLabel("红方");

        //悔棋按钮
        try {
            ImageIcon undoIcon = new ImageIcon("src/resources/Button/back.jpg");
            undoButton = new JButton(undoIcon);
        } catch (Exception e) {
            undoButton = new JButton("悔棋");
        }
        undoButton.setBorderPainted(false);
        undoButton.setContentAreaFilled(false);
        undoButton.setFocusPainted(false);

        // 音乐开关按钮
        try {
            ImageIcon musicOnIcon = new ImageIcon("src/resources/Button/turnon.png");
            musicButton = new JButton(musicOnIcon);
        } catch (Exception e) {
            musicButton = new JButton("音乐开");
        }
        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);

        // 新局按钮
        try {
            ImageIcon newGameIcon = new ImageIcon("src/resources/Button/new.jpg");
            newGameButton = new JButton(newGameIcon);
        } catch (Exception e) {
            newGameButton = new JButton("新局");
        }
        newGameButton.setBorderPainted(false);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setFocusPainted(false);

        // 设置统一的按钮样式
        styleButtons();
        styleNicknameLabels();
    }

    private void styleButtons() {
        // 设置按钮尺寸
        undoButton.setPreferredSize(new Dimension(100, 39));
        undoButton.setMaximumSize(new Dimension(100, 39));

        musicButton.setPreferredSize(new Dimension(32, 32));
        musicButton.setMaximumSize(new Dimension(32, 32));

        newGameButton.setPreferredSize(new Dimension(100, 39));
        newGameButton.setMaximumSize(new Dimension(100, 39));

        // 居中对齐
        undoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        musicButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void styleNicknameLabels() {
        blackNicknameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        blackNicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        blackNicknameLabel.setFont(new Font("楷体", Font.BOLD, 19));
        blackNicknameLabel.setForeground(Color.BLACK);
        blackNicknameLabel.setMaximumSize(new Dimension(140, 30));

        redNicknameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        redNicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        redNicknameLabel.setFont(new Font("楷体", Font.BOLD, 19));
        redNicknameLabel.setForeground(new Color(200, 0, 0));
        redNicknameLabel.setMaximumSize(new Dimension(140, 30));
    }

    private void setupLayout() {
        // 添加顶部留白
        add(Box.createVerticalStrut(35));

        JPanel blackpanel = new JPanel();
        blackpanel.setLayout(new OverlayLayout(blackpanel));
        blackpanel.setOpaque(false);
        blackpanel.add(blackavatar);
        blackavatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        blackpanel.add(blackavatarframe);
        blackavatarframe.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(blackpanel);

        // 添加黑方标签（在blackpanel下方）
        add(Box.createVerticalStrut(5));
        add(blackNicknameLabel);

        add(Box.createVerticalStrut(140));
        // 添加悔棋按钮
        add(undoButton);
        add(Box.createVerticalStrut(50));

        // 添加音乐按钮
        add(musicButton);
        add(Box.createVerticalStrut(50));

        // 添加新局按钮
        add(newGameButton);
        add(Box.createVerticalStrut(100));

        add(Box.createVerticalStrut(40));



        JPanel redpanel = new JPanel();
        redpanel.setLayout(new OverlayLayout(redpanel));
        redpanel.setOpaque(false);
        redpanel.add(redavatar);
        redavatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        redpanel.add(redavatarframe);
        redavatarframe.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(redpanel);

        // 添加红方标签
        add(redNicknameLabel);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制ControlPanel.jpg作为背景，缩放到合适高度
        Image controlPanelBg = Toolkit.getDefaultToolkit().getImage("src/resources/Background/ControlPanel.png");
        g.drawImage(controlPanelBg, 0, 0, 158, 900, this);
    }

    // 增加监听器
    public void setUndoListener(ActionListener listener) {
        undoButton.addActionListener(listener);
    }

    public void setMusicListener(ActionListener listener) {
        musicButton.addActionListener(listener);
    }

    public void setNewGameListener(ActionListener listener) {
        newGameButton.addActionListener(listener);
    }

    public void updateMusicButton(boolean isOn) {
        musicOn = isOn;
        try {
            if (isOn) {
                ImageIcon musicOnIcon = new ImageIcon("src/resources/Button/turnon.png");
                musicButton.setIcon(musicOnIcon);
            } else {
                ImageIcon musicOffIcon = new ImageIcon("src/resources/Button/turnoff.png");
                musicButton.setIcon(musicOffIcon);
            }
        } catch (Exception e) {
            musicButton.setText("音乐: " + (isOn ? "开" : "关"));
        }
    }

    public boolean isMusicOn() {
        return musicOn;
    }
    private Icon redavatarframe =new Icon("src/resources/Icon/红色头像框",140,140,1);
    private Icon blackavatarframe =new Icon("src/resources/Icon/黑色头像框",140,140,1);
    private Icon redavatar =new Icon("src/resources/Icon/红",120,120,4);
    private Icon blackavatar =new Icon("src/resources/Icon/黑",120,120,4);
    class Icon extends JLabel{
        int count =0;
        int sort ;
        ImageIcon image=null;
        Icon(String address,int width,int height,int sort){
            image = new ImageIcon(address + (count % sort + 1) + ".png");
            this.setIcon(PressImage(image,width,height));
            this.setSize(width,height);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    count++;
                    image=new ImageIcon(address + (count % sort + 1) + ".png");
                    Icon.this.setIcon(PressImage(image,width,height));
                }
            });
        }
    }
    private ImageIcon PressImage(ImageIcon originalimage,int width,int height){
        Image resetimage = originalimage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon result = new ImageIcon(resetimage);
        return result;
    }
}