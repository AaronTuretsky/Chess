package pieceTypes;

import java.util.ArrayList;

/**
 * Class representing the chess piece of King
 *
 *@author Aaron Turetsky
 */
public class King extends Piece {

    /**
     * A boolean reflecting whether or not a king is able to castle
     */
    private boolean castle;

    /**
     * Constructor for king
     * @param player
     */
    public King(Player player){
        super(player);
        castle = true;
    }

    /**
     * Constructor for king
     * Typically called when a deep copy is made
     * @param piece - The piece who's fields are being copied to the king being created
     * @param player - The player who owns the king being created
     */
    public King(Piece piece, Player player){
        super(piece, player);
        King orig = (King) piece;
        castle = orig.castle;
    }

    /**
     * Returns a list of natural potential moves the king can make
     * The list contains moves that may be invalid due to other pieces or position
     * @param board - The 2d array containing the current game
     * @return A list of moves that the selected piece is naturally capable off
     */
    public ArrayList<String> potentialMoves(Piece[][] board) {
        ArrayList<String> moves = new ArrayList<>();

        String[] coords = getlocation().split(" ");
        int row = Integer.parseInt(coords[1]);
        int column = Integer.parseInt(coords[4]);

        if (row+1 <= 7 && (board[row + 1][column] == null || board[row + 1][column].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row + 1, column));
        }
        if (row-1 >= 0 && (board[row - 1][column] == null || board[row - 1][column].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row - 1, column));
        }
        if (column+1 <= 7 && (board[row][column + 1] == null || board[row][column+1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row, column + 1));
        }
        if (column-1 >= 0 && (board[row][column - 1] == null || board[row][column - 1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row, column - 1));
        }
        if (row-1 >= 0 && column - 1 >= 0 && (board[row - 1][column - 1] == null || board[row - 1][column - 1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row - 1, column - 1));
        }
        if (row-1 >= 0 && column + 1 <= 7 && (board[row - 1][column + 1] == null || board[row - 1][column + 1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row - 1, column + 1));
        }
        if (row+1 <= 7 && column - 1 >= 0 && (board[row + 1][column - 1] == null || board[row + 1][column - 1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row + 1, column - 1));
        }
        if (row+1 <= 7 && column + 1 <= 7 && (board[row + 1][column + 1] == null || board[row + 1][column + 1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row + 1, column + 1));
        }
        return moves;
    }

    /**
     * Returns a string representation of the piece
     * @return - A string representation of the piece
     */
    public String toString(){
        return this.getPlayer().getColor().charAt(0)+"O";
    }

    public boolean isChecked(Piece[][] board){
        String[] loc = this.getlocation().split(" ");
        Integer row = Integer.parseInt(loc[1]);
        Integer column = Integer.parseInt(loc[4]);
        String color = this.getPlayer().getColor();

        if(this.getPlayer().getColor().equals("black")){
            if(row+1 < 8){
                if(column +1 <= 7) {
                    if (board[row + 1][column + 1] != null && board[row + 1][column + 1].getClass().getName().equals("pieceTypes.Pawn")) {
                        if(!board[row + 1][column + 1].getPlayer().getColor().equals("black")){
                            return true;
                        }
                    }
                }
                if(column-1 >= 0) {
                    if (board[row + 1][column - 1] != null && board[row + 1][column - 1].getClass().getName().equals("pieceTypes.Pawn")) {
                        if(!board[row + 1][column - 1].getPlayer().getColor().equals("black")){
                            return true;
                        }
                    }
                }
            }
        } else if(this.getPlayer().getColor().equals("white")){
            if(row-1 >= 0){
                if(column+1 <= 7) {
                    if (board[row - 1][column + 1] != null && board[row - 1][column + 1].getClass().getName().equals("pieceTypes.Pawn")) {
                        String x = board[row - 1][column + 1].getPlayer().getColor();
                        if(!x.equals(color)){
                            return true;
                        }
                    }
                }
                if(column-1 >= 0) {
                    if (board[row - 1][column - 1] != null && board[row - 1][column - 1].getClass().getName().equals("pieceTypes.Pawn")) {
                        String x = board[row - 1][column - 1].getPlayer().getColor();
                        if(!x.equals(color)){
                            return true;
                        }
                    }
                }
            }
        }

        Piece check = null;
        Integer r = row-1;
        Integer c = column-1;

        while(check == null && r >= 0 && c >= 0){
            check = board[r][c];
            if(check != null){
                if(check.getClass().getName().equals("pieceTypes.Queen") || check.getClass().getName().equals("pieceTypes.Bishop")){
                    String x = check.getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            r-=1;
            c-=1;
        }

        check = null;
        r = row+1;
        c = column+1;

        while(check == null && r <= 7 && c <= 7){
            check = board[r][c];
            if(check != null){
                if(check.getClass().getName().equals("pieceTypes.Queen") || check.getClass().getName().equals("pieceTypes.Bishop")){
                    String x = board[r][c].getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            r+=1;
            c+=1;
        }

        check = null;
        r = row+1;
        c = column-1;

        while(check == null && r <= 7 && c >= 0){
            check = board[r][c];
            if(check != null){
                if(check.getClass().getName().equals("pieceTypes.Queen") || check.getClass().getName().equals("pieceTypes.Bishop")){
                    String x = board[r][c].getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            r+=1;
            c-=1;
        }

        check = null;
        r = row-1;
        c = column+1;

        while(check == null && r >= 0 && c <= 7){
            check = board[r][c];
            if(check != null){
                if(check.getClass().getName().equals("pieceTypes.Queen") || check.getClass().getName().equals("pieceTypes.Bishop")){
                    String x = board[r][c].getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            r-=1;
            c+=1;
        }

        check = null;
        r = row+1;
        c = column;

        while(check == null && r <=7){
            check = board[r][c];
            if(check != null){
                if(check.getClass().getName().equals("pieceTypes.Queen") || check.getClass().getName().equals("pieceTypes.Rook")){
                    String x = board[r][c].getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            r+=1;
        }

        check = null;
        r = row-1;
        c = column;

        while(check == null && r >= 0){
            check = board[r][c];
            if(check != null){
                if(check.getClass().getName().equals("pieceTypes.Queen") || check.getClass().getName().equals("pieceTypes.Rook")){
                    String x = board[r][c].getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            r-=1;
        }

        check = null;
        r = row;
        c = column+1;

        while(check == null && c <=7){
            check = board[r][c];
            if(check != null){
                if(check.getClass().getName().equals("pieceTypes.Queen") || check.getClass().getName().equals("pieceTypes.Rook")){
                    String x = board[r][c].getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            c+=1;
        }

        check = null;
        r = row;
        c = column-1;

        while(check == null && c >= 0){
            check = board[r][c];
            if(check != null){
                if(check.getClass().getName().equals("pieceTypes.Queen") || check.getClass().getName().equals("pieceTypes.Rook")){
                    String x = board[r][c].getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            c-=1;
        }

        if(row+2 <= 7){
            if(column-1 >= 0){
                check = board[row+2][column-1];
                if(check != null && check.getClass().getName().equals("pieceTypes.Knight") ){
                    String x = check.getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            if(column+1 <= 7){
                check = board[row+2][column+1];
                if(check != null && check.getClass().getName().equals("pieceTypes.Knight") ){
                    String x = check.getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
        }

        if(row-2 >= 0){
            if(column-1 >= 0){
                check = board[row-2][column-1];
                if(check != null && check.getClass().getName().equals("pieceTypes.Knight") ){
                    String x = check.getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            if(column+1 <= 7){
                check = board[row-2][column+1];
                if(check != null && check.getClass().getName().equals("pieceTypes.Knight") ){
                    String x = check.getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
        }

        if(column+2 <= 7){
            if(row-1 >= 0){
                check = board[row-1][column+2];
                if(check != null && check.getClass().getName().equals("pieceTypes.Knight") ){
                    String x = check.getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            if(row+1 <= 7){
                check = board[row+1][column+2];
                if(check != null && check.getClass().getName().equals("pieceTypes.Knight") ){
                    String x = check.getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
        }

        if(column-2 >= 0){
            if(row-1 >= 0){
                check = board[row-1][column-2];
                if(check != null && check.getClass().getName().equals("pieceTypes.Knight") ){
                    String x = check.getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
            if(row+1 <= 7){
                check = board[row+1][column-2];
                if(check != null && check.getClass().getName().equals("pieceTypes.Knight") ){
                    String x = check.getPlayer().getColor();
                    if(!x.equals(color)){
                        return true;
                    }
                }
            }
        }

        return false;

    }

    /**
     * Returns the boolean for castle
     * @return - The boolean for castle
     */
    public boolean getCastle(){
        return castle;
    }

    /**
     * Sets castle to false
     */
    public void noCastle(){
        castle = false;
    }

}
