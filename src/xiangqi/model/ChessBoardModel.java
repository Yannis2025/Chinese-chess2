package xiangqi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChessBoardModel {
    // 储存棋盘上所有的棋子，要实现吃子的话，直接通过pieces.remove(被吃掉的棋子)删除就可以
    private final List<AbstractPiece> pieces;
    private static final int ROWS = 10;
    private static final int COLS = 9;

    public ChessBoardModel() {
        pieces = new ArrayList<>();
        initializePieces();
    }
    private void initializePieces() {
        // 黑方棋子
        pieces.add(new GeneralPiece("黑将", 0, 4, false));
        pieces.add(new AdvisorPiece("黑士", 0, 5, false));
        pieces.add(new AdvisorPiece("黑士", 0, 3, false));
        pieces.add(new ElephantPiece("黑象", 0, 6, false));
        pieces.add(new ElephantPiece("黑象", 0, 2, false));
        pieces.add(new HorsePiece("黑馬", 0, 7, false));
        pieces.add(new HorsePiece("黑馬", 0, 1, false));
        pieces.add(new RookPiece("黑車", 0, 0, false));
        pieces.add(new RookPiece("黑車", 0, 8, false));
        pieces.add(new CannonPiece("黑炮", 2, 1, false));
        pieces.add(new CannonPiece("黑炮", 2, 7, false));
        pieces.add(new SoldierPiece("黑卒", 3, 0, false));
        pieces.add(new SoldierPiece("黑卒", 3, 2, false));
        pieces.add(new SoldierPiece("黑卒", 3, 4, false));
        pieces.add(new SoldierPiece("黑卒", 3, 6, false));
        pieces.add(new SoldierPiece("黑卒", 3, 8, false));

        // 红方棋子
        pieces.add(new GeneralPiece("红帅", 9, 4, true));
        pieces.add(new AdvisorPiece("红仕", 9, 5, true));
        pieces.add(new AdvisorPiece("红仕", 9, 3, true));
        pieces.add(new ElephantPiece("红相", 9, 6, true));
        pieces.add(new ElephantPiece("红相", 9, 2, true));
        pieces.add(new HorsePiece("红馬", 9, 7, true));
        pieces.add(new HorsePiece("红馬", 9, 1, true));
        pieces.add(new RookPiece("红車", 9, 0, true));
        pieces.add(new RookPiece("红車", 9, 8, true));
        pieces.add(new CannonPiece("红砲", 7, 1, true));
        pieces.add(new CannonPiece("红砲", 7, 7, true));
        pieces.add(new SoldierPiece("红兵", 6, 0, true));
        pieces.add(new SoldierPiece("红兵", 6, 2, true));
        pieces.add(new SoldierPiece("红兵", 6, 4, true));
        pieces.add(new SoldierPiece("红兵", 6, 6, true));
        pieces.add(new SoldierPiece("红兵", 6, 8, true));
    }

    public List<AbstractPiece> getPieces() {
        return pieces;
    }

    public AbstractPiece getPieceAt(int row, int col) {
        for (AbstractPiece piece : pieces) {
            if (piece.getRow() == row && piece.getCol() == col) {
                return piece;
            }
        }
        return null;
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }

    boolean lastColor = false;
    public boolean getlastColor(){return lastColor;}

    public boolean movePiece(AbstractPiece piece, int newRow, int newCol) {
        if (!isValidPosition(newRow, newCol)) {
            return false;
        }//检测移动位置是否合法
        if(lastColor ==  piece.isRed()){
            return false;
        }//检测回合玩家是否正确
        if (newCol==piece.getCol()&&newRow==piece.getRow()){
            return false;
        }//检测是否同格位移，若同格位移则不计算移动
        if(!piece.canMoveTo(newRow,newCol,this)){
            return false;
        }//检测移动是否符合棋子规则
        if(!Objects.equals(null,getPieceAt(newRow,newCol))){//检测移动位置是否有棋子
            if(getPieceAt(newRow,newCol).isRed()==piece.isRed()){
                return false;//有棋子且同色则无法移动
            }else {
                pieces.remove(getPieceAt(newRow,newCol));//有棋子且异色则吃子
            }
        }
        lastColor = piece.isRed();
        piece.moveTo(newRow, newCol);
        return true;
    }

    public int winCondition (){
        int result = 0;
        AbstractPiece RedGeneralPiece = null;
        AbstractPiece BlackGeneralPiece = null;
        for (AbstractPiece piece:pieces) {
            if (piece instanceof GeneralPiece){
                if(piece.isRed()){
                    RedGeneralPiece = piece;
                    result++;
                }else{
                    BlackGeneralPiece = piece;
                    result--;
                }
            }
        }
        if(result == 0){//=0时必然没有空指针
            if (RedGeneralPiece.getCol()==BlackGeneralPiece.getCol()){
                int Col = RedGeneralPiece.getCol();
                if(lastColor){
                    result--;//上一个颜色是红，下完黑方胜
                }else {
                    result++;//上一个颜色是黑。下完红方胜
                }
                for (int i = 0; i < 10; i++) {
                    if (!(getPieceAt(i, Col) instanceof GeneralPiece) && !(Objects.equals(null, getPieceAt(i, Col)))) {
                        result = 0;//如果同列上有一个非将棋子就继续游戏
                        break;
                    }
                }
            }
        }
        return result;//1为红赢，-1为黑赢，0为继续
    }



    public static int getRows() {
        return ROWS;
    }

    public static int getCols() {
        return COLS;
    }
}
