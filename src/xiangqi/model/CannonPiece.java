package xiangqi.model;

import java.util.Objects;

public class CannonPiece extends AbstractPiece{
    public CannonPiece(String name, int row, int col, boolean isRed) {
        super(name, row, col, isRed);
    }
    int CurrentRow=getRow();
    int CurrentCol=getCol();
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        if(targetRow!=CurrentRow&&targetCol!=CurrentCol){
            return false;
        }
        int MaxCol = 8;
        int MinCol = 0;
        int MaxRow = 9;
        int MinRow = 0;
        AbstractPiece MaxColPiece =null;
        AbstractPiece MinColPiece =null;
        AbstractPiece MaxRowPiece =null;
        AbstractPiece MinRowPiece =null;
        AbstractPiece selectedPiece = model.getPieceAt(targetRow,targetCol);
        for (int i = CurrentCol+1; i < 9; i++) {
            if(!Objects.equals(model.getPieceAt(CurrentRow,i),null)){
                MaxColPiece =  model.getPieceAt(CurrentRow,i);
                MaxCol = MaxColPiece.getCol()-1;
                break;
            }
        }
        for (int i = CurrentCol-1; i >-1 ; i--) {
            if(!Objects.equals(model.getPieceAt(CurrentRow,i),null)){
                MinColPiece = model.getPieceAt(CurrentRow,i);
                MinCol = MinColPiece.getCol()+1;
                break;
            }
        }
        for (int i = CurrentRow+1; i < 10; i++) {
            if(!Objects.equals(model.getPieceAt(i,CurrentCol),null)){
                MaxRowPiece = model.getPieceAt(i,CurrentCol);
                MaxRow = MaxRowPiece.getRow()-1;
                break;
            }
        }
        for (int i = CurrentRow-1; i >-1 ; i--) {
            if(!Objects.equals(model.getPieceAt(i,CurrentCol),null)){
                MinRowPiece = model.getPieceAt(i,CurrentCol);
                MinRow = MinRowPiece.getRow()+1;
                break;
            }
        }//四个循环结构一致，目的是获取炮能到的边界（有棋子的前一格，因为炮直走无论如何不能吃子）

        if(!Objects.equals(MaxColPiece,null)) {//防止空指针
            for (int i = MaxColPiece.getCol() + 1; i < 9; i++) {
                if (!Objects.equals(model.getPieceAt(CurrentRow, i), null)) {//防止空指针
                    if (model.getPieceAt(CurrentRow, i).isRed() != this.isRed()) {
                        if (model.getPieceAt(CurrentRow, i).equals(selectedPiece)) {//当移动位置恰好是离边界棋子最近的异色棋子时，移动合法
                            CurrentCol = targetCol;
                            CurrentRow = targetRow;
                            return true;
                        }
                        break;
                    }
                }
            }
        }//下同

        if(!Objects.equals(MinColPiece,null)) {
            for (int i = MinColPiece.getCol() - 1; i > -1; i--) {
                if (!Objects.equals(model.getPieceAt(CurrentRow, i), null)) {
                    if (model.getPieceAt(CurrentRow, i).isRed() != this.isRed()) {
                        if (model.getPieceAt(CurrentRow, i).equals(selectedPiece)) {
                            CurrentCol = targetCol;
                            CurrentRow = targetRow;
                            return true;
                        }
                        break;
                    }
                }
            }
        }

        if(!Objects.equals(MaxRowPiece,null)) {
            for (int i = MaxRowPiece.getRow() + 1; i < 10; i++) {
                if (!Objects.equals(model.getPieceAt(i, CurrentCol), null)) {
                    if (model.getPieceAt(i, CurrentCol).isRed() != this.isRed()) {
                        if (model.getPieceAt(i, CurrentCol).equals(selectedPiece)) {
                            CurrentCol = targetCol;
                            CurrentRow = targetRow;
                            return true;
                        }
                        break;
                    }
                }
            }
        }

        if(!Objects.equals(MinRowPiece,null)) {
            for (int i = MinRowPiece.getRow() - 1; i > -1; i--) {
                if (!Objects.equals(model.getPieceAt(i, CurrentCol), null)) {
                    if (model.getPieceAt(i, CurrentCol).isRed() != this.isRed()) {
                        if (model.getPieceAt(i, CurrentCol).equals(selectedPiece)) {
                            CurrentCol = targetCol;
                            CurrentRow = targetRow;
                            return true;
                        }
                        break;
                    }
                }
            }
        }//四个循环结构一致，吃子检测

        if(targetCol<MinCol||targetCol>MaxCol||targetRow<MinRow||targetRow>MaxRow){
            return false;
        }


        CurrentCol = targetCol;
        CurrentRow = targetRow;
        return true;

    }
}
