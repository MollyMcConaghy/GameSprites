package main;
import java.util.Random;

/**@author coding_java */
public class Spawn {
    private Handler handler;
    private Health health;
    private final SpriteSheet ss;
    private Random r = new Random();
    private int scoreNew = 0;
    
    public  Spawn(Handler handler, Health health, SpriteSheet ss){
        this.handler = handler;
        this.health = health;
        this.ss = ss;
    }
    
    public void tick(){
        scoreNew++;
        
        if(scoreNew >= 250){
            scoreNew = 0;
            health.setLevel(health.getLevel() + 1);
        if(health.getLevel() == 2){    
            handler.addObject(new Enemy2(r.nextInt(Game.W), r.nextInt(Game.H -100), ID.Enemy2, ss));
        }else if(health.getLevel() == 3){
            handler.addObject(new Enemy2(r.nextInt(Game.W), r.nextInt(Game.H), ID.Enemy2, ss));
            handler.addObject(new Enemy(r.nextInt(Game.W), r.nextInt(Game.H), ID.Enemy, ss));
        }
        }
    }
}

