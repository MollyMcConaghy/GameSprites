package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import static main.Game.H;
import main.Game.STATE;
import static main.Game.W;

/**
 *
 * @author coding_java
 */
public class Menu extends MouseAdapter{
    
    private Game game;
    private Handler handler;
    private final SpriteSheet ss;
    private Random r = new Random();
    public Menu(Game game, Handler handler, SpriteSheet ss){
        this.game = game;
        this.handler = handler;
        this.ss = ss;
    }
    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
   
        //play button
        if(game.gameState == STATE.Menu){
        if(mouseOver(mx, my, 200, 150, 500, 64)){
            game.gameState = STATE.Game;
                       handler.addObject(new Player(100, 400, ID.Player, ss, handler)); //sets the coords 
                        for (int i = 0; i < 3; i++)
                        handler.addObject(new Enemy(r.nextInt(Game.W), r.nextInt(Game.H), ID.Enemy, ss)); //sets the coords 
        }

        //options
        if(mouseOver(mx, my, 200, 250, 400, 64)){
            game.gameState = STATE.Options;}
        //back button for help
        if(game.gameState == STATE.Options){
            if(mouseOver(mx, my, 200, 350, 400, 64)){
                game.gameState = STATE.Menu;
                return;
            }}
                //help button
        if(mouseOver(mx, my, 210, 350, 400, 64)){
            System.exit(0);
        }
        
        }
   
    }
    public void mouseReleased(MouseEvent e){
        
    }
    public static void main(String[] args) {
       
    }
    
    //check if mouse is over target 
    private boolean mouseOver(int mx, int my, int x, int y, int W, int H){
        if(mx > x && mx < x + W){
            if(my > y && my < y + H){
                return true;
                }else return false;
        }else return false;
    }
    
    public void tick(){
    }
    
    public void render(Graphics g){
        if(game.gameState == STATE.Menu){
        //cool menu
        Font fnt = new Font("one font", 3, 50);
        Font fnt2 = new Font("font 2", 7, 40);
        g.setFont(fnt);
        g.setColor(Color.BLACK);
        g.drawString("Menu", 470, 70);
        
        //set something up
        g.setFont(fnt2);
        g.setColor(Color.BLACK);
        g.drawRect(300, 150, 500, 64);
        g.drawString("Play", 510, 200);
        g.setColor(Color.BLACK);
        
        g.drawRect(300, 250, 500, 64);
        g.drawString("Options", 470, 300);  
        
        g.setColor(Color.BLACK);
        g.drawRect(300, 350, 500, 64);       
        g.drawString("Quit", 510, 400);  
        
        
    }else if(game.gameState == STATE.Options){
        Font fnt = new Font("one font", 3, 50);
        Font fnt2 = new Font("one font", 1, 30);        
        Font fnt3 = new Font("font 2", 7, 40);

        g.setFont(fnt);
        g.setColor(Color.BLACK);
        g.drawString("Options", 400, 70);
        g.setFont(fnt2);
        g.drawString("One player game by Molly", 300, 190);
        g.drawString("Use arrow keys to move player", 300, 240);
        g.setFont(fnt3);
        g.setColor(Color.BLACK);      
        g.drawRect(300, 350, 500, 64);       
        g.drawString("Back", 500, 400);        
    }
    }
    
}
