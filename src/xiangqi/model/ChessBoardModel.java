package xiangqi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChessBoardModel implements Serializable {
    // 储存棋盘上所有的棋子，要实现吃子的话，直接通过pieces.remove(被吃掉的棋子)删除就可以
    private List<AbstractPiece> pieces;
    private List<AbstractPiece> redpieces=new ArrayList<AbstractPiece>();
    private List<AbstractPiece> blackpieces=new ArrayList<AbstractPiece>();;
    private static final int ROWS = 10;
    private static final int COLS = 9;
    private boolean isRedTurn = true; // 红方先行
    private AbstractPiece lastpiece;
    private AbstractPiece killedPiece;
    private AbstractPiece blackgeneralpiece;
    private AbstractPiece redgeneralpiece;
    private int withdrawRow;
    private int withdrawCol;
    public ChessBoardModel() {
        pieces = new ArrayList<>();
        initializePieces();
    }
    public boolean isRedTurn() {
        return isRedTurn;
    }

    public void setRedTurn(boolean isRedTurn) {
        this.isRedTurn = isRedTurn;
    }

    public void setPieces(List<AbstractPiece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }
    public void initializePieces() {
        // 黑方棋子
        pieces.add(blackgeneralpiece=new GeneralPiece("黑将", 0, 4, false));
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
        blackpieces.addAll(pieces);

        // 红方棋子
        pieces.add(redgeneralpiece=new GeneralPiece("红帅", 9, 4, true));
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
        redpieces.addAll(pieces);
        redpieces.removeAll(blackpieces);
        killedPiece=null;
        lastpiece=null;
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
                killedPiece=getPieceAt(newRow,newCol);
                pieces.remove(getPieceAt(newRow,newCol));//有棋子且异色则吃子
                if(killedPiece.isRed()){
                    redpieces.remove(killedPiece);
                }else {
                    blackpieces.remove(killedPiece);
                }
            }
        }else {
            killedPiece=null;
        }
        lastColor = piece.isRed();
        withdrawCol=piece.getCol();
        withdrawRow= piece.getRow();
        lastpiece=piece;
        piece.moveTo(newRow, newCol);
        return true;
    }

    public int winCondition (){
        int result = 0;
        for (AbstractPiece piece:pieces) {
            if (piece instanceof GeneralPiece){
                if(piece.isRed()){
                    result++;
                }else{
                    result--;
                }
            }
        }
        return result;//1为红赢，-1为黑赢，0为继续
    }

    public boolean Withdraw(){
        if(Objects.equals(lastpiece,null)){
            return false;
        }
        for(AbstractPiece piece : pieces){
            if (piece.equals(lastpiece)){
                piece.setRow(withdrawRow);
                piece.setCol(withdrawCol);
                lastColor=!lastColor;
            }
        }
        if(!Objects.equals(null,killedPiece)){
            pieces.add(killedPiece);
            if(killedPiece.isRed()){
                redpieces.add(killedPiece);
            }else{
                blackpieces.add(killedPiece);
            }
        }
        lastpiece=null;
        return true;
    }

    public int check(){
        if(lastColor){
            for(AbstractPiece piece : redpieces) {
                if (assumemove(piece, blackgeneralpiece.getRow(), blackgeneralpiece.getCol())) {
                    return 1;
                }
            }
            for (AbstractPiece piece : blackpieces){
                if (assumemove(piece, redgeneralpiece.getRow(), redgeneralpiece.getCol())) {
                    return -1;//黑方将军
                }
            }
        }else{
            for (AbstractPiece piece : blackpieces){
                if (assumemove(piece, redgeneralpiece.getRow(), redgeneralpiece.getCol())) {
                    return -1;//黑方将军
                }
            }
            for(AbstractPiece piece : redpieces) {
                if (assumemove(piece, blackgeneralpiece.getRow(), blackgeneralpiece.getCol())) {
                    return 1;
                }
            }
        }
        return 0;//继续游戏
    }

    public boolean assumemove(AbstractPiece piece, int newRow, int newCol){
        if (!isValidPosition(newRow, newCol)) {
            return false;
        }//检测移动位置是否合法
        if (newCol==piece.getCol()&&newRow==piece.getRow()){
            return false;
        }//检测是否同格位移，若同格位移则不计算移动
        if(!piece.canMoveTo(newRow,newCol,this)){
            return false;
        }//检测移动是否符合棋子规则
        if(!Objects.equals(null,getPieceAt(newRow,newCol))){//检测移动位置是否有棋子
            if(getPieceAt(newRow,newCol).isRed()==piece.isRed()){
                return false;//有棋子且同色则无法移动
            }
        }
        return true;
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getCols() {
        return COLS;
    }
}
