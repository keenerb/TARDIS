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
package me.eccentric_nz.TARDIS.utility;

import java.util.ArrayList;
import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.TARDISConstants;
import me.eccentric_nz.TARDIS.database.QueryFactory;
import me.eccentric_nz.TARDIS.database.ResultSetChunks;
import me.eccentric_nz.TARDIS.database.ResultSetLamps;
import me.eccentric_nz.TARDIS.database.ResultSetTardis;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * The TARDIS scanner was the main method for the occupants of the vessel to
 * observe the outside environment. The appearance and specifications of the
 * scanner system varied significantly in the course of the Doctor's travels.
 *
 * @author eccentric_nz
 */
public class TARDISLampScanner {

    private final TARDIS plugin;

    public TARDISLampScanner(TARDIS plugin) {
        this.plugin = plugin;
    }

    /**
     * Updates TARDISes from pre-malfunction plugin versions so that the lamps
     * can flash.
     *
     * @param owner the Timelord of the TARDIS
     * @return true if the TARDIS has not been updated, otherwise false
     */
    public boolean addLampBlocks(Player owner) {
        // check they have permission
        if (!owner.hasPermission("tardis.update")) {
            owner.sendMessage(plugin.pluginName + TARDISConstants.NO_PERMS_MESSAGE);
            return false;
        }
        HashMap<String, Object> where = new HashMap<String, Object>();
        where.put("owner", owner.getName());
        ResultSetTardis rs = new ResultSetTardis(plugin, where, "", false);
        if (rs.resultSet()) {
            int id = rs.getTardis_id();
            // check if they have already got lamp records
            HashMap<String, Object> wherel = new HashMap<String, Object>();
            wherel.put("tardis_id", id);
            ResultSetLamps rsl = new ResultSetLamps(plugin, wherel, false);
            QueryFactory qf = new QueryFactory(plugin);
            if (rsl.resultSet()) {
                owner.sendMessage(plugin.pluginName + "Deleting previously stored TARDIS lamps!");
                HashMap<String, Object> wheredel = new HashMap<String, Object>();
                wheredel.put("tardis_id", id);
                qf.doDelete("lamps", wheredel);
            }
            // get the TARDIS console chunks
            HashMap<String, Object> wherec = new HashMap<String, Object>();
            wherec.put("tardis_id", id);
            ResultSetChunks rsc = new ResultSetChunks(plugin, wherec, true);
            if (rsc.resultSet()) {
                int starty, endy;
                switch (rs.getSchematic()) {
                    case BIGGER:
                        starty = 20;
                        endy = 28;
                        break;
                    case DELUXE:
                        starty = 20;
                        endy = 27;
                        break;
                    case ELEVENTH:
                        starty = 16;
                        endy = 30;
                        break;
                    case REDSTONE:
                        starty = 20;
                        endy = 33;
                        break;
                    default:
                        starty = 19;
                        endy = 28;
                        break;
                }
                ArrayList<HashMap<String, String>> data = rsc.getData();
                // loop through the chunks
                for (HashMap<String, String> map : data) {
                    String w = map.get("world");
                    World world = plugin.getServer().getWorld(w);
                    int x = plugin.utils.parseNum(map.get("x"));
                    int z = plugin.utils.parseNum(map.get("z"));
                    Chunk chunk = world.getChunkAt(x, z);
                    // find the lamps in the chunks
                    int bx = chunk.getX() << 4;
                    int bz = chunk.getZ() << 4;
                    for (int xx = bx; xx < bx + 16; xx++) {
                        for (int zz = bz; zz < bz + 16; zz++) {
                            for (int yy = starty; yy < endy; yy++) {
                                int typeId = world.getBlockTypeIdAt(xx, yy, zz);
                                if (typeId == 124) {
                                    String lamp = w + ":" + xx + ":" + yy + ":" + zz;
                                    HashMap<String, Object> set = new HashMap<String, Object>();
                                    set.put("tardis_id", id);
                                    set.put("location", lamp);
                                    qf.doInsert("lamps", set);
                                    owner.sendMessage("Added lamp at: " + xx + ":" + yy + ":" + zz);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } else {
            owner.sendMessage(plugin.pluginName + "You are not a Time Lord!");
            return false;
        }
    }
}
