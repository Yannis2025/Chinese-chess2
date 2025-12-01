package xiangqi.ui.Game;

import xiangqi.model.ChessBoardModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Winpopup extends JLabel {
    private ImageIcon originalImage;
    int width = 621;
    int height = 413;
    private ChessBoardPanel boardPanel;
    private GameFrame gameFrame;

    public Winpopup(String address, ChessBoardModel model,ChessBoardPanel boardPanel,GameFrame gameFrame) {
        this.boardPanel=boardPanel;
        this.gameFrame=gameFrame;
        originalImage=new ImageIcon("src/resources/Popup/"+address+".png");
        setSize(width,height);
        ImageIcon scaleImage = new ImageIcon(originalImage.getImage().getScaledInstance(width,height,Image.SCALE_SMOOTH));
        this.setIcon(scaleImage);
        setLocation(100,200);
        setOpaque(false);//设置透明

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                restartGame();
            }
        });
        setLayout(null);//使用绝对定位
    }
    // 重新开始游戏
    private void restartGame() {
        // 从父容器中移除自己
        Container parent = getParent();
        if (parent != null) {
            parent.remove(Winpopup.this);
            parent.repaint();
        }

        // 重新开始游戏
        boardPanel.restartGame();
    }

    @Override
    public boolean contains(int x, int y) {
        // 覆盖contains方法，让整个弹窗区域都可以点击
        return x >= 0 && x < getWidth() && y >= 0 && y < getHeight();
    }

}



