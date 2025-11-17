package xiangqi.ui.Game;

import xiangqi.model.ChessBoardModel;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame (String title){//后续要在游戏界面添加功能在这里修改即可
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(boardPanel);
        pack();
        setLocationRelativeTo(null);
    }
    ChessBoardModel model = new ChessBoardModel();
    ChessBoardPanel boardPanel = new ChessBoardPanel(model);

}
