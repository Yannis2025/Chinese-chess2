package xiangqi.model;
import java.util.Objects;

public class HorsePiece extends AbstractPiece{
    int CurrentRow=getRow();
    int CurrentCol=getCol();
    public HorsePiece(String name, int row, int col, boolean isRed) {
        super(name, row, col, isRed);
    }
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        if(Math.abs(CurrentRow-targetRow)+Math.abs(CurrentCol-targetCol)!=3){
            return false;
        }//位移量判定，马的位移量恒为3
        if(Math.abs(CurrentRow-targetRow)>2||Math.abs(CurrentCol-targetCol)>2){
            return false;
        }//横，竖直线判定
        if(!Objects.equals(null,model.getPieceAt(CurrentRow+1,CurrentCol))){
            if(targetRow-CurrentRow==2){
                return false;
            }
        }
        if(!Objects.equals(null,model.getPieceAt(CurrentRow-1,CurrentCol))){
            if(CurrentRow-targetRow==2){
                return false;
            }
        }
        if(!Objects.equals(null,model.getPieceAt(CurrentRow,CurrentCol+1))){
            if(targetCol-CurrentCol ==2){
                return false;
            }
        }
        if(!Objects.equals(null,model.getPieceAt(CurrentRow,CurrentCol-1))){
            if(CurrentCol-targetCol==2){
                return false;
            }
        }

        CurrentCol = targetCol;
        CurrentRow = targetRow;

        return true;
    }
}
