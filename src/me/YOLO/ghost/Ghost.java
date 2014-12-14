package me.YOLO.ghost;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

public class Ghost extends JavaPlugin implements Listener {
	public static Ghost plugin;
	public final Logger logger = Logger.getLogger("Minecraft");
	private Team ghostTeam;
	@Override
	public void onDisable() {
		PluginDescriptionFile p = this.getDescription();
		this.logger.info(p.getName() + " V" + p.getVersion()
				+ "Has Been Enabled!");
	}

	@Override
	public void onEnable() {
		PluginDescriptionFile p = this.getDescription();
		getServer().getPluginManager().registerEvents(this, this);
		this.logger.info(p.getName() + " V" + p.getVersion()
				+ "Has Been Enabled!");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("ghost")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("ghost.*")) {
					if (!(player.hasPotionEffect(PotionEffectType.INVISIBILITY))) {
						 player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 15));
						
					} else {
						player.sendMessage(ChatColor.RED
								+ "You are already a ghost!");
					}

				} else {
					player.sendMessage(ChatColor.RED
							+ "You don't have permission to be such diao!");
				}
			}
		} else if (label.equalsIgnoreCase("unghost")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("ghost.*")) {
					if (player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
						player.removePotionEffect(PotionEffectType.INVISIBILITY);
					} else {
						player.sendMessage(ChatColor.RED
								+ "You are not a ghost yet!");
					}
				} else {
					player.sendMessage(ChatColor.RED
							+ "You don't have permission to be such diao!");
				}
			}
		}
		return true;
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if((event.getAction().equals(Action.RIGHT_CLICK_BLOCK))&& player.getItemInHand() != null){
			if (player.getItemInHand().getType().equals(Material.ARROW)&&player.getItemInHand().getItemMeta().getDisplayName() != null) {
				if (player.getItemInHand().getItemMeta().getDisplayName().equals("Ghost")) {
					if (player.hasPermission("ghost.*")) {
						Block block= event.getClickedBlock();
						Location location = block.getLocation();
						int x = location.getBlockX();
						int y = location.getBlockY();
						int z = location.getBlockZ();
						block.getWorld().getBlockAt(x,y,z).setTypeId(36);
					}
				}
			
		}
			
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		ghostTeam.addPlayer(player);
	}
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		ghostTeam.removePlayer(player);
	}

}
