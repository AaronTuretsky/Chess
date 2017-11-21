package pieceTypes;

/**
 * Abstract class defining common methods each piece will use 
 * 
 * @author Aaron Turetsky
 */
public abstract class Piece {

    /**
     * Row the piece is in
     */
    private int row;

    /**
     * Column the piece is in
     */
    private int column;

    /**
     * Player the piece belongs to
     */
    private Player player;

    /**
     * Constructor for piece
     * @param player - The player the piece belongs to
     */
    public Piece(Player player){
        this.player = player;
    }

    /**
     * Constructor for piece
     * Typically called when a deep copy a player is made
     * @param peice - The piece who's fields are being copied
     * @param player - The player the piece being created belongs to
     */
    public Piece(Piece peice, Player player){
        this.row = peice.row;
        this.column = peice.column;
        this.player = peice.player;
        this.player = player;
    }

    /**
     * Returns a string representation of the location the piece is on the board
     * @return - Returns a string representation of the location the piece is on the board
     */
    public String getlocation(){
        return "ROW: " + row + " , " + "COLUMN: " + column;
    }

    /**
     * Returns a string representation of the location the piece is on the board
     * @param row - The row the piece is in
     * @param column - The column the piece is in
     * @return - Returns a string representation of the location the piece is on the board
     */
    public String locationToString(int row, int column){
        return "ROW: " + row + " , " + "COLUMN: " + column;
    }

    /**
     * Sets the location of the piece
     * @param row - The integer to set to the row of the piece
     * @param column - The integer to set to the column of the piece
     */
    public void setLocation(int row, int column){
        this.row = row;
        this.column = column;
    }

    /**
     * Returns the player that owns the piece
     * @return - Returns the player that owns the piece
     */
    public Player getPlayer(){ return player;}

}
