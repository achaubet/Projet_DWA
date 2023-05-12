/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteGame;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Arnaud
 */
public class Game {
    
    public static int scoreMax = 343;
    public static ArrayList<String> playerOrder = new ArrayList<>();
    public static ArrayList<Integer> lastPlayerResults = new ArrayList<>();
    public static ArrayList<String> chouettesVeluesDataKey = new ArrayList<>();
    public static ArrayList<Boolean> chouettesVeluesDataObject = new ArrayList<>();
    public static ArrayList<String> suitesDataKey = new ArrayList<>();
    public static ArrayList<Boolean> suitesDataObject = new ArrayList<>();
    public static Boolean areConsecutives = false;
    public static Boolean areChouetteVelute = false;
    public static int nbCv = 0;
    public static int nbSuites = 0;
       
    
    public static int rollDice() {
        lastPlayerResults.add((int) (Math.random() * 6) + 1);
        return lastPlayerResults.get(lastPlayerResults.size() - 1);
    }
    
    public static int calculateScore() {
        int dice1 = lastPlayerResults.get(0);
        int dice2 = lastPlayerResults.get(1);
        int dice3 = lastPlayerResults.get(2);
        int score = 0;
        // Velute
        if(dice1 + dice2 == dice3) {
            score = dice3 * dice3;
        }
        // Le cul de chouette
        else if(dice1 == dice2 && dice1 == dice3) {
            score = 50 + (dice1 * 10);
        }
        // Chouette
        else if(dice1 == dice2) {
            score = dice1 * dice2;
        }
        return score;
    }
    
    public static Boolean detectInteraction() {
        //System.out.println(lastPlayerResults.toString());
        int dice1 = lastPlayerResults.get(0);
        int dice2 = lastPlayerResults.get(1);
        int dice3 = lastPlayerResults.get(2);
        // Si velute détéctée
        if((dice1 == dice2) && (dice1 + dice2 == dice3)) {
            System.out.println("VELUTE DETECTEE");
            nbCv++;
            areChouetteVelute = true;
            return true;
        }
        // Si suite détéctée
        int[] orderedList = {dice1, dice2, dice3};
        Arrays.sort(orderedList);
        if((orderedList[0] + 1 == orderedList[1]) && (orderedList[1] + 1 == orderedList[2])) {
            System.out.println("VALUES ARE CONseCutives");
            nbSuites++;
            areConsecutives = true;
            return true;
        }

        // Sinon
        return false;
    }
    
    public static void setChouetteVelueWinner(String winnerPlayername) {
        //chouettesVeluesData.put(winnerPlayername, true);
        chouettesVeluesDataKey.add(winnerPlayername);
        chouettesVeluesDataObject.add(true);
        for(String player: playerOrder) {
            if(!player.equals(winnerPlayername)) {
                //chouettesVeluesData.put(player, false);
                chouettesVeluesDataKey.add(winnerPlayername);
                chouettesVeluesDataObject.add(false);
            }
        }
    }
    
    public static void setSuiteLoose(String loosePlayername) {
        //suitesData.put(loosePlayername, false);
        suitesDataKey.add(loosePlayername);
        suitesDataObject.add(false);
        for(String player: playerOrder) {
            if(!player.equals(loosePlayername)) {
                // suitesData.put(player, true);
                suitesDataKey.add(player);
                suitesDataObject.add(true);
            }
        }
    }
    
    public static void clearGameData() {
        playerOrder.clear();
        lastPlayerResults.clear();
        chouettesVeluesDataKey.clear();
        chouettesVeluesDataObject.clear();
        suitesDataKey.clear();
        suitesDataObject.clear();
        nbCv = 0;
        nbSuites= 0;
    }
}
