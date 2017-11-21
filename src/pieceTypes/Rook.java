package pieceTypes;

import java.util.ArrayList;

/**
 * Class representing the chess piece of Rook
 *
 *@author Aaron Turetsky
 */
public class Rook extends Piece {

    /**
     * A boolean representing whether the piece is valid to be castled with
     */
    private boolean castle;

    /**
     * Constructor for rook
     * @param player
     */
    public Rook(Player player){
        super(player);
        castle = true;
    }

    /**
     * Constructor for rook
     * Typically called when a deep copy is being made of a rook
     * @param peice
     * @param player
     */
    public Rook(Piece peice, Player player){
        super(peice, player);
        Rook orig = (Rook) peice;
        castle = orig.castle;
    }

    /**
     * Returns a list of the rooks natural potential moves
     * @param board - The 2d array that contains the rook
     * @return - A list of the rooks natural potential moves
     */
    public ArrayList<String> potentialMoves(Piece[][] board){
        ArrayList<String> moves = new ArrayList<>();

        String[] coords = getlocation().split(" ");
        int row = Integer.parseInt(coords[1]);
        int column = Integer.parseInt(coords[4]);

        for(int r = row+1; r <= 7; r++){
            if(board[r][column] == null){
                moves.add(locationToString(r,column));
            }
            else if(!board[r][column].getPlayer().getColor().equals(this.getPlayer().getColor())){
                moves.add(locationToString(r,column));
                break;
            }
            else{
                break;
            }
        }
        for(int r = row-1; r >= 0; r--){
            if(board[r][column] == null){
                moves.add(locationToString(r,column));
            }
            else if(!board[r][column].getPlayer().getColor().equals(this.getPlayer().getColor())){
                moves.add(locationToString(r,column));
                break;
            }
            else{
                break;
            }
        }
        for(int c = column+1; c <= 7; c++){
            if(board[row][c] == null){
                moves.add(locationToString(row,c));
            }
            else if(!board[row][c].getPlayer().getColor().equals(this.getPlayer().getColor())){
                moves.add(locationToString(row,c));
                break;
            }
            else{
                break;
            }
        }
        for(int c = column-1; c >= 0; c--){
            if(board[row][c] == null){
                moves.add(locationToString(row,c));
            }
            else if(!board[row][c].getPlayer().getColor().equals(this.getPlayer().getColor())){
                moves.add(locationToString(row,c));
                break;
            }
            else{
                break;
            }
        }

        return moves;
    }

    /**
     * Returns a string representation of a rook
     * @return - A string representation of a rook
     */
    public String toString(){
        return this.getPlayer().getColor().charAt(0)+"R";
    }

    /**
     * Returns the boolean status castle
     * @return
     */
    public boolean getCastle(){
        return castle;
    }

    /**
     * Invalidates the rooks ability to castle
     */
    public void noCastle(){
        castle = false;
    }
}
