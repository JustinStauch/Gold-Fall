package goldfall;

import goldfall.listeners.Blocks;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GoldFall extends JavaPlugin {
    public PluginDescriptionFile info;
    public int use;
    public int denom;
    public String message;
    private PluginManager manager;
    private File directory, config;
    private BufferedReader read;
    
    @Override
    public void onDisable() {
        info = getDescription();
        
        System.out.println("[" + info.getName() + "] disabled");
    }
    
    @Override
    public void onEnable() {
        info = getDescription();
        manager = getServer().getPluginManager();
        directory = getDataFolder();
        
        System.out.println("[" + info.getName() + "] ENABLED!!!!!!!");
        
        manager.registerEvent(Type.BLOCK_BREAK, new Blocks(this), Priority.Low, this);
        
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        config = new File(directory, "config.txt");
        
        try {
            read = new BufferedReader(new FileReader(config));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GoldFall.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        BufferedWriter out = null;
        if (!config.exists()) {
            try {
                config.createNewFile();
                out = new BufferedWriter(new FileWriter(config));
                out.write("frequency: 1/20");
                out.newLine();
                out.write("message: +name just got a golden apple!");
            } catch (IOException ex) {
                System.out.println("[" + info.getName() + "] Error: " + ex.getMessage());
            } finally {
                try {
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(GoldFall.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        ArrayList<String> often = new ArrayList<String>();
        Scanner scan = null;
        try {
            try {
                scan = new Scanner(config);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GoldFall.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (scan.hasNextLine()) {
                often.add(scan.nextLine());
            }
        } finally {
            scan.close();
        }
        for (int x = 0; x < often.size(); x++) {
            String[] there = often.get(x).split(": ", 2);
            if (there[0].equalsIgnoreCase("frequency")) {
                String[] fract = there[1].split("/");
                if (fract.length == 2) {
                    use = Integer.parseInt(fract[0]);
                    denom = Integer.parseInt(fract[1]);
                    int temp = (use/denom) * 100;
                    System.out.println("[" + info.getName() + "] There is a " + temp + "% chance of getting a golden apple when a leaf is brocken");
                }
                else {
                    use = Integer.parseInt(there[1]);
                    denom = 100;
                    System.out.println("[" + info.getName() + "] There is a " + use + "% chance of getting a golden apple when a leaf is broken");
                }
                use = use - 1;
            }
            if (there[0].equals("message")) {
                message = there[1];
            }
        }
    }
}
