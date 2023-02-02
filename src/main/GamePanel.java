package main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import static main.Game.*;


public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
    private Game game;
    

    public GamePanel(Game game) {
        keyboardInputs = new KeyboardInputs(this);
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();

        addMouseListener(mouseInputs);
        addKeyListener(keyboardInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);       
    }

    public void updateGame() {

    }
    
    public void backr(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        backr(g);
        
        game.rander(g);
    }
    
    public Game getGame() {
        return this.game;
    }
}
