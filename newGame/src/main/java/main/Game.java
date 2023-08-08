package main;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

 /** @author 
  **/

public class Game extends Canvas implements Runnable{

    public static void main(String[] args) {
        Game game = new Game();
        
    }
    public static final int W = 1000, H = 563;
    private Thread thread;
    private boolean running = false;
    private final SpriteSheet ss;
    private final Random r;
    private Health health;
    private Spawn spawn;
    private Menu menu;
    //include enumeration for game
    public enum STATE{
        Menu,
        Options,
        Game
    };
    //can change game state
    public STATE gameState = STATE.Menu;
    
    
    
    //create an instance of our handler 
    private final Handler handler;
    private BufferedImage sprite_sheet = null;
    private Camera camera; 
    
    private BufferedImage level = null;
    public Game(){
        //need to initialize the handler first 
        handler = new Handler();
        camera = new Camera(0, 0);
        
        this.addKeyListener(new Action(handler, camera));
        BufferedImageLoader loader = new BufferedImageLoader();
        sprite_sheet = loader.loadImage("/spritesheet.png"); 
        
        level = loader.loadImage("/wizard_level.png");
        
        loadLevel(level);
        
        ss = new SpriteSheet(sprite_sheet);
        Window window = new Window(W, H, "new game", this);
        health = new Health();
        spawn = new Spawn(handler, health, ss);
        menu = new Menu(this, handler, ss);
        this.addMouseListener(menu);
        r = new Random();
        if(gameState == STATE.Game)
        {                
            handler.addObject(new Player(100, 400, ID.Player, ss, handler)); //sets the coords 
        for (int i = 0; i < 3; i++)
        handler.addObject(new Enemy(r.nextInt(W), r.nextInt(H), ID.Enemy, ss)); //sets the coords 
        }
        //new player specs   
        //put as many objects in game as you like 
        
        //handler.addObject(new Player(100, 200, ID.Player2)); //sets the coords 
        //for (int i = 0; i < 2; i++)
        //handler.addObject(new Enemy2(r.nextInt(W), r.nextInt(H), ID.Enemy2, ss)); //sets the coords 
        //handler.addObject(new Player(100, 200, ID.Player2)); //sets the coords         
       
   }
        
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
        
    }
    
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(InterruptedException e){
        }
        
    }
    
    public void run(){
      
      this.requestFocus();
      long lastTime = System.nanoTime(); // get current time to the nanosecond
      double amountOfTicks = 60.0; // set the number of ticks 
      //divide 60 into 1e9 of nano seconds or about 1 second
      double nanoseconds = 1000000000 / amountOfTicks; 
      double delta = 0;
      long timer = System.currentTimeMillis(); // get current time
      int frames = 0; // set frame variable
      while(running) {
      // get current time in nonoseconds durring current loop
       long now = System.nanoTime(); 
       delta += (now - lastTime) / nanoseconds; //add the amount of change 
       //since the last loop
       lastTime = now; // set lastTime to now to prepare for next loop
       while(delta >= 1) {
        tick();
        delta--;
       }
       if(running){
            render();  // render visuals of the game
       frames++; //frame has passed
       }
       if(System.currentTimeMillis() - timer > 1000) { //if one second has passed
        timer += 1000;  // add a thousand to our timer for next time
        frames = 0;  // reset frame count for the next second
  
       }
      }
      stop(); // no longer running stop the thread
        
    
    }  

    private void tick() {
        
         for(int i = 0; i < handler.object.size(); i++){
            if(handler.object.get(i).getId() == ID.Player){
                camera.tick(handler.object.get(i));
            }
        }       
        
        handler.tick();
        if(gameState == STATE.Game){
        health.tick();
        spawn.tick();
    }else if(gameState == STATE.Menu || gameState == STATE.Options){
        menu.tick();
    }
    }
   
    private void render() {
        BufferStrategy b = this.getBufferStrategy();
        if (b == null){
            this.createBufferStrategy(3);
            return;
        }
        
        Graphics g = b.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, W, H);

        g2d.translate(-camera.getX(), -camera.getY());
        
        if(gameState == STATE.Game){
        handler.render(g);
        health.render(g);
        }else if(gameState == STATE.Menu || gameState == STATE.Options){
        menu.render(g);
        }
        
        g2d.translate(camera.getX(), camera.getY());
        
        g.dispose();
        b.show();
    }
    
    
        private void loadLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();
        
        for(int xx = 0; xx < w; xx++){
            for(int yy = 0; yy < h; yy++){
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                if(green == 255)
                    handler.addObject(new Block(xx*32, yy*32, ID.Block, ss));
                if(red == 255)
                    handler.addObject(new Player(xx*32, yy*32, ID.Player, ss, handler));
                }
            }
    }
    
    public static int clamp(int var, int min, int max){
        if(var >= max)
            return var = max;
        else if(var <= min)
            return var = min;
        else 
            return var;
    }

    
}



