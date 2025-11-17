package xiangqi.ui.Game;

import xiangqi.model.AbstractPiece;
import xiangqi.model.ChessBoardModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessBoardPanel extends JPanel {
    private final ChessBoardModel model;
    /**
     * 单个棋盘格子的尺寸（px）
     */
    private static final int CELL_SIZE = 64;
    /**
     * 棋盘边界与窗口边界的边距
     */
    private static final int MARGIN = 40;
    /**
     * 棋子的半径
     */
    private static final int PIECE_RADIUS = 35;
    private AbstractPiece selectedPiece = null;
     public ChessBoardPanel(ChessBoardModel model) {
        this.model = model;
        setPreferredSize(new Dimension(
                CELL_SIZE * (ChessBoardModel.getCols() - 1) + MARGIN * 2,
                CELL_SIZE * (ChessBoardModel.getRows() - 1) + MARGIN * 2
        ));
        setBackground(new Color(220, 179, 92));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
    }

    private void handleMouseClick(int x, int y) {
        int col = Math.round((float)(x - MARGIN) / CELL_SIZE);
        int row = Math.round((float)(y - MARGIN) / CELL_SIZE);

        if (!model.isValidPosition(row, col)) {
            return;
        }

        if (selectedPiece == null) {
            selectedPiece = model.getPieceAt(row, col);
        } else {
            model.movePiece(selectedPiece, row, col);
            selectedPiece = null;
        }

        if(model.winCondition() == 1){
            setVisible(false);
            //+红方胜利UI
        }
        if(model.winCondition() == -1){
            setVisible(false);
            //+黑方胜利UI
        }

        // 处理完点击事件后，需要重新绘制ui界面才能让界面上的棋子“移动”起来
        // Swing 会将多个请求合并后再重新绘制，因此调用 repaint 后gui不会立刻变更
        // repaint 中会调用 paintComponent，从而重新绘制gui上棋子的位置等
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Demo的GUI都是由Swing中基本的组件组成的，比如背景的格子是用许多个line组合起来实现的，棋子是先绘制一个circle再在上面绘制一个text实现的
        // 因此绘制GUI的过程中需要自己手动计算每个组件的位置（坐标）
        drawBoard(g2d);
        drawPieces(g2d);
    }

    /**
     * 绘制棋盘
     */
    private void drawBoard(Graphics2D g) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image Chessboard =  toolkit.getImage("src/resources/Background/棋盘.png");
        g.drawImage(Chessboard,0,0,2*MARGIN+8*CELL_SIZE,2*MARGIN+9*CELL_SIZE,this);
    }
    /**
     * 绘制棋子
     */
    private void drawPieces(Graphics2D g) {
        // 遍历棋盘上的每一个棋子，每次循环绘制该棋子
        for (AbstractPiece piece : model.getPieces()) {
            // 计算每一个棋子的坐标
            int x = MARGIN + piece.getCol() * CELL_SIZE;
            int y = MARGIN + piece.getRow() * CELL_SIZE;

            boolean isSelected = (piece == selectedPiece);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image chess= toolkit.getImage("src/resources/Chess/"+piece.getName()+".png");
            g.drawImage(chess,x - PIECE_RADIUS, y - PIECE_RADIUS, PIECE_RADIUS * 2, PIECE_RADIUS * 2,this);
            if (isSelected) {
                drawCornerBorders(g, x, y);
            }
        }
    }

    /**
     * 绘制选中棋子时的蓝色外边框效果
     */
    private void drawCornerBorders(Graphics2D g, int centerX, int centerY) {

        if(model.getlastColor()){
            g.setColor(new Color(50, 50, 50));
        }else{
            g.setColor(new Color(205, 50, 50));
        }
        g.setStroke(new BasicStroke(3));

        int cornerSize = 32;
        int lineLength = 12;

        // 选中效果的边框实际上是8条line，每两个line组成一个角落的边框

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
}
