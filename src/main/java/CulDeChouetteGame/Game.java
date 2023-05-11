/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteGame;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Arnaud
 */
public class Game {
    
    public static ArrayList<String> playerOrder = new ArrayList<>();
    public static ArrayList<Integer> lastPlayerResults = new ArrayList<>();
    public static ConcurrentHashMap<String, Boolean> chouettesVeluesData = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, Boolean> suitesData = new ConcurrentHashMap<>();
       
    
    public static int rollDice() {
        lastPlayerResults.add((int) (Math.random() * 6) + 1);
        return lastPlayerResults.get(lastPlayerResults.size() - 1);
    }
    
    public static int calculateScore(int dice1, int dice2, int dice3) {
        int score = 0;
        // Velute
        if(dice1 + dice2 == dice3) {
            score = dice3 * dice3;
        }
        // Chouette
        else if(dice1 == dice2) {
            score = dice1 * dice2;
        }
        // Le cul de chouette
        else if(dice1 == dice2 && dice1 == dice3) {
            score = 50 * (dice1 * 10);
        }
        return score;
    }
    
    public static void setChouetteVelueWinner(String winnerPlayername) {
        chouettesVeluesData.put(winnerPlayername, true);
        for(String player: playerOrder) {
            if(!player.equals(winnerPlayername)) {
                chouettesVeluesData.put(player, false);
            }
        }
    }
    
    public static void setSuiteLoose(String loosePlayername) {
        suitesData.put(loosePlayername, false);
        for(String player: playerOrder) {
            if(!player.equals(loosePlayername)) {
                suitesData.put(player, true);
            }
        }
    }
    
    public static void clearGameData() {
        playerOrder.clear();
        lastPlayerResults.clear();
        chouettesVeluesData.clear();
        suitesData.clear();
    }
}
