package net.frozenchaos.TirNaNog.explorer;

import net.frozenchaos.TirNaNog.data.ModuleConfig;

/*
The telephone is responsible for ringing other TirNaNog devices to tell them about this modules IP.
Additionally it is responsible for being rang and being told other modules their IP if they've changed.

So the singular concern is synchronizing IP addresses between TirNaNog modules.

A module knows what the last IP is that they've broadcast through the Broadcaster class.
Any module that hasn't responded through the Telephone is considered to know this module by its old IP.
 */
public class Telephone {
    public Telephone() {
    }

    public void destroyGracefully() {

    }

    public void addContactToRing(ModuleConfig moduleConfig) {

    }
}
