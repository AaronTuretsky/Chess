package pieceTypes;

import java.util.ArrayList;

/**
 * Class representing the chess piece of Pawn
 *
 * @author Aaron Turetsky
 */
public class Pawn extends Piece {

    /**
     * Boolean reflecting whether a pawn can perform enPassant
     */
    private boolean enPassant;

    /**
     * Constructor for pawn
     * @param player - Player that owns the pawn
     */
    public Pawn(Player player){
        super(player);
        enPassant = false;
    }

    /**
     * Constructor for pawn
     * Typically called when board is deep copied
     * @param piece - The piece who's fields are copied to the pawn being created
     * @param player - The player who owns the pawn being created
     */
    public Pawn(Piece piece, Player player){
        super(piece, player);
        Pawn orig = (Pawn) piece;
        enPassant = orig.enPassant;
    }

    /**
     * Returns a list of all the natural moves the pawn is capable of making
     * The list contains moves that may be invalid due to the location of other pieces
     * @param board - The 2d array containing the current game
     * @return - A list of potential moves
     */
    public ArrayList<String> potentialMoves(Piece[][] board) {
        ArrayList<String> moves = new ArrayList<>();

        String color = getPlayer().getColor();

        String[] coords = getlocation().split(" ");
        int row = Integer.parseInt(coords[1]);
        int column = Integer.parseInt(coords[4]);


        if (color.equals("white") && board[row - 2][column] == null) {
            if (row == 6) {
                moves.add(locationToString(row - 2, column));
            }
        }
        if (color.equals("white") && board[row - 1][column] == null) {
            moves.add(locationToString(row - 1, column));
        }
        if (color.equals("white") && row - 1 >= 0 && column - 1 >= 0 && board[row - 1][column - 1] != null && board[row - 1][column - 1].getPlayer().getColor().equals("black")) {
            moves.add(locationToString(row - 1, column - 1));
        }
        if (color.equals("white") && row - 1 >= 0 && column + 1 <= 7 && board[row - 1][column + 1] != null && board[row - 1][column + 1].getPlayer().getColor().equals("black")) {
            moves.add(locationToString(row - 1, column + 1));
        }
        if (color.equals("white") && column + 1 <= 7 && board[row][column + 1] != null && board[row][column + 1].getPlayer().getColor().equals("black")) {
            if(board[row][column +1].getClass().getClass().getName().equals("pieceTypes.Pawn")){
                Piece p = board[row][column+1];
                Pawn pawn = (Pawn) p;
                if(pawn.getPassant()) {
                    moves.add(locationToString(row, column + 1));
                }
            }

        }
        if (color.equals("white") && column - 1 >= 0 && board[row][column - 1] != null && board[row][column - 1].getPlayer().getColor().equals("black")) {
            if(board[row][column -1].getClass().getClass().getName().equals("pieceTypes.Pawn")){
                Piece p = board[row][column-1];
                Pawn pawn = (Pawn) p;
                if(pawn.getPassant()) {
                    moves.add(locationToString(row, column - 1));
                }
            }

        }

        if (color.equals("black") && board[row + 2][column] == null) {;
            if (row == 1) {
                moves.add(locationToString(row + 2, column));
            }
        }
        if (color.equals("black") && board[row + 1][column] == null) {;
            moves.add(locationToString(row + 1, column));
        }
        if (color.equals("black") && row + 1 <= 7 && column - 1 >= 0 && board[row + 1][column - 1] != null && board[row + 1][column - 1].getPlayer().getColor().equals("white")) {
            moves.add(locationToString(row + 1, column - 1));
        }
        if (color.equals("black") && row + 1 <= 7 && column + 1 <= 7 && board[row + 1][column + 1] != null && board[row + 1][column + 1].getPlayer().getColor().equals("white")) {
            moves.add(locationToString(row + 1, column + 1));
        }
        if (color.equals("black") && column + 1 <= 7 && board[row][column + 1] != null && board[row][column + 1].getPlayer().getColor().equals("white")) {
            if(board[row][column +1].getClass().getClass().getName().equals("pieceTypes.Pawn")){
                Piece p = board[row][column+1];
                Pawn pawn = (Pawn) p;
                if(pawn.getPassant()) {
                    moves.add(locationToString(row, column + 1));
                }
            }

        }
        if (color.equals("black") && column - 1 >= 0 && board[row][column - 1] != null && board[row][column - 1].getPlayer().getColor().equals("white")) {
            if(board[row][column -1].getClass().getClass().getName().equals("pieceTypes.Pawn")){
                Piece p = board[row][column-1];
                Pawn pawn = (Pawn) p;
                if(pawn.getPassant()) {
                    moves.add(locationToString(row, column - 1));
                }
            }

        }

        return moves;
    }

    public void passantValid(){
        enPassant = true;
    }

    public void passantInvalid(){
        enPassant = false;
    }

    public boolean getPassant(){
        return enPassant;
    }

    public String toString(){
        return this.getPlayer().getColor().charAt(0)+"P";
    }
}
