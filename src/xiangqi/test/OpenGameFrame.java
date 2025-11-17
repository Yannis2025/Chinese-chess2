package xiangqi.test;

import xiangqi.ui.Game.GameFrame;

import javax.swing.*;

public class OpenGameFrame {//后续调试GameFrame里的内容直接在这里调试即可
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame("中国象棋");
            gameFrame.setVisible(true);
        });
    }
}
