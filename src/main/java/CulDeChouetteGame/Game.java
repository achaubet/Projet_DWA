/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteGame;

import java.util.ArrayList;

/**
 *
 * @author Arnaud
 */
public class Game {
    
    public static ArrayList<String> playerOrder;
    
    public static void setPlayerOrder(ArrayList<String> players) {
        playerOrder = new ArrayList<>(players);
    }
    
    public static int rollDice() {
        return (int) (Math.random() * 6) + 1;
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
}
