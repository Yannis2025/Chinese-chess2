package xiangqi.ui.Game;

import xiangqi.util.SoundManager;
import xiangqi.model.AbstractPiece;
import xiangqi.model.ChessBoardModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoardPanel extends JPanel {
    private ChessBoardModel model;
    private final GameFrame gameFrame;
    private SoundManager soundManager;
    private Winpopup winPopup;

    private static final int BOARD_WIDTH = 800;
    private static final int BOARD_HEIGHT = 900;
    private static final int CELL_SIZE = 87;
    private static final int MARGIN_X = 44;
    private static final int MARGIN_Y = 44;
    private static final int PIECE_RADIUS = 46;

    private AbstractPiece selectedPiece = null;
    private boolean gameEnded=false;

    public ChessBoardPanel(ChessBoardModel model, GameFrame gameFrame) {
        this.model = model;
        this.gameFrame = gameFrame;
        this.soundManager=SoundManager.getInstance();//初始化
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        setBackground(new Color(220, 179, 92));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    private void handleMouseClick(int x, int y) {
        if (gameEnded){
            restartGame();
            return;
        }

        int col = Math.round((float)(x - MARGIN_X) / CELL_SIZE);
        int row = Math.round((float)(y - MARGIN_Y) / CELL_SIZE);

        if (!model.isValidPosition(row, col)) {
            return;
        }

        if (selectedPiece == null) {
            selectedPiece = model.getPieceAt(row, col);
        } else {
            //记录移动前是否有棋子被吃
            AbstractPiece targetPiece = model.getPieceAt(row, col);
            boolean isEat = targetPiece != null && targetPiece.isRed() != selectedPiece.isRed();
            //移动棋子
            boolean moveSuccess = model.movePiece(selectedPiece, row, col);

            if (moveSuccess) {
                // 播放音效：吃子优先于移动
                if (isEat) {
                    soundManager.playSound("eat");
                } else {
                    soundManager.playSound("move");
                }
                if (model.winCondition() == 1) {
                    gameEnded = true;
                    showWinPopup("红方胜利");
                    soundManager.stopBackgroundMusic();  // 停止背景音乐
                    soundManager.playSound("win");
                    gameFrame.deleteSaveFile();
                    gameFrame.setGameEnded(true);
                }
                if (model.winCondition() == -1) {
                    gameEnded = true;
                    showWinPopup("黑方胜利");
                    soundManager.stopBackgroundMusic();  // 停止背景音乐
                    soundManager.playSound("win");
                    gameFrame.deleteSaveFile();
                    gameFrame.setGameEnded(true);
                }
                if (model.check() == 1) {
                    Promptpopup Check = new Promptpopup("红方将军1", 621, 413, 1200);
                    add(Check);
                    soundManager.playSound("check");
                } else if (model.check() == -1) {
                    Promptpopup Check = new Promptpopup("黑方将军1", 621, 413, 1200);
                    add(Check);
                    soundManager.playSound("check");
                }
            }
            selectedPiece = null;
        }
        repaint();
    }
    // 重新开始游戏
    public void restartGame() {
        gameEnded = false;
        selectedPiece = null;

        // 移除胜利弹窗
        if (winPopup != null) {
            remove(winPopup);
            winPopup = null;
        }

        // 重新初始化棋盘
        model.getPieces().clear();
        model.initializePieces();

        // 重新开始背景音乐
        soundManager.playBackgroundMusic();

        // 通知GameFrame游戏重新开始
        gameFrame.setGameEnded(false);

        repaint();
    }

    // 显示胜利弹窗
    private void showWinPopup(String winner) {
        winPopup = new Winpopup(winner + "1", model, this, gameFrame);
        winPopup.setLocation((BOARD_WIDTH - winPopup.width) / 2,
                (BOARD_HEIGHT - winPopup.height) / 2);
        add(winPopup);
        setComponentZOrder(winPopup, 0); // 放到最前面
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBoard(g2d);
        drawPieces(g2d);
    }

    private void drawBoard(Graphics2D g) {
        Image chessboard = Toolkit.getDefaultToolkit().getImage("src/resources/Background/棋盘.png");
        g.drawImage(chessboard, 0, 0, BOARD_WIDTH, BOARD_HEIGHT, this);
    }

    private void drawPieces(Graphics2D g) {
        for (AbstractPiece piece : model.getPieces()) {
            int x = MARGIN_X + 11 + piece.getCol() * CELL_SIZE;  // 额外加15像素
            int y = MARGIN_Y + 11 + piece.getRow() * CELL_SIZE;  // 额外加15像素

            boolean isSelected = (piece == selectedPiece);

            Image chess = Toolkit.getDefaultToolkit().getImage(
                    "src/resources/Chess/" + piece.getName() + ".png");
            g.drawImage(chess, x - PIECE_RADIUS, y - PIECE_RADIUS,
                    PIECE_RADIUS * 2, PIECE_RADIUS * 2, this);

            if (isSelected) {
                drawCornerBorders(g, x, y);
            }
        }
    }

    private void drawCornerBorders(Graphics2D g, int centerX, int centerY) {
        if(model.getlastColor()){
            g.setColor(new Color(50, 50, 50));
        }else{
            g.setColor(new Color(205, 50, 50));
        }
        g.setStroke(new BasicStroke(3));

        int cornerSize = 32;
        int lineLength = 12;

        // 左上角的边框
        g.drawLine(centerX - cornerSize, centerY - cornerSize,
                centerX - cornerSize + lineLength, centerY - cornerSize);
        g.drawLine(centerX - cornerSize, centerY - cornerSize,
                centerX - cornerSize, centerY - cornerSize + lineLength);

        // 右上角的边框
        g.drawLine(centerX + cornerSize, centerY - cornerSize,
                centerX + cornerSize - lineLength, centerY - cornerSize);
        g.drawLine(centerX + cornerSize, centerY - cornerSize,
                centerX + cornerSize, centerY - cornerSize + lineLength);

        // 左下角的边框
        g.drawLine(centerX - cornerSize, centerY + cornerSize,
                centerX - cornerSize + lineLength, centerY + cornerSize);
        g.drawLine(centerX - cornerSize, centerY + cornerSize,
                centerX - cornerSize, centerY + cornerSize - lineLength);

        // 右下角的边框
        g.drawLine(centerX + cornerSize, centerY + cornerSize,
                centerX + cornerSize - lineLength, centerY + cornerSize);
        g.drawLine(centerX + cornerSize, centerY + cornerSize,
                centerX + cornerSize, centerY + cornerSize - lineLength);
    }

    public void setModel(ChessBoardModel model) {
        this.model = model;
        this.selectedPiece = null;
        repaint();
    }

    public boolean isGameEnded(){return gameEnded;}

}