package pieceTypes;

import java.util.ArrayList;

/**
 * Class representing a player
 *
 *@author Aaron Turetsky
 */
public class Player {

    /**
     * String representing the color of the piece
     */
    private String color;
    /**
     * ArrayList containing the players rooks
     */
    private ArrayList<Piece> rooks;
    /**
     * ArrayList containing the players knights
     */
    private ArrayList<Piece> knights;
    /**
     * ArrayList containing the players bishops
     */
    private ArrayList<Piece> bishops;
    /**
     * ArrayList containing the players queens
     */
    private ArrayList<Piece> queen;
    /**
     * ArrayList containing the players king
     */
    private Piece king;
    /**
     * ArrayList containing the players pawns
     */
    private ArrayList<Piece> pawns;

    /**
     * Constructor for player
     * @param color - The color of the players pieces (White or Black)
     * @param board - The 2d Array the pieces will be added to representing the board
     */
    public Player(String color, Piece[][] board){
        this.color = color;

        rooks = new ArrayList<>();
        knights = new ArrayList<>();
        bishops = new ArrayList<>();
        queen = new ArrayList<>();
        pawns = new ArrayList<>();

        if(color.equals("white")){
            Rook RBL = new Rook(this);
            Rook RBR = new Rook(this);
            rooks.add(RBL);
            rooks.add(RBR);

            board[7][0] = RBL;
            board[7][7] = RBR;
            RBL.setLocation(7,0);
            RBR.setLocation(7,7);

            Knight KBL = new Knight(this);
            Knight KBR = new Knight(this);
            knights.add(KBL);
            knights.add(KBR);

            board[7][1] = KBL;
            board[7][6] = KBR;
            KBL.setLocation(7,1);
            KBR.setLocation(7,6);

            Bishop BBL = new Bishop(this);
            Bishop BBR = new Bishop(this);
            bishops.add(BBL);
            bishops.add(BBR);

            board[7][2] = BBL;
            board[7][5] = BBR;
            BBL.setLocation(7,2);
            BBR.setLocation(7,5);

            this.king = new King(this);
            board[7][4] = king;
            king.setLocation(7,4);

            Queen whiteQueen = new Queen(this);
            queen.add(whiteQueen);

            board[7][3] = whiteQueen;
            whiteQueen.setLocation(7,3);

            for(int c = 0; c <= 7; c++){
                Pawn pawn = new Pawn(this);
                board[6][c] = pawn;
                pawn.setLocation(6,c);
                pawns.add(pawn);
            }
        }
        if(color.equals("black")){
            Rook RTL = new Rook(this);
            Rook RTR = new Rook(this);
            rooks.add(RTL);
            rooks.add(RTR);

            board[0][0] = RTL;
            board[0][7] = RTR;
            RTL.setLocation(0,0);
            RTR.setLocation(0,7);

            Knight KTL = new Knight(this);
            Knight KTR = new Knight(this);
            knights.add(KTL);
            knights.add(KTR);

            board[0][1] = KTL;
            board[0][6] = KTR;
            KTL.setLocation(0,1);
            KTR.setLocation(0,6);

            Bishop BTL = new Bishop(this);
            Bishop BTR = new Bishop(this);
            bishops.add(BTL);
            bishops.add(BTR);

            board[0][2] = BTL;
            board[0][5] = BTR;
            BTL.setLocation(0,2);
            BTR.setLocation(0,5);

            this.king = new King(this);

            board[0][4] = king;
            king.setLocation(0,4);

            Queen blackQueen = new Queen(this);
            queen.add(blackQueen);

            board[0][3] = blackQueen;
            blackQueen.setLocation(0,3);

            for(int c = 0; c <= 7; c++){
                Pawn pawn = new Pawn(this);
                pawns.add(pawn);

                board[1][c] = pawn;
                pawn.setLocation(1,c);
            }
        }
    }

    /**
     * Removes a piece from the players possession
     * Typically called if a piece is taken by the opponent
     * @param overtaken - The piece being removed
     */
    public void deletePiece(Piece overtaken){
        if(rooks.contains(overtaken)){
            rooks.remove(overtaken);

        }
        else if(knights.contains(overtaken)){
            knights.remove(overtaken);

        }
        else if(bishops.contains(overtaken)){
            bishops.remove(overtaken);

        }
        else if(queen.contains(overtaken)){
            queen.remove(overtaken);

        }
        /**
        else if(king == overtaken){
            king == null;

        }
         */
        else if(pawns.contains(overtaken)){
            pawns.remove(overtaken);

        }
    }

    /**
     * Constructor for player
     * Typically called when a deep copy is being made
     * @param player - The player who's fields are being copied to the player being created
     */
    public Player(Player player){
        this.color = player.color;

        rooks = new ArrayList<>();
        knights = new ArrayList<>();
        bishops = new ArrayList<>();
        queen = new ArrayList<>();
        pawns = new ArrayList<>();

        King Kings = (King) player.getKings();
        King king = new King(Kings, this);
        this.king = king;

        ArrayList<Piece> Queens = player.getQueens();
        for(int i = 0; i< Queens.size(); i++){
            Queen queen = new Queen(Queens.get(i), this);
            this.queen.add(queen);
        }

        ArrayList<Piece> Rooks = player.getRooks();
        for(int i = 0; i< Rooks.size(); i++){
            Rook rook = new Rook(Rooks.get(i), this);
            rooks.add(rook);
        }

        ArrayList<Piece> Knights = player.getKnights();
        for(int i = 0; i< Knights.size(); i++){
            Knight knight = new Knight(Knights.get(i), this);
            knights.add(knight);
        }

        ArrayList<Piece> Bishops = player.getBishops();
        for(int i = 0; i< Bishops.size(); i++){
            Bishop bishop = new Bishop(Bishops.get(i), this);
            bishops.add(bishop);
        }

        ArrayList<Piece> Pawns = player.getPawns();
        for(int i = 0; i< Pawns.size(); i++){
            Pawn pawn = new Pawn(Pawns.get(i), this);
            pawns.add(pawn);
        }

    }

    /**
     * Returns the players color
     * @return - The color of the player
     */
    public String getColor(){
        return color;
    }

    /**
     * Adds a piece to players owned pieces
     * @param peice - The piece being added
     * @param promoted - A string representing the type of piece
     */
    public void addPeice(Piece peice, String promoted){
        if(promoted.equalsIgnoreCase("queen")){
            queen.add(peice);
        }else if(promoted.equalsIgnoreCase("rook")){
            rooks.add(peice);
        }else if(promoted.equalsIgnoreCase("knight")){
            knights.add(peice);
        }else if(promoted.equalsIgnoreCase("bishop")) {
            bishops.add(peice);
        }

    }

    /**
     * Returns the players king
     * @return - the players king
     */
    public Piece getKings() {
        return king;
    }

    /**
     * Returns the players list containing queens
     * @return - the list containing the players queens
     */
    public ArrayList<Piece> getQueens() {
        return queen;
    }

    /**
     * Returns the players list containing Bishops
     * @return - the list containing the players bishops
     */
    public ArrayList<Piece> getBishops() {
        return bishops;
    }

    /**
     * Returns the players list containing knights
     * @return - the list containing the players knights
     */
    public ArrayList<Piece> getKnights() {
        return knights;
    }

    /**
     * Returns the players list containing rooks
     * @return - the list containing the players rooks
     */
    public ArrayList<Piece> getRooks() {
        return rooks;
    }

    /**
     * Returns the players list containing pawns
     * @return - the list containing the players pawns
     */
    public ArrayList<Piece> getPawns() {
        return pawns;
    }
}
