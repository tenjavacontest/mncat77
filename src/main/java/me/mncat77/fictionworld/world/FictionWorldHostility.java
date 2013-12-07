package me.mncat77.fictionworld.world;

public enum FictionWorldHostility {
    
    IDYLL(0),
    PEACEFUL(1),
    HOSTILE(2),
    APOCALYPSE(3);
    
    public final int hostility;
    
    
    FictionWorldHostility(int hostility) {
        this.hostility = hostility;
    }
    
}
