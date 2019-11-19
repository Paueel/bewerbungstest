package pauel.bewerbungstest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	// Kurze Info: Ich wei�, dass es mit Klassen eigentlich �bersichtlicher ist,
	// aber ich habe f�r dieses Plugin alles in die Main geschrieben.
	
	
	// Worlds + Locations
	
	public enum worlds {
		LBAH, NETHER;
	}
	
	World worldLBAH = Bukkit.getWorld("world");
	World worldNether = Bukkit.getWorld("world_nether");
	
	Location LBAH = new Location(worldLBAH, 1, 100, 1);
	Location Nether = new Location(worldNether, 1, 100, 1);
	
	
	// Plugin Enable + Disable Printline
	
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		System.out.println("[Bewerbungstest] Plugin activated.");
	}
	
	@Override
	public void onDisable() {
		System.out.println("[Bewerbungstest] Plugin deactivated.");
	}
	
	// Join / Leave / Quit
	
	@EventHandler
  	public void onJoin(PlayerJoinEvent e){
		
		Player p = (Player) e.getPlayer();
		
		e.setJoinMessage(null);
		
		
		// Ich denke, dass switch zwar sinnvoll ist um zu entscheiden uob es sich um einen "besonderen" Spieler handelt,
		// jedoch h�tte ich es mit "if" gel�st.
		
		switch(p.getName()) {
		
		case "Der_Zauberer":
			Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "#Dagidumm");
			break;
			
		case "Lamettaling":
			p.sendMessage(ChatColor.BLUE + "Willkommen auf dem LBAH Server!");
			break;
			
		default:
			
			break;
		
		}
		
  	}
	
	@EventHandler
  	public void onQuit(PlayerQuitEvent e){
		
		e.setQuitMessage(null);
		
	}
	
	@EventHandler
  	public void onQuit(PlayerKickEvent e){
		
		e.setLeaveMessage(null);
		
	}
	
	// WorldTP Command
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdstring, String[] args) {
		
		Player p = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("worldtp")) {
			
			if(args.length == 0) {
				p.sendMessage(ChatColor.RED + "Syntaxfehler! Benutze </worldtp Nether> oder </worldtp LBAH>.");
			}
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("Nether")) {
					if(!(p.getLocation().getWorld() == worldNether)) {
						p.teleport(Nether);
					} else {
						p.sendMessage(ChatColor.RED + "Du bist bereits im Nether. Benutze </worldtp LBAH> um zur LBAH-Welt zu gelangen.");
					}
					
				}
				
				if(args[0].equalsIgnoreCase("LBAH")) {
					if(!(p.getLocation().getWorld() == worldLBAH)) {
						p.teleport(LBAH);
					} else {
						p.sendMessage(ChatColor.RED + "Du bist bereits in der LBAH-Welt. Benutze </worldtp Nether> um in den Nether zu gelangen.");
					}
					
				}
				
			}

			return true;
		}
		
		return false;
	}
	
	// TabCompleter - Da ich haupts�chlich in der 1.8 programmiere, wo es keinen TabCompleter mehr gibt, musste ich erstmal schauen wie er
	// funktioniert. Ich hoffe, dass es effizient umgesetzt wurde.
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String cmdstring, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("worldtp")) {
			
			if(args.length == 1) {
				List<String> worldlist = new ArrayList<String>(); {
					for(worlds s : worlds.values()) {
						worldlist.add(s.name().toLowerCase()); // Ich fand Lower Case sch�ner anzusehen
					}
				}
				
				return worldlist;
			}

		}
		
		return null;
		
	}

}
