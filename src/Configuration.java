import pieceTypes.Player;

import java.util.Scanner;

/**
 * Class controlling how the game operates
 *
 * @author Aaron Turetsky
 */
public class Configuration {

    /**
     * Has access to the players and the board
     */
    private static Board set;

    /**
     * Checks to see if the string check is in the correct form #/#
     * @param check - String whose value is to be checked
     * @return - true if correct , false otherwise
     */
    private static boolean correctSyntax(String check){
        if(check.length() != 3){
            return false;
        }else if(String.valueOf(check.charAt(1)).equals('/')){
            return false;
        }

        try{
            int num = Integer.parseInt(String.valueOf(check.charAt(0)));
            if(num > 7 || num < 0){
                return false;
            }
        }catch(NumberFormatException e){
            return false;
        }
        try{
            int num = Integer.parseInt(String.valueOf(check.charAt(2)));
            if(num > 7 || num < 0){
                return false;
            }
        }catch(NumberFormatException e){
            return false;
        }


        return true;
    }

    /**
     * Carries out a move for a player and returns whether or not that move ended the game
     * @param player - The player whose move is to be done
     * @return true if a player is in checkmate, false otherwise
     */
    private static boolean move(Player player){
        Scanner reader = new Scanner(System.in);
        String code;
        Boolean validMove = false;
        while (!validMove) {
            System.out.println(player.getColor() + "'s turn!");

            System.out.println(set.boardToString());
            System.out.println("Please select piece to move/command:");
            code = reader.nextLine();

            String[] coordsF;
            String[] coordsT;
            String row;
            String column;

            if (code.equalsIgnoreCase("castle/queenside") || code.equalsIgnoreCase("castle/kingside")) {
                coordsT = null;

                coordsF = code.split("/");
                row = coordsF[0];
                column = coordsF[1];
            }else{
                while (!correctSyntax(code)) {
                    System.out.println("INVALID INPUT");
                    System.out.println("Please select piece to move/command:");
                    code = reader.nextLine();
                }

                coordsF = code.split("/");
                row = coordsF[0];
                column = coordsF[1];

                System.out.println("Please select where to move the piece");
                code = reader.nextLine();
                while (!correctSyntax(code)) {
                    System.out.println("INVALID INPUT");
                    System.out.println("Please select piece to move/command:");
                    code = reader.nextLine();
                }
                coordsT = code.split("/");
            }

            validMove = set.isValid(coordsF, coordsT, player);
            if (!validMove) {
                System.out.println("MOVE IS INVALID PLEASE CHOOSE ANOTHER MOVE");
            }

        }

        set.updateSet();
        Player Opposing;
        if(player.getColor().equals("black")){
            Opposing = set.getWhite();
        }else{
            Opposing = set.getBlack();
        }
        return set.isGoal(Opposing.getKings());
    }
    /**
     * Controls the games overarching procedures, including inputs for a players turns
     * @param args Unused
     */
    public static void main(String[] args) {
        set = new Board();

        System.out.println("To make a move when prompted enter the coordinates of the piece you would like to move in the form of #/# (row/column)");
        System.out.println("Otherwise to castle enter: 'Castle/Queenside' or 'Castle/Kingside");

        while (true) {
            if(move(set.getWhite())){
                break;
            }
            if(move(set.getBlack())){
                break;
            }
        }
    }
}
