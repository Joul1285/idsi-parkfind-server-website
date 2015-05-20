package ch.unige.idsi.y15.parkfind_roo;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
* Recieve parking's type in parameter (if no parameter just display the map empty)
* Get from java object all parking points, load a google map and put in parameter all points on map
*
* @author  Ludmila Banaru
* @author  Julien Burn
* @version 1.0
* @since   2015-05-20 
*/
@RequestMapping("/map/**")
@Controller
public class Map {

	/**
	 * Get type on GET parameter and return the map with markers points generated
	 * @param type
	 * @param pModel
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/map/{type}")
	public String index(@PathVariable String type, final ModelMap pModel) {
		System.out.println(type);
		String marker = generateMarker(type);
		System.out.println(marker);
		pModel.addAttribute("markers", marker);
		return "map/index";
	}

	/**
	 * Create all markers from a parking's type.
	 * Return all markers as google map javascript markers
	 * 
	 * @param type
	 * @return javascript String code
	 */
	public String generateMarker(String type) {
		String marker = "";
		System.out.println("generateMarker");
		
		//Generate markers depending of the type
		if (type.equals("handi")) {
			List<ParkingHandi> parkingList = ParkingHandi.findAllParkingHandis();
			for (Iterator<ParkingHandi> iterator = parkingList.iterator(); iterator
					.hasNext();) {
				ParkingHandi parkingElement = (ParkingHandi) iterator.next();
				marker += "var marker = new google.maps.Marker({position: new google.maps.LatLng("
						+ parkingElement.getLatitude()
						+ ","
						+ parkingElement.getLongitude()
						+ "), map: map, title: 'parking Handi'}); \n";
			}
		}
		else if (type.equals("publique")) {
			List<ParkingPublique> parkingList = ParkingPublique.findAllParkingPubliques();
			for (Iterator<ParkingPublique> iterator = parkingList.iterator(); iterator.hasNext();) {
				ParkingPublique parkingElement = (ParkingPublique) iterator.next();
				marker += "var marker = new google.maps.Marker({position: new google.maps.LatLng("
						+ parkingElement.getLatitude()
						+ ","
						+ parkingElement.getLongitude()
						+ "), map: map, title: 'parking Publique'}); \n";
			}
		}
		else if (type.equals("voie")) {
			List<ParkingVoie> parkingList = ParkingVoie.findAllParkingVoies();
			for (Iterator<ParkingVoie> iterator = parkingList.iterator(); iterator.hasNext();) {
				ParkingVoie parkingElement = (ParkingVoie) iterator.next();
				marker += "var marker = new google.maps.Marker({position: new google.maps.LatLng("
						+ parkingElement.getLatitude()
						+ ","
						+ parkingElement.getLongitude()
						+ "), map: map, title: 'parking Voie'}); \n";
			}
		}
		return marker;

	}
}
