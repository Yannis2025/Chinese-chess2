package xiangqi.model;

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
        }else {
            return false;
        }
    }
    }

