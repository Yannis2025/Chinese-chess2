package xiangqi.model;

import java.util.Objects;

public class ElephantPiece extends AbstractPiece{
    int CurrentRow=getRow();
    int CurrentCol=getCol();
    public ElephantPiece(String name, int row, int col, boolean isRed) {
        super(name, row, col, isRed);
    }
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        if(Math.abs(CurrentRow-targetRow)!=2||Math.abs(CurrentCol-targetCol)!=2){
            return false;
        }//位移量判定，象的位移量恒为横2竖2
        if(isRed()){
            boolean crossedRiver = targetRow < 5;
            if(crossedRiver){
                return false;
            }
        }else{
            boolean crossedRiver = targetRow >= 5;
            if(crossedRiver){
                return false;
            }
        }//过河判定



        if(!Objects.equals(null,model.getPieceAt(CurrentRow+1,CurrentCol+1))){
            if(targetRow-CurrentRow==2&&targetCol-CurrentCol==2){
                return false;
            }
        }
        if(!Objects.equals(null,model.getPieceAt(CurrentRow-1,CurrentCol+1))){
            if(CurrentRow-targetRow==2&&targetCol-CurrentCol ==2){
                return false;
            }
        }
        if(!Objects.equals(null,model.getPieceAt(CurrentRow+1,CurrentCol-1))){
            if(CurrentCol-targetCol ==2&&targetRow-CurrentRow==2){
                return false;
            }
        }
        if(!Objects.equals(null,model.getPieceAt(CurrentRow-1,CurrentCol-1))){
            if(CurrentCol-targetCol==2&&CurrentRow-targetRow==2){
                return false;
            }
        }

        CurrentCol = targetCol;
        CurrentRow = targetRow;

        return true;
    }
}
