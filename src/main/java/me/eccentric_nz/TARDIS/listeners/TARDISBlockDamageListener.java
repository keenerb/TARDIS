/*
 * Copyright (C) 2013 eccentric_nz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package me.eccentric_nz.TARDIS.listeners;

import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.ResultSetBlocks;
import me.eccentric_nz.TARDIS.database.ResultSetTardis;
import me.eccentric_nz.TARDIS.utility.TARDISHostileDisplacement;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

/**
 * The Judoon are a race of rhinocerid humanoids frequently employed as a
 * mercenary police force.
 *
 * @author eccentric_nz
 */
public class TARDISBlockDamageListener implements Listener {

    private final TARDIS plugin;
    private boolean HADS;

    public TARDISBlockDamageListener(TARDIS plugin) {
        this.plugin = plugin;
        this.HADS = this.plugin.getConfig().getBoolean("allow_hads");
    }

    /**
     * Listens for block damage to the TARDIS Police Box. If the block is a
     * Police Box block then the event is cancelled, and the player warned.
     *
     * @param event a block being damaged
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPoliceBoxDamage(BlockDamageEvent event) {
        Player p = event.getPlayer();
        Block b = event.getBlock();
        String l = b.getLocation().toString();
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("location", l);
        ResultSetBlocks rsb = new ResultSetBlocks(plugin, where, false);
        if (rsb.resultSet()) {
            String message = "You cannot break the TARDIS blocks!";
            boolean m = false;
            boolean isDoor = false;
            int id = rsb.getTardis_id();
            if (HADS && !plugin.tardisDematerialising.contains(id) && !plugin.tardisMaterialising.contains(id) && isOwnerOnline(id)) {
                if (b.getTypeId() == 71) {
                    if (isOwner(id, p.getName())) {
                        isDoor = true;
                    }
                }
                if (!isDoor && rsb.isPolice_box()) {
                    int damage = (plugin.trackDamage.containsKey(Integer.valueOf(id))) ? plugin.trackDamage.get(Integer.valueOf(id)) : 0;
                    plugin.trackDamage.put(Integer.valueOf(id), damage + 1);
                    if (damage == plugin.getConfig().getInt("hads_damage")) {
                        new TARDISHostileDisplacement(plugin).moveTARDIS(id, p);
                        m = true;
                    }
                    message = "WARNING - HADS initiating in " + (plugin.getConfig().getInt("hads_damage") - damage);
                }
            }
            event.setCancelled(true);
            if (b.getTypeId() != 71 && !m) {
                p.sendMessage(plugin.pluginName + message);
            }
        }
    }

    private boolean isOwner(int id, String p) {
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("tardis_id", id);
        where.put("owner", p);
        ResultSetTardis rst = new ResultSetTardis(plugin, where, "", false);
        return rst.resultSet();
    }

    private boolean isOwnerOnline(int id) {
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("tardis_id", id);
        ResultSetTardis rst = new ResultSetTardis(plugin, where, "", false);
        if (rst.resultSet()) {
            return plugin.getServer().getOfflinePlayer(rst.getOwner()).isOnline();
        } else {
            return false;
        }
    }
}
