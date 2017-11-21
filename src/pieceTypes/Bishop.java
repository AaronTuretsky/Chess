package pieceTypes;

import java.util.ArrayList;

/**
 *  Class representing the chess piece of bishop
 *
 * @author Aaron Turetsky
 */
public class Bishop extends Piece {

    /**
     * Constructor for Bishop
     * @param player - The player of which this piece belongs to
     */
    public Bishop(Player player){
        super(player);
    }

    /**
     * Constructor for Bishop
     * Typically called when a deep copy is made
     * @param peice - The piece who's fields are being copied to the bishop being created
     * @param player - The player who owns the bishop being created
     */
    public Bishop(Piece peice, Player player){
        super(peice, player);
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

        int r = row-1;
        int c = column-1;
        while(r >= 0 && c >= 0){
            if(board[r][c] == null){
                moves.add(locationToString(r,c));
            }
            else if(!board[r][c].getPlayer().getColor().equals(this.getPlayer().getColor())){
                moves.add(locationToString(r,c));
                break;
            }
            else{
                break;
            }
            r--;
            c--;
        }

        r = row-1;
        c = column+1;
        while(r >= 0 && c <= 7){
            if(board[r][c] == null){
                moves.add(locationToString(r,c));
            }
            else if(!board[r][c].getPlayer().getColor().equals(this.getPlayer().getColor())){
                moves.add(locationToString(r,c));
                break;
            }
            else{
                break;
            }
            r--;
            c++;
        }

        r = row+1;
        c = column+1;
        while(r <= 7 && c <= 7){
            if(board[r][c] == null){
                moves.add(locationToString(r,c));
            }
            else if(!board[r][c].getPlayer().getColor().equals(this.getPlayer().getColor())){
                moves.add(locationToString(r,c));
                break;
            }
            else{
                break;
            }
            r++;
            c++;
        }
        r = row+1;
        c = column-1;
        while(r <= 7 && c >= 0){
            if(board[r][c] == null){
                moves.add(locationToString(r,c));
            }
            else if(!board[r][c].getPlayer().getColor().equals(this.getPlayer().getColor())){
                moves.add(locationToString(r,c));
                break;
            }
            else{
                break;
            }
            r++;
            c--;
        }

        return moves;
    }

    /**
     * Returns a string representation of the piece
     * @return - A string representation of the piece
     */
    public String toString(){
        return this.getPlayer().getColor().charAt(0)+"B";
    }
}
