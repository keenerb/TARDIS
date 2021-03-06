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
package me.eccentric_nz.TARDIS.rooms;

import me.eccentric_nz.TARDIS.TARDIS;
import me.eccentric_nz.TARDIS.TARDISConstants.SCHEMATIC;

/**
 *
 * @author eccentric_nz
 */
public class TARDISSeedData {

    private TARDIS plugin;
    private int id;
    private SCHEMATIC schematic;
    private String room;
    private int minx;
    private int maxx;
    private int minz;
    private int maxz;

    public TARDISSeedData(TARDIS plugin) {
        this.plugin = plugin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SCHEMATIC getSchematic() {
        return schematic;
    }

    public void setSchematic(SCHEMATIC schematic) {
        this.schematic = schematic;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getMinx() {
        return minx;
    }

    public int getMaxx() {
        return maxx;
    }

    public int getMinz() {
        return minz;
    }

    public int getMaxz() {
        return maxz;
    }

    public void setChunkMinMax(String s) {
        String[] data = s.split(":");
        int x = plugin.utils.parseNum(data[1]);
        int z = plugin.utils.parseNum(data[2]);
        this.minx = x - 4;
        this.maxx = x + 4;
        this.minz = z - 4;
        this.maxz = z + 4;
    }
}
