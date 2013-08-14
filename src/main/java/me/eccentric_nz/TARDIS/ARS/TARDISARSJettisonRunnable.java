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
package me.eccentric_nz.TARDIS.ARS;

import java.util.HashMap;
import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.database.QueryFactory;

/**
 *
 * @author eccentric_nz
 */
public class TARDISARSJettisonRunnable implements Runnable {

    private final TARDIS plugin;
    private TARDISARSJettison slot;
    private TARDISARS room;
    private int id;

    public TARDISARSJettisonRunnable(TARDIS plugin, TARDISARSJettison slot, TARDISARS room, int id) {
        this.plugin = plugin;
        this.slot = slot;
        this.room = room;
        this.id = id;
    }

    @Override
    public void run() {
        String r = room.toString();
        // remove the room

        // give them their energy!
        int amount = Math.round((plugin.getArtronConfig().getInt("jettison") / 100F) * plugin.getRoomsConfig().getInt("rooms." + r + ".cost"));
        QueryFactory qf = new QueryFactory(plugin);
        HashMap<String, Object> set = new HashMap<String, Object>();
        set.put("tardis_id", id);
        qf.alterEnergyLevel("tardis", amount, set, null);
        // if it is a secondary console room remove the controls
        if (r.equals("BAKER") || r.equals("WOOD")) {
            // get tardis_id
            int secondary = (r.equals("BAKER")) ? 1 : 2;
            HashMap<String, Object> del = new HashMap<String, Object>();
            del.put("tardis_id", id);
            del.put("secondary", secondary);
            qf.doDelete("controls", del);
        }
    }
}