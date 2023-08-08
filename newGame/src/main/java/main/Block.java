/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author mollymcconaghy
 */
public class Block extends GameObject{
    public Block(int x, int y, ID id, SpriteSheet ss){
        super(x, y, id, ss);
    }


    public void tick() {
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x, y, 32, 32);
    }

    
    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
    
}
