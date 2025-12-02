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
    public boolean Kingsmeet = false;

    @Override
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        boolean kinginline = false;
        int kingRow=0;
        for (int i = 0; i < 10; i++) {
            if(model.getPieceAt(i, targetCol) instanceof  GeneralPiece){
                kinginline=true;
                kingRow=i;
            }
        }
        if(kinginline) {
            Kingsmeet=true;
            for (int i = Math.min(kingRow,targetRow); i <Math.max(kingRow,targetRow) ; i++) {
                if(!Objects.equals(null,model.getPieceAt(i,targetCol))&&!(model.getPieceAt(i,targetCol) instanceof GeneralPiece)){
                    Kingsmeet =false;
                }
            }
        }

        if (Kingsmeet) {
            return false;
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

