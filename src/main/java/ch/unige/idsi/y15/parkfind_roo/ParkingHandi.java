package ch.unige.idsi.y15.parkfind_roo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
* Parking Handicapés, generate with Spring Roo
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
