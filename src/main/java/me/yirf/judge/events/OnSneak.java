package me.yirf.judge.events;

import me.yirf.judge.Judge;
import me.yirf.judge.config.Config;
import me.yirf.judge.group.Group;
import me.yirf.judge.menu.Display;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.RayTraceResult;
import static me.yirf.judge.group.Group.group;

public class OnSneak implements Listener {

    Config config = new Config(){};

    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();

        if (group.get(p.getUniqueId()) != null) {
            Group.remove(p);
            return;
        }

        if(!Config.getBoolean("allow-all-worlds")) {
            if (!Judge.allowedWorlds.contains(p.getWorld())) {
                return;
            }
        }

        if (p.rayTraceEntities(10) == null){return;}
        RayTraceResult result = p.rayTraceEntities(10);
        if (!(result.getHitEntity() instanceof Player)) {return;}

        Entity entity = result.getHitEntity();

        assert event.isSneaking();

        if(Bukkit.getServer().getOnlinePlayers().contains(p)) {
            Display.spawnMenu(p, (Player) entity);
        }

    }

}
