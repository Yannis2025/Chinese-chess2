package xiangqi.model;

public class AdvisorPiece extends AbstractPiece{
    public AdvisorPiece(String name, int row, int col, boolean isRed) {
        super(name, row, col, isRed);
    }
    int CurrentRow=getRow();
    int CurrentCol=getCol();
    public boolean canMoveTo(int targetRow, int targetCol, ChessBoardModel model) {
        if (Math.abs(CurrentRow - targetRow) + Math.abs(CurrentCol - targetCol) != 2) {
            return false;
        }//位移量判定，士的位移量恒为两格
        if(Math.abs(CurrentRow - targetRow) == 2 ||Math.abs(CurrentCol - targetCol) == 2){
            return false;
        }//横，竖判定
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
