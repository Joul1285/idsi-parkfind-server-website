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

	@RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {

    }
    


    @RequestMapping
    public String index(final ModelMap pModel) {
		
		String marker = generateMarker();
		System.out.println(marker);
		pModel.addAttribute("markers", marker);
        return "map/index";
    }
    
    public String generateMarker(){
    	String marker = "";
    	System.out.println("generateMarker");
    	List<ParkingHandi> parkingList = ParkingHandi.findAllParkingHandis();
    	for (Iterator<ParkingHandi> iterator = parkingList.iterator(); iterator
				.hasNext();) {
    		ParkingHandi parkingElement = (ParkingHandi) iterator.next();
    		System.out.println(parkingElement.getName());
    		System.out.println(parkingElement.getLatitude());
    		System.out.println(parkingElement.getLongitude());
    		
    		marker += "var marker = new google.maps.Marker({position: new google.maps.LatLng("+parkingElement.getLatitude()+","+parkingElement.getLongitude()+"), map: map, title: 'test'}); \n";
    	}
//    	marker += "var marker = new google.maps.Marker({position: new google.maps.LatLng(46.2050295,6.1440885), map: map, title: 'test'});";
//    	marker += "var marker = new google.maps.Marker({position: new google.maps.LatLng(46.2051295,6.1441885), map: map, title: 'test'});";
    	
		return marker;
    	
    }
}
