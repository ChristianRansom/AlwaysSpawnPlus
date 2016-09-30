package muttsworld.dev.team.AlwaysSpawnPlus;

/*AlwaysSpawn is a CraftBukkit plugin originally created by UkzFinestModdar.
 *  Since the original author has discontinued to update the plugin, the 
 *  Muttsworld Devevlopment Team has decided to update the plugin ourselves
 *   for our own personal use along with adding a few additional functions. 
 *   
 *   This plugin was updated for spigot 1.10.2
 *   
 *   
 *   
*/
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class AlwaysSpawnPlus extends JavaPlugin implements Listener
{
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    Player p = (Player)sender;
    if (cmd.getName().equalsIgnoreCase("setwspawn")) {
      if (p.hasPermission("spawnonjoin.set"))
      {
        p.sendMessage("§aSetting the spawn...");
        getConfig().set("spawn.world", 
          p.getLocation().getWorld().getName());
        getConfig().set("spawn.x", 
          Double.valueOf(p.getLocation().getX()));
        getConfig().set("spawn.y", 
          Double.valueOf(p.getLocation().getY()));
        getConfig().set("spawn.z", 
          Double.valueOf(p.getLocation().getZ()));
        saveConfig();
        p.sendMessage("§bSOJ> §7Spawn Set §aSuccessfully!");        
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10.0F, -10.0F);
      }
      else
      {
        p.sendMessage("§b> §7You do not have the permission §ospawnonjoin.set");
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, 10.0F, -10.0F);
      }
    }
    if (cmd.getName().equalsIgnoreCase("spawn"))
    {
      World w = Bukkit.getServer().getWorld(
        getConfig().getString("spawn.world"));
      double x = getConfig().getDouble("spawn.x");
      double y = getConfig().getDouble("spawn.y");
      double z = getConfig().getDouble("spawn.z");
      p.teleport(new Location(w, x, y, z));
      p.sendMessage("§e>> §6Teleporting to spawn...");
      p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 10.0F, -10.0F);
      p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 120, 
        4));
    }
    if (cmd.getName().equalsIgnoreCase("spawnonjoin"))
    {
      p.sendMessage("§8SpawnOnJoin version 1.2.1 by TheCoderX.");
      p.sendMessage("§7§oCommands§7:");
      p.sendMessage("§a/soj setspawn§7: §fSets the server spawnpoint.");
      p.sendMessage("§a/soj spawn§7: §fTeleports to the server spawnpoint.");
      p.sendMessage("§7§oPermissions§7:");
      p.sendMessage("§aspawnonjoin.set§7: §fAllows access to /setspawn.");
      p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10.0F, -10.0F);
    }
    return true;
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e)
  {
    Player p = e.getPlayer();
    try
    {
      e.setJoinMessage(null);
      p.sendMessage("§e>> §6Teleporting to spawn.");
      World w = Bukkit.getServer().getWorld(
        getConfig().getString("spawn.world"));
      double x = getConfig().getDouble("spawn.x");
      double y = getConfig().getDouble("spawn.y");
      double z = getConfig().getDouble("spawn.z");
      p.teleport(new Location(w, x, y, z));
    }
    catch (Exception localException) {}
  }
}
