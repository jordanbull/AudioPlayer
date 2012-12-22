/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jbull.audioplayer;

import java.net.URL;

/**
 *
 * @author jordan
 */
public interface Plugin {
    
    /**
     * This is the entrance point to the plugin. From this method all aspects of
     * the plugin should start.
     */
    public void onStart();
    
    /**
     * Checks the repository for updates to this specific plugin. It returns the
     * {@link URL} to the new .jar file for this plugin to be installed or null if
     * there are no updates detected for this plugin. the new .jar file must
     * have the same name as the existing .jar file for this plugin.
     * @return  the {@link URL} to the new jar file for this plugin
     */
    public URL checkUpdates();
}
