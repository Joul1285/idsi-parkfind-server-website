package ch.unige.idsi.y15.parkingtest1;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/map/**")
@Controller
public class Map {



	@RequestMapping(method = RequestMethod.GET, value = "/map/{type}")
	public String index(@PathVariable String type, final ModelMap pModel) {
		System.out.println(type);
		String marker = generateMarker(type);
		System.out.println(marker);
		pModel.addAttribute("markers", marker);
		return "map/index";
	}

	public String generateMarker(String type) {
		String marker = "";
		System.out.println("generateMarker");
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
