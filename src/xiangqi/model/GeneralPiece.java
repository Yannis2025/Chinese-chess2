package xiangqi.model;

import java.util.Objects;

/**
 * 帅/将
 */
public class GeneralPiece extends AbstractPiece {
    public GeneralPiece(String name, int row, int col, boolean isRed) {
        super(name, row, col, isRed);
    }
    int CurrentRow=getRow();
    int CurrentCol=getCol();

    @Override
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        boolean Kingsmeet = true;

        for (int i = 0; i < 10; i++) {
            if (!(model.getPieceAt(i, CurrentCol) instanceof GeneralPiece) && !(Objects.equals(null, model.getPieceAt(i, CurrentCol)))) {
                Kingsmeet = false;//如果同列上有一个非将棋子就继续游戏
                break;
            }
        }

        if (Kingsmeet && model.getPieceAt(targetRow, targetCol) instanceof GeneralPiece) {
            return true;
        }

        if (Math.abs(CurrentRow - targetRow) + Math.abs(CurrentCol - targetCol) != 1) {
            return false;
        }//位移量判定，将的位移量恒为一格

        if (targetCol > 2 && targetCol < 6) {
            if (targetRow < 3 || targetRow > 6) {//九宫格内判定
                CurrentCol = targetCol;
                CurrentRow = targetRow;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}

