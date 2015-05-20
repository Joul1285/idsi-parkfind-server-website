package ch.unige.idsi.y15.parkingtest1;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
* Recieve parking's type in parameter (if no parameter just display the map empty)
* Get from java object all parking points, load a google map and put in parameter all points on map
*
* @author  Ludmila Banaru
* @author  Julien Burn
* @version 1.0
* @since   2015-05-20 
*/

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ParkingHandi {

    /**
     */
    private String name;

    /**
     */
    private String latitude;

    /**
     */
    private String longitude;
}
