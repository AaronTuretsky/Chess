package pieceTypes;
import java.util.ArrayList;

/**
 * Class representing the chess piece of Queen
 *
 *@author Aaron Turetsky
 */
public class Queen extends Piece {
    /**
     * Constructor for Queen
     * @param player - The player of whom the piece belongs to
     */
    public Queen(Player player){
        super(player);
    }

    /**
     * Constructor for Queen, typically called when a deep copy is to made.
     * @param piece - The piece to by copied
     * @param player - player of whom the piece belongs to
     */
    public Queen(Piece piece, Player player){
        super(piece, player);
    }

    /**
     * Returns a list of moves that the selected piece is naturally capable off.
     * Includes moves that may be blocked by other pieces
     * @param board - The 2d array containing the current game
     * @return - A list of moves that the selected piece is naturally capable off
     */
    public ArrayList<String> potentialMoves(Piece[][] board) {
        ArrayList<String> moves = new ArrayList<>();

        String[] coords = getlocation().split(" ");
        int row = Integer.parseInt(coords[1]);
        int column = Integer.parseInt(coords[4]);

        int r = row - 1;
        int c = column - 1;
        while (r >= 0 && c >= 0) {
            if (board[r][c] == null) {
                moves.add(locationToString(r, c));
            } else if (!board[r][c].getPlayer().getColor().equals(this.getPlayer().getColor())) {
                moves.add(locationToString(r, c));
                break;
            } else {
                break;
            }
            r--;
            c--;
        }

        r = row - 1;
        c = column + 1;
        while (r >= 0 && c <= 7) {
            if (board[r][c] == null) {
                moves.add(locationToString(r, c));
            } else if (!board[r][c].getPlayer().getColor().equals(this.getPlayer().getColor())) {
                moves.add(locationToString(r, c));
                break;
            } else {
                break;
            }
            r--;
            c++;
        }

        r = row + 1;
        c = column + 1;
        while (r <= 7 && c <= 7) {
            if (board[r][c] == null) {
                moves.add(locationToString(r, c));
            } else if (!board[r][c].getPlayer().getColor().equals(this.getPlayer().getColor())) {
                moves.add(locationToString(r, c));
                break;
            } else {
                break;
            }
            r++;
            c++;
        }
        r = row + 1;
        c = column - 1;
        while (r <= 7 && c >= 0) {
            if (board[r][c] == null) {
                moves.add(locationToString(r, c));
            } else if (!board[r][c].getPlayer().getColor().equals(this.getPlayer().getColor())) {
                moves.add(locationToString(r, c));
                break;
            } else {
                break;
            }
            r++;
            c--;
        }

        for (r = row+1; r <= 7; r++) {
            if (board[r][column] == null) {
                moves.add(locationToString(r, column));
            } else if (!board[r][column].getPlayer().getColor().equals(this.getPlayer().getColor())) {
                moves.add(locationToString(r, column));
                break;
            } else {
                break;
            }
        }
        for (r = row-1; r >= 0; r--) {
            if (board[r][column] == null) {
                moves.add(locationToString(r, column));
            } else if (!board[r][column].getPlayer().getColor().equals(this.getPlayer().getColor())) {
                moves.add(locationToString(r, column));
                break;
            } else {
                break;
            }
        }
        for (c = column+1; c <= 7; c++) {
            if (board[row][c] == null) {
                moves.add(locationToString(row, c));
            } else if (!board[row][c].getPlayer().getColor().equals(this.getPlayer().getColor())) {
                moves.add(locationToString(row, c));
                break;
            } else {
                break;
            }
        }
        for (c = column-1; c >= 0; c--) {
            if (board[row][c] == null) {
                moves.add(locationToString(row, c));
            } else if (!board[row][c].getPlayer().getColor().equals(this.getPlayer().getColor())) {
                moves.add(locationToString(row, c));
                break;
            } else {
                break;
            }
        }

        return moves;
    }

    /**
     * Returns the string representation of this piece
     * @return The string representation of the piece
     */
    public String toString(){
        return this.getPlayer().getColor().charAt(0)+"Q";
    }
}
