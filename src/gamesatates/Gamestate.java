package gamesatates;

public enum Gamestate {
    PLAYING, MENU, OPITONS, QUIT;
    
    public static Gamestate state = MENU;

    public static void setState(Gamestate state) {
        Gamestate.state = state;
    }
    
}
