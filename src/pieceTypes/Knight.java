package pieceTypes;

import java.util.ArrayList;

/**
 * Class representing the chess piece of Knight
 *
 * @author Aaron Turetsky
 */
public class Knight extends Piece {
    /**
     * Constructor for knight
     * @param player - The player who the piece belongs to
     */
    public Knight(Player player){
        super(player);
    }

    /**
     * Constructor for knight
     * Typically called when a deep copy is made
     * @param peice - The piece who's fields are being copied to the bishop being created
     * @param player - The player that owns the king being made
     */
    public Knight(Piece peice, Player player){
        super(peice, player);
    }

    /**
     * Returns a list of natural moves the knight can make
     * The list contains moves that may be invalid due to the location of other pieces
     * @param board - The 2d array containing the current game
     * @return - A list of potential moves
     */
    public ArrayList<String> potentialMoves(Piece[][] board) {
        ArrayList<String> moves = new ArrayList<>();

        String[] coords = getlocation().split(" ");
        int row = Integer.parseInt(coords[1]);
        int column = Integer.parseInt(coords[4]);

        if (row - 2 >= 0 && column - 1 >= 0 && (board[row - 2][column - 1] == null || board[row - 2][column - 1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row - 2, column - 1));
        }
        if (row - 2 >= 0 && column + 1 <= 7 && (board[row - 2][column + 1] == null || board[row - 2][column + 1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row - 2, column + 1));
        }

        if (row + 2 <= 7 && column - 1 >= 0 && (board[row + 2][column - 1] == null || board[row + 2][column - 1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row + 2, column - 1));
        }
        if (row + 2 <= 7 && column + 1 <= 7 && (board[row + 2][column + 1] == null || board[row + 2][column + 1].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row + 2, column + 1));
        }

        if (row - 1 >= 0 && column + 2 <= 7 && (board[row - 1][column + 2] == null || board[row - 1][column + 2].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row - 1, column + 2));
        }
        if (row + 1 <= 7 && column + 2 <= 7 && (board[row + 1][column + 2] == null || board[row + 1][column + 2].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row + 1, column + 2));
        }
        if (row - 1 >= 0 && column - 2 >= 0 && (board[row - 1][column - 2] == null || board[row - 1][column - 2].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row - 1, column - 2));
        }
        if (row + 1 <= 7 && column - 2 >= 0 && (board[row + 1][column - 2] == null || board[row + 1][column - 2].getPlayer() != this.getPlayer())) {
            moves.add(locationToString(row + 1, column - 2));
        }

        return moves;
    }

    /**
     * Returns a string representation of knight
     * @return - Returns a string representation of knight
     */
    public String toString(){
        return this.getPlayer().getColor().charAt(0)+"K";
    }
}
