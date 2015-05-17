package ch.unige.idsi.y15.parkingtest1;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ParkingPublique {

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