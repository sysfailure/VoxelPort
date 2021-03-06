/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thevoxelbox.voxelport;

//import com.thevoxelbox.bukkit.port.NPC.PortNPC;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Voxel
 */
public class PortContainer {

    private ArrayList<NewPort> collection = new ArrayList<NewPort>();

    public PortContainer(NewPort n) {
        collection.add(n);
    }

    public void put(NewPort n) {
        collection.add(n);
    }

    public boolean remove(NewPort n) {
        collection.remove(n);
        return collection.isEmpty();
    }

    public void check(Player p, Location l) {
        if (PortTick.tickets.containsKey(p)) {
            return;
        }
        if (p.getItemInHand().getTypeId() == 337) {
            return;
        }
        for (NewPort n : collection) {
            if (n.insideZone(l)) {
                if (p.getItemInHand().getTypeId() == 266 && VoxelPort.admns.contains(p.getName())) {
                    n.instaPort(p, true);
                    return;
                }
                if (n.ticket()) {
                    ItemStack i = p.getItemInHand();
                    //n.turnNpcToPlayer(l);
                    if (i.getTypeId() == PortManager.TICKETID) {
                        if (n.instant()) {
                            n.instaPort(p, false);
                            p.getInventory().clear(p.getInventory().getHeldItemSlot());
                            return;
                        }
                        if (n.isPortActivated()) {
                            n.welcomePlayer(p);

                            p.getInventory().clear(p.getInventory().getHeldItemSlot());

                            PortTick.registerTicket(p, n);
                            return;
                        }
                    }
                } else {
                    if (n.instant()) {
                        n.instaPort(p, false);
                        return;
                    }
                    if (n.isPortActivated()) {
                        n.welcomePlayer(p);

                        PortTick.registerTicket(p, n);
                        return;
                    }
                }
            }
        }
    }

    /* public PortNPC matchNpcId(int id) {
     * for (newPort n : collection) {
     * PortNPC pn = n.matchNpcId(id);
     * if (pn != null) {
     * return pn;
     * }
     * }
     * return null;
     * }
     *
     */
    public NewPort getPort(Location l) {
        for (NewPort n : collection) {
            if (n.insideZone(l)) {
                return n;
            }
        }
        return null;
    }
}
