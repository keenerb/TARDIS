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
package me.eccentric_nz.TARDIS.chameleon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Time travel is, as the name suggests, the (usually controlled) process of
 * travelling through time, even in a non-linear direction. In the 26th century
 * individuals who time travel are sometimes known as persons of meta-temporal
 * displacement.
 *
 * @author eccentric_nz
 */
public class TARDISChameleonInventory {

    private ItemStack[] terminal;
    List<String> types = new ArrayList<String>();

    public TARDISChameleonInventory() {
        types.add("Stone Brick Column");
        types.add("Desert Temple");
        types.add("Jungle Temple");
        types.add("Nether Fortress");
        types.add("Chameleon Circuit Disengaged");
        types.add("Default Police Box");
        types.add("Swamp Hut");
        types.add("New police Box");
        types.add("Party Tent");
        types.add("Village House");
        types.add("Yellow Submarine");
        this.terminal = getItemStack();
    }

    /**
     * Constructs an inventory for the Temporal Locator GUI.
     *
     * @return an Array of itemStacks (an inventory)
     */
    private ItemStack[] getItemStack() {
        ItemStack[] clocks = new ItemStack[27];
        // add morning
        ItemStack morn = new ItemStack(347, 1);
        ItemMeta ing = morn.getItemMeta();
        ing.setDisplayName("Morning");
        ing.setLore(Arrays.asList(new String[]{"0 ticks", "6 AM"}));
        morn.setItemMeta(ing);
        clocks[0] = morn;
        // add midday
        ItemStack mid = new ItemStack(347, 1);
        ItemMeta day = mid.getItemMeta();
        day.setDisplayName("Midday");
        day.setLore(Arrays.asList(new String[]{"6000 ticks", "12 Noon"}));
        mid.setItemMeta(day);
        clocks[1] = mid;
        // add night
        ItemStack nig = new ItemStack(347, 1);
        ItemMeta ht = nig.getItemMeta();
        ht.setDisplayName("Night");
        ht.setLore(Arrays.asList(new String[]{"12000 ticks", "6 PM"}));
        nig.setItemMeta(ht);
        clocks[2] = nig;
        // add midnight
        ItemStack zero = new ItemStack(347, 1);
        ItemMeta hrs = zero.getItemMeta();
        hrs.setDisplayName("Midnight");
        hrs.setLore(Arrays.asList(new String[]{"18000 ticks", "12 PM"}));
        zero.setItemMeta(hrs);
        clocks[3] = zero;

        // add some clocks
        int c = 4;
        for (int i = 1000; i < 24000; i += 1000) {
            ItemStack is = new ItemStack(347, 1);
            ItemMeta im = is.getItemMeta();
            im.setDisplayName(types.get(c));
            List<String> lore = new ArrayList<String>();
            lore.add(i + " ticks");
            im.setLore(lore);
            is.setItemMeta(im);
            clocks[c] = is;
            c++;
        }
        return clocks;
    }

    public ItemStack[] getTerminal() {
        return terminal;
    }
}