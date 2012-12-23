package jbull.audioplayer;

import java.net.URL;


/**
 *
 * @author jordan
 */
interface Component {
    
    /**
     * Returns the FXML document associated with the component.
     * @return  the FXML document associated with the component
     */
    URL getFXML();
}
