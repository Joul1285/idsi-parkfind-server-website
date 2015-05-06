package ch.unige.idsi.y15.parkingtest1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

/**
 * Servlet implementation class Parser
 */
public class Parser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Parser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("process start...");
		
//		URL u = new URL("http://ge.ch/ags1/rest/services/Mobilite/MapServer/33/query?text=&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=1%3D1&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=&f=kmz");
//		URLConnection uc = u.openConnection();
//		uc.connect();
//		InputStream in = uc.getInputStream();
//		FileOutputStream out = new FileOutputStream("test.kmz");
//		final int BUF_SIZE = 1 << 8;
//		byte[] buffer = new byte[BUF_SIZE];
//		int bytesRead = -1;
//		while((bytesRead = in.read(buffer)) > -1) {
//		    out.write(buffer, 0, bytesRead);
//		}
//		in.close();
//		out.close();
//		System.out.println("process end");
		
		final Kml kml = Kml.unmarshal(new File("doc.kml"));
		System.out.println("process run");
		// System.out.println("process run2");
		final Document document = (Document) kml.getFeature();
		System.out.println(document.getName());

		List<Feature> t = document.getFeature();
        for(Object o : t){
            Folder f = (Folder)o;
            List<Feature> tg = f.getFeature();
            for(Object ftg : tg){
                Placemark g = (Placemark) ftg;
                System.out.println(g.getName());
                
//                //create new object parkinghandi
//                ParkingHandi test = new ParkingHandi();
//                test.setName(g.getName());
//                request.setAttribute("ParkingHandi", test);
                
                
                
                Point point = (Point) g.getGeometry();
                List<Coordinate> coordinates = point.getCoordinates(); 
                for (Coordinate c : coordinates) 
                { 
                    System.out.println(c.getLatitude()); 
                    System.out.println(c.getLongitude()); 
                    
                    System.out.println("load loader function");    
                    onApplicationEvent(g.getName(), String.valueOf(c.getLatitude()), String.valueOf(c.getLongitude()));
                }  
                
                
            }
            
//            this.getServletContext()
//            	.getRequestDispatcher("/WEB-INF/views/index.jpsx")
//            	.forward(request, response);
        }
	   
    
	}
	
//	void ParseKMZ(){
//		final Kml kml = Kml.unmarshal(new File("test.kmz"));
//		final Placemark placemark = (Placemark) kml.getFeature();
//		Point point = (Point) placemark.getGeometry();
//		List<Coordinate> coordinates = point.getCoordinates();
//		for (Coordinate coordinate : coordinates) {
//			System.out.println(coordinate.getLatitude());
//			System.out.println(coordinate.getLongitude());
//			System.out.println(coordinate.getAltitude());
//		}
//	}

    @Transactional
    public void onApplicationEvent(String name, String latitude, String longitude) {
        System.out.println("before: " + ParkingHandi.countParkingHandis());
        ParkingHandi parkingHandi = new ParkingHandi();
        parkingHandi.persist();
        parkingHandi.setName(name);
        parkingHandi.setLatitude(latitude);
        parkingHandi.setLongitude(longitude);
        
        System.out.println("after: " + ParkingHandi.countParkingHandis());
    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
