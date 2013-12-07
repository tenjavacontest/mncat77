package me.mncat77.fictionworld.world;

import java.util.List;
import java.util.Random;

public class FictionWorldAttributes {
    
    private static final String[] lowTemperatureWords = {"cold", "frozen", "icy", "snow", "ice"};
    private static final String[] highTemperatureWords = {"hot", "volcano", "volcanic", "lava", "magma", "fire", "desert", "savanna"};
    private static final String[] lowRainfallWords = {"dry", "arid"};
    private static final String[] highRainfallWords = {"moist", "water", "river", "ocean", "rain"};
    private static final String[] lowAltitudeDiffrenceWords = {"flat", "plain", "shallow", "even"};
    private static final String[] highAltitudeDiffrenceWords = {"hill", "valley", "ravine", "mount"};
    
    //Biome distribution attributes
    private final double averageRainfall;
    private final double averageTemperature;
    
    //Altitude value attributes
    private final int waterLevel;
    
    private final double noiseAmplitude;
    private final double noiseFrequency;
    
    private final double distanceGridAddition;
    
    //Structure and Creature attributes
    private FictionWorldHostility hostility;
    
    
    public FictionWorldAttributes(List<String> describingWords) {
        Random rand = new Random();
        
        double averageRainfall = .5D;
        double averageTemperature = .5D;
        double altitudeDiffrence = .5D;
        
        if (containsAny(describingWords, lowTemperatureWords)) {
            averageTemperature -= .3D;
        }
        if (containsAny(describingWords, highTemperatureWords)) {
            averageTemperature += .3D;
        }
        
        //Make Rainfall and Temperature correlate
        averageRainfall += .3D * averageTemperature -.15D;
        
        if (containsAny(describingWords, lowRainfallWords)) {
            averageRainfall -= .3D;
        }
        if (containsAny(describingWords, highRainfallWords)) {
            averageRainfall += .3D;
        }
        
        if (containsAny(describingWords, lowAltitudeDiffrenceWords)) {
            altitudeDiffrence -= .3D;
        }
        if (containsAny(describingWords, highAltitudeDiffrenceWords)) {
            altitudeDiffrence += .3D;
        }
        
        //Randomize everything a little
        averageRainfall = (averageRainfall - .5D) * (.9 + Math.random() / 5.0D) + .5D;
        averageTemperature = (averageRainfall - .5D) * (.9 + Math.random() / 5.0D) + .5D;
        
        this.averageRainfall = averageRainfall;
        this.averageTemperature = averageTemperature;
        this.waterLevel = 64 + (int)Math.round(12.0D * averageRainfall - 6.0D);
        this.noiseAmplitude = altitudeDiffrence;
        this.noiseFrequency = altitudeDiffrence;
        this.distanceGridAddition = altitudeDiffrence / 3.0D;
        
        for (String s : describingWords) {
            if (s.contains("apocalyp")) {
                this.hostility = FictionWorldHostility.APOCALYPSE;
            }
        }
        if(this.hostility != FictionWorldHostility.APOCALYPSE) {
           this.hostility = FictionWorldHostility.values()[rand.nextInt(4)]; 
        }
    }
    
    private FictionWorldAttributes(double averageRainfall, double averageTemperature, int waterLevel, double noiseAmplitude, double noiseFrequency, double distanceGridAddition, FictionWorldHostility hostility) {
        this.averageRainfall = averageRainfall;
        this.averageTemperature = averageTemperature;
        this.waterLevel = waterLevel;
        this.noiseAmplitude = noiseAmplitude;
        this.noiseFrequency = noiseFrequency;
        this.distanceGridAddition = distanceGridAddition;
        this.hostility = hostility;
    }
    
    private static boolean containsAny(List<String> list, String[] words) {
        for (String s : list) {
            for (String word : words) {
                if (s.contains(word)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return averageRainfall + ";" + averageTemperature + ";" + waterLevel + ";" + noiseAmplitude + ";" + noiseFrequency + ";" + distanceGridAddition + ";" + hostility.hostility;
    }
    
    public static FictionWorldAttributes fromString(String s) {
        String[] split = s.split(";");
        double averageRainfall = Double.parseDouble(split[0]);
        double averageTemperature = Double.parseDouble(split[1]);
        int waterLevel = Integer.parseInt(split[2]);
        double noiseAmplitude = Double.parseDouble(split[3]);
        double noiseFrequency = Double.parseDouble(split[4]);
        double distanceGridAddition = Double.parseDouble(split[5]);
        FictionWorldHostility hostility = FictionWorldHostility.values()[Integer.parseInt(split[6])];
        
        return new FictionWorldAttributes(averageRainfall, averageTemperature, waterLevel, noiseAmplitude, noiseFrequency, distanceGridAddition, hostility);
    }

    public double getAverageRainfall() {
        return averageRainfall;
    }

    public double getAverageTemperature() {
        return averageTemperature;
    }

    public double getDistanceGridAddition() {
        return distanceGridAddition;
    }

    public FictionWorldHostility getHostility() {
        return hostility;
    }

    public double getNoiseAmplitude() {
        return noiseAmplitude;
    }

    public double getNoiseFrequency() {
        return noiseFrequency;
    }

    public int getWaterLevel() {
        return waterLevel;
    }
    
}
