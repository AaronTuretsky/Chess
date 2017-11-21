import pieceTypes.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class representing the board
 *
 * @ Aaron Turetsky
 */
public class Board {

    /**
     * A 2d array containing the current configuration of the board
     */
    private static Piece[][] board;
    /**
     * The player set to be white
     */
    private static Player white;
    /**
     * The player set to be black
     */
    private static Player black;
    /**
     * A temporary copy of the white player that has been slightly modified
     */
    private static Player whiteholder;
    /**
     * A temporary copy of the black player that has been slightly modified
     */
    private static Player blackholder;
    /**
     * A temporary copy of the board that has been slightly modified
     */
    private static Piece[][] boardholder;

    /**
     * Creates the starting board along with the default white and black players
     */
    public Board(){
        board = new Piece[8][8];

        white = new Player("white", board);
        black = new Player("black", board);
    }

    /**
     * Returns the white player
     * @return - Returns the white player
     */
    public Player getWhite(){
        return white;
    }

    /**
     * Returns the black player
     * @return Returns the black player
     */
    public Player getBlack(){
        return black;
    }

    /**
     * Returns a string representation of the board
     * @return a string representation of the board
     */
    public String boardToString(){
        int row = 0;
        int column = 0;
        String B = "   0  1  2  3  4  5  6  7\n0  ";
        while(row <= 7){

            if(board[row][column] == null){
                B+= "-  ";
            }else{
                B+= board[row][column].toString() +" ";
            }
            column++;
            if(column > 7){
                column = 0;
                row+=1;
                if(row <= 7) {
                    B += "\n" + row + "  ";
                }
            }

        }

        return B;
    }

    /**
     * Replaces the players, white and black and the board with slightly modified copies
     */
    public static void updateSet(){
        white = whiteholder;
        black = blackholder;
        board = boardholder;

        whiteholder = null;
        blackholder = null;
        boardholder = null;
    }

    /**
     * Returns a string representation of a spot on the board
     * @param row - A row of the board
     * @param column - A column of the board
     * @return - A string representation of a spot on the board
     */
    public String moveToString(Integer row, Integer column){
        return "ROW: " + row + " , " + "COLUMN: " + column;
    }

    /**
     * Moves a piece on the board to another spot on the board
     * @param rowF The current row of the piece being moved
     * @param columnF The current column of the piece being moved
     * @param rowT The requested row the piece be moved to
     * @param columnT The requested column the piece be moved to
     * @return - Returns the board after the piece is moved
     */
    public static Piece[][] Move(Integer rowF, Integer columnF, Integer rowT, Integer columnT){
        Piece peice = boardholder[rowF][columnF];
        boardholder[rowF][columnF] = null;
        Piece overtaken = boardholder[rowT][columnT];

        if(overtaken != null){
            overtaken.getPlayer().deletePiece(overtaken);
        }
        boardholder[rowT][columnT] = peice;
        peice.setLocation(rowT,columnT);

        if(peice.getClass().getName().equals("pieceTypes.Rook")){
            Rook rook = (Rook) peice;
            rook.noCastle();
        }else if(peice.getClass().getName().equals("pieceTypes.King")){
            King king = (King) peice;
            king.noCastle();
        }else if(peice.getClass().getName().equals("pieceTypes.Pawn")){
            Pawn pawn = (Pawn) peice;

            if(rowT == 0 || rowT == 7) {
                System.out.println("Pawn promotion is available please choose a piece to exchange your pawn with");
                Scanner reader = new Scanner(System.in);
                String code = reader.nextLine();

                while (!pawnPromotion(pawn, code)) {
                    System.out.println("Invalid piece is selected please enter another:");
                    code = reader.nextLine();
                }
            }else if((rowF == 6 && rowT == 4) || (rowF == 1 && rowT == 3)){
                pawn.passantValid();

            }
        }

        ArrayList<Piece> p;
        if(peice.getPlayer().getColor().equals("white")){
            p =  blackholder.getPawns();
        }else{
            p = whiteholder.getPawns();
        }


        for(int i = 0; i < p.size(); i++){
            Pawn pawn = (Pawn) p.get(i);
            pawn.passantInvalid();
        }

        return boardholder;
    }

    /**
     * Called when a pawn makes it to the opposite side of the board from their starting position
     * Replaces the pawn with the desired piece defined by promoted
     * @param pawn The pawn being replaced
     * @param promoted The requested piece type to replace the pawn
     * @return - Returns true if promoted is a valid piece type, otherwise returns false
     */
    private static boolean pawnPromotion(Pawn pawn,String promoted){
        Player player = pawn.getPlayer();

        String[] location = pawn.getlocation().split(" ");
        int row = Integer.parseInt(location[1]);
        int column = Integer.parseInt(location[4]);


        Piece replacement;
        if(promoted.equalsIgnoreCase("queen")){
            replacement = new Queen(player);
        }else if(promoted.equalsIgnoreCase("rook")){
            replacement = new Rook(player);
            Rook r = (Rook) replacement;
            r.noCastle();
            replacement = r;
        }else if(promoted.equalsIgnoreCase("knight")){
            replacement = new Knight(player);
        }else if(promoted.equalsIgnoreCase("bishop")){
            replacement = new Bishop(player);
        }else{
            return false;
        }
        boardholder[row][column] = replacement;
        replacement.setLocation(row,column);
        player.deletePiece(pawn);
        player.addPeice(replacement, promoted);

        return true;
    }

    /**
     * Creates copies of white and black players and the board then stores them in whiteholder, blackholder, and boardholder
     */
    private void deepCopy(){
        boardholder = new Piece[8][8];

        whiteholder = new Player(white);
        blackholder = new Player(black);

        int row;
        int column;
        ArrayList<Piece> rooks = whiteholder.getRooks();
        for(int i =0; i< rooks.size(); i++){
            Piece rook = rooks.get(i);
            String[] loc = rook.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = rook;
        }

        ArrayList<Piece> bishops = whiteholder.getBishops();
        for(int i =0; i< bishops.size(); i++){
            Piece bishop = bishops.get(i);
            String[] loc = bishop.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = bishop;
        }

        ArrayList<Piece> knights = whiteholder.getKnights();
        for(int i =0; i< knights.size(); i++){
            Piece knight = knights.get(i);
            String[] loc = knight.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = knight;
        }

        ArrayList<Piece> queens = whiteholder.getQueens();
        for(int i =0; i< queens.size(); i++){
            Piece queen = queens.get(i);
            String[] loc = queen.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = queen;
        }

        ArrayList<Piece> pawns = whiteholder.getPawns();
        for(int i =0; i< pawns.size(); i++){
            Piece pawn = pawns.get(i);
            String[] loc = pawn.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = pawn;
        }

        Piece king = whiteholder.getKings();
        String[] loc = king.getlocation().split(" ");
        row = Integer.parseInt(loc[1]);
        column = Integer.parseInt(loc[4]);
        boardholder[row][column] = king;

        rooks = blackholder.getRooks();
        for(int i =0; i< rooks.size(); i++){
            Piece rook = rooks.get(i);
            loc = rook.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = rook;
        }

        bishops = blackholder.getBishops();
        for(int i =0; i< bishops.size(); i++){
            Piece bishop = bishops.get(i);
            loc = bishop.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = bishop;
        }

        knights = blackholder.getKnights();
        for(int i =0; i< knights.size(); i++){
            Piece knight = knights.get(i);
            loc = knight.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = knight;
        }

        queens = blackholder.getQueens();
        for(int i =0; i< queens.size(); i++){
            Piece queen = queens.get(i);
            loc = queen.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = queen;
        }

        pawns = blackholder.getPawns();
        for(int i =0; i< pawns.size(); i++){
            Piece pawn = pawns.get(i);
            loc = pawn.getlocation().split(" ");
            row = Integer.parseInt(loc[1]);
            column = Integer.parseInt(loc[4]);
            boardholder[row][column] = pawn;

        }

        king = blackholder.getKings();
        loc = king.getlocation().split(" ");
        row = Integer.parseInt(loc[1]);
        column = Integer.parseInt(loc[4]);
        boardholder[row][column] = king;
    }

    /**
     * Checks if a given move is valid
     * @param coordsF - A set containing the coordinates of the starting position of the piece being moved
     * @param coordsT - A set containing the coordinates of the ending position of the piece being moved
     * @param player - The player who is making the move
     * @return Returns true if the move is valid, otherwise returns false
     */
    public boolean isValid(String[] coordsF, String[] coordsT, Player player){
        deepCopy();

        if(coordsT == null){

            if (player.getColor().equals("white")) {
                King King = (King) whiteholder.getKings();
                if(!(King).isChecked(boardholder)) {
                    if (coordsF[0].equals("castle")) {
                        if (coordsF[1].equals("kingside")) {
                            if (board[7][7] != null && board[7][7].getClass().getName().equals("pieceTypes.Rook") && board[7][7].getPlayer() == player && King.getCastle()) {
                                Piece R = boardholder[7][7];
                                Rook Rook = (Rook) R;
                                if (Rook.getCastle()) {
                                    if (board[7][6] == null && board[7][5] == null ) {
                                        Castle(King, Rook,"king");
                                        if(!King.isChecked(boardholder)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else if (coordsF[1].equals("queenside")) {
                            if (board[7][0] != null && board[7][0].getClass().getName().equals("pieceTypes.Rook") && board[7][0].getPlayer() == player && King.getCastle()) {
                                Piece R = boardholder[7][0];
                                Rook Rook = (Rook) R;
                                if (Rook.getCastle()) {
                                    if (board[7][1] == null && board[7][2] == null && board[7][3] == null) {
                                        Castle(King, Rook,"queen");
                                        if(!King.isChecked(boardholder)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (player.getColor().equals("black")) {

                King King = (King) blackholder.getKings();
                if(!(King).isChecked(boardholder)) {
                    if (coordsF[0].equals("castle")) {
                        if (coordsF[1].equals("kingside")) {
                            if (board[0][7] != null && board[0][7].getClass().getName().equals("pieceTypes.Rook") && board[0][7].getPlayer() == player && King.getCastle()) {
                                Piece R = boardholder[0][7];
                                Rook Rook = (Rook) R;
                                if (Rook.getCastle()) {
                                    if (board[0][5] == null && board[7][6] == null) {
                                        Castle(King, Rook,"king");
                                        if(!King.isChecked(boardholder)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else if (coordsF[1].equals("queenside")) {
                            if (board[7][0] != null && board[7][0].getClass().getName().equals("pieceTypes.Rook") && board[7][0].getPlayer() == player && King.getCastle()) {
                                Piece R = boardholder[7][0];
                                Rook Rook = (Rook) R;
                                if (Rook.getCastle()) {
                                    if (board[7][1] == null && board[7][2] == null && board[0][3] == null) {
                                        Castle(King, Rook,"queen");
                                        if(!King.isChecked(boardholder)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }



        Integer rowF = Integer.parseInt(coordsF[0]);
        Integer columnF = Integer.parseInt(coordsF[1]);

        Integer rowT = Integer.parseInt(coordsT[0]);
        Integer columnT = Integer.parseInt(coordsT[1]);

        Piece toMove = boardholder[rowF][columnF];
        if(boardholder[rowF][columnF] == null){
            return false;
        }

        if(board[rowF][columnF].getPlayer() != player){
            return false;
        }
        ArrayList<String> moves;
            if (toMove.getClass().getName().equals("pieceTypes.Rook")) {
                Rook selected = (Rook) toMove;
                moves = selected.potentialMoves(boardholder);
            } else if (toMove.getClass().getName().equals("pieceTypes.Bishop")) {
                Bishop selected = (Bishop) toMove;
                moves = selected.potentialMoves(boardholder);
            } else if (toMove.getClass().getName().equals("pieceTypes.Knight")) {
                Knight selected = (Knight) toMove;
                moves = selected.potentialMoves(boardholder);
            } else if (toMove.getClass().getName().equals("pieceTypes.Queen")) {
                Queen selected = (Queen) toMove;
                moves = selected.potentialMoves(boardholder);
            } else if (toMove.getClass().getName().equals("pieceTypes.King")) {
                King selected = (King) toMove;
                moves = selected.potentialMoves(boardholder);
            } else {
                Pawn selected = (Pawn) toMove;
                moves = selected.potentialMoves(boardholder);
            }


        boolean gate = false;
        for(int i = 0 ; i < moves.size(); i++){
            String[] loc = moves.get(i).split(" ");
            int rt = Integer.parseInt(loc[1]);
            int ct = Integer.parseInt(loc[4]);
            if(rt == rowT && ct == columnT){
                gate = true;
                break;
            }
        }
        if(!gate){
            return false;
        }

        if(rowF == rowT && columnF == columnT){
            return false;
        }

        if( 0 > rowF || columnF < 0 || 0 > rowT || columnT < 0 ){
            return false;
        }else if( 7 < rowF || columnF > 7 || 7 < rowT || columnT > 7 ){
            return false;
        }

        if(boardholder[rowT][columnT] != null){
            if(boardholder[rowF][columnF].getPlayer() == boardholder[rowT][columnT].getPlayer()){

                return false;
            }else if(boardholder[rowT][columnT].getClass().getName().equals("pieceTypes.King")){

                return false;
            }
        }


        Piece x = boardholder[rowF][columnF].getPlayer().getKings();
        King king = (King) x;
        Move(rowF,columnF,rowT,columnT);
        if(king.isChecked(boardholder)){
            return false;
        }else{
            return true;
        }

    }

    /**
     * Called when a player is castling
     * @param king The king involved in the move
     * @param rook The rook involved in the move
     * @param side The side the of the king the rook is on in the form of the kings side(left) queens side (right)
     */
    public void Castle(King king, Rook rook, String side){
        String[] L = king.getlocation().split(" ");
        Integer Krow = Integer.parseInt(L[1]);

        if(side.equals("queen")){
            king.setLocation(Krow,2);
            rook.setLocation(Krow,3);

            boardholder[Krow][2] = king;
            boardholder[Krow][3] = rook;
        }

        if(side.equals("king")){
            king.setLocation(Krow,6);
            rook.setLocation(Krow,5);

            boardholder[Krow][6] = king;
            boardholder[Krow][5] = rook;
        }

        rook.noCastle();
        king.noCastle();

    }

    /**
     * Checks to see if a player is in checkmate
     * @return returns true if valid, false otherwise
     */
    public boolean isGoal(Piece king){
        King check = (King) king;
        String color = check.getPlayer().getColor();
        Player loser;
        String winner;
        if(color.equals("black")){
            loser = black;
            winner = "white";
        }else{
            loser = white;
            winner = "black";
        }

        if(check.isChecked(board)){
            String[] coords = check.getlocation().split(" ");
            int fRow = Integer.parseInt(coords[1]);
            int fColumn= Integer.parseInt(coords[4]);
            coords[0] = coords[1];
            coords[1] = coords[4];

            String tCoords = (fRow-1) + " " + (fColumn-1);
            String[] tC = tCoords.split(" ");
            if(fRow-1 >=0 && fColumn -1 >=0 && isValid(coords,tC,loser)){
                return false;
            }

            tCoords = (fRow-1) + " " + fColumn;
            tC = tCoords.split(" ");
            if(fRow-1 >=0 && isValid(coords,tC,loser)){
                return false;
            }

            tCoords = (fRow-1) + " " +(fColumn+1);
            tC = tCoords.split(" ");
            if(fRow-1 >=0 && fColumn +1 <= 7 && isValid(coords,tC,loser)){
                return false;
            }

            tCoords = fRow + " " + (fColumn+1);
            tC = tCoords.split(" ");
            if(fColumn +1 <= 7 && isValid(coords,tC,loser)){
                return false;
            }

            tCoords = (fRow+1) + " " + (fColumn+1);
            tC = tCoords.split(" ");
            if( fRow+1 <=7 && fColumn +1 <=7 &&isValid(coords,tC,loser)){
                return false;
            }

            tCoords = (fRow+1) + " " + fColumn;
            tC = tCoords.split(" ");
            if(fRow +1 <= 7 && isValid(coords,tC,loser)){
                return false;
            }

            tCoords = (fRow+1) + " " + (fColumn-1);
            tC = tCoords.split(" ");
            if( fRow+1 <=7 && fColumn -1 >=0 && isValid(coords,tC,loser)){
                return false;
            }

            tCoords = fRow + " " + (fColumn-1);
            tC = tCoords.split(" ");
            if(fColumn-1 >= 0 &&isValid(coords,tC,loser)){
                return false;
            }

            System.out.println( loser.getColor() + " is in check and is unable to make a move.");
            System.out.println("Checkmate!\n" + winner + " Wins!!!");

            return true;
        }

        return false;
    }


}
