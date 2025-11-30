package xiangqi.ui.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JButton undoButton;      // 悔棋按钮 (back.jpg)
    private JButton musicButton;     // 音乐开关按钮 (turnon.png/turnoff.png)
    private JButton newGameButton;   // 新局按钮 (new.jpg)
    private boolean musicOn = true;

    public ControlPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(128, 900)); // 和棋盘等高（900像素）

        initComponents();
        setupLayout();
    }

    private void initComponents() {
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

    private void setupLayout() {
        // 添加顶部留白
        add(Box.createVerticalStrut(300));

        // 添加悔棋按钮
        add(undoButton);
        add(Box.createVerticalStrut(50));

        // 添加音乐按钮
        add(musicButton);
        add(Box.createVerticalStrut(50));

        // 添加新局按钮
        add(newGameButton);

        // 添加底部弹性空间
        add(Box.createVerticalGlue());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 绘制ControlPanel.jpg作为背景，缩放到合适高度
        Image controlPanelBg = Toolkit.getDefaultToolkit().getImage("src/resources/Background/ControlPanel.png");
        g.drawImage(controlPanelBg, 0, 0, 128, 900, this);
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
}