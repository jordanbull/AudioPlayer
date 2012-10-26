/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull;

import java.util.ArrayList;

/**
 *
 * @author Jordan
 */
public abstract class GUIController {
    static ArrayList<Deck> decks = new ArrayList<Deck>();
    
    /**
     * The first method called. Used to initialize any layout
     * specific content.
     */
    protected abstract void begin();
    
    /**
     * Adds a deck to the application so it can control the audio and 
     * have functionality.
     * @param deck the Deck being added to the application for control
     * @returns the deck number to use to get the appropriate deck
     */
    public static int addDeckControl(Deck deck) {
        decks.add(deck);
        return (decks.size()-1);
    }
    
}
