package xiangqi.model;
import java.util.Objects;
public class RookPiece extends AbstractPiece{
    public RookPiece(String name, int row, int col, boolean isRed) {
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
        for (int i = CurrentCol+1; i < 9; i++) {
            if(!Objects.equals(model.getPieceAt(CurrentRow,i),null)){
                MaxCol = model.getPieceAt(CurrentRow,i).getCol();
                break;
            }
        }
        for (int i = CurrentCol-1; i >-1 ; i--) {
            if(!Objects.equals(model.getPieceAt(CurrentRow,i),null)){
                MinCol = model.getPieceAt(CurrentRow,i).getCol();
                break;
            }
        }
        for (int i = CurrentRow+1; i < 10; i++) {
            if(!Objects.equals(model.getPieceAt(i,CurrentCol),null)){
                MaxRow = model.getPieceAt(i,CurrentCol).getRow();
                break;
            }
        }
        for (int i = CurrentRow-1; i >-1 ; i--) {
            if(!Objects.equals(model.getPieceAt(i,CurrentCol),null)){
                MinRow = model.getPieceAt(i,CurrentCol).getRow();
                break;
            }
        }//四个循环结构一致，目的是获取车能到的边界（有棋子的那一格），是否能吃子的处理交给move piece方法

        if(targetCol<MinCol||targetCol>MaxCol||targetRow<MinRow||targetRow>MaxRow){
            return false;
        }//超出边界则非法

        CurrentCol = targetCol;
        CurrentRow = targetRow;
        return true;

    }
}
