package main;
import java.awt.Color;
import java.awt.Graphics;
/**
 * @author coding_java
 */
public class Health {
    //be able to transition from green to red 
    public static int health = 100;
    //rgb value go from 0 which is black to 255
    private int GreenVal = 255;
    private int score = 0;
    private int level = 1;
    
    public void tick(){
        health = Game.clamp(health, 1, 100);
        GreenVal = Game.clamp(GreenVal, 0, 255);
        GreenVal = health*2;
        
        score++;
    }
    
    public void render(Graphics g ){
        g.setColor(Color.black);
        g.fillRect(10, 10, 200, 25);
        g.setColor(new Color(75, GreenVal, 0));
        g.fillRect(10, 10, health * 2, 25);
        g.setColor(Color.white);
        g.drawRect(10, 10, 200, 25);
        
        g.drawString("Score " + score, 15, 64);
        g.drawString("Level " + level, 15, 80);
        
    }
    
    public void score(int score){
        this.score = score;
    }
    
    public int getScore(){
        return score;
    }
    
    public int getLevel(){
        return level;
    }
    public void setLevel(int level){
        this.level = level;
    }
}


//can draw out our score and level. 
//later will change to use my own font. 