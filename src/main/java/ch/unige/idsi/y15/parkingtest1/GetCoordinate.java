package ch.unige.idsi.y15.parkingtest1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class GetCoordinate
 */
public class GetCoordinate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetCoordinate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * 
	 *      url exemple: /GetCoordinate?lat=46.1948892&long=6.1398835&type=Handi
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String[] result_shortest = null;
		List<?> parkingList = null;

		// Get parameters in URL (latitude and longitude)
		double getLatitude = Double.parseDouble(request.getParameter("lat"));
		double getLongitude = Double.parseDouble(request.getParameter("long"));
		String getType = request.getParameter("type");

		System.out.println(getLatitude);
		System.out.println(getLongitude);
		System.out.println(getType);

		if (getType.equals("Handi")) {
			System.out.println("ParkingHandi");
			parkingList = ParkingHandi.findAllParkingHandis();
			
		}
		if (getType.equals("Publique")) {
			System.out.println("ParkingPublique");
			parkingList = ParkingPublique.findAllParkingPubliques();
		}
		if (getType.equals("Voie")) {
			System.out.println("ParkingVoie");
			parkingList = ParkingVoie.findAllParkingVoies();
		}
		
		//Call shortest methode calculation
		result_shortest = gps2m(getLatitude, getLongitude, parkingList, getType);

		
		// Print XML response
		response.setContentType("application/xml");
		PrintWriter writer = response.getWriter();
		writer.println("<?xml version=\"1.0\"?>");
		writer.println("<parking>");
		
		writer.println("<place name='" + result_shortest[0] + "'>");
		writer.println("<type>"+getType+"</type>");
		writer.println("<latitude>" + result_shortest[1] + "</latitude>");
		writer.println("<longitude>" + result_shortest[2] + "</longitude>");

		writer.println("</place>");
		writer.println("</parking>");
		writer.flush();

	}

	/**
	 * @param lat_origin
	 * @param lng_origin
	 * @param parkingList
	 * @return
	 */
	private static String[] gps2m(double lat_origin, double lng_origin, List<?> parkingList, String type) {
		double pk = (double) (180 / 3.14169);
		double a1 = lat_origin / pk;
		double a2 = lng_origin / pk;
		double shortest = 1000000.0; //initialise shortest le plus grand possible
		
		double b2 = 0;
		double b1 = 0;
		double tt = 0;
		String name = null;
		String latitude = null;
		String longitude = null;

		String short_name = new String();
		String short_lat = new String();
		String short_long = new String();
		


		
		// Create an iterator to parse all Parking Object
		// Iterator<ParkingHandi> iterator = parkingList.iterator();
		for (Iterator<?> iterator = parkingList.iterator(); iterator
				.hasNext();) {
			if (type.equals("Handi")){
				ParkingHandi parkingElement = (ParkingHandi) iterator.next();
				b1 = Double.parseDouble(parkingElement.getLatitude()) / pk;
				b2 = Double.parseDouble(parkingElement.getLongitude()) / pk;
				name = parkingElement.getName();
				latitude = parkingElement.getLatitude();
				longitude = parkingElement.getLongitude();
				System.out.println(name + " - " + latitude + " - " + longitude);
			}
			if (type.equals("Publique")){
				ParkingPublique parkingElement = (ParkingPublique) iterator.next();
				b1 = Double.parseDouble(parkingElement.getLatitude()) / pk;
				b2 = Double.parseDouble(parkingElement.getLongitude()) / pk;
				name = parkingElement.getName();
				latitude = parkingElement.getLatitude();
				longitude = parkingElement.getLongitude();
			}
			if (type.equals("Voie")){
				ParkingVoie parkingElement = (ParkingVoie) iterator.next();
				b1 = Double.parseDouble(parkingElement.getLatitude()) / pk;
				b2 = Double.parseDouble(parkingElement.getLongitude()) / pk;
				name = parkingElement.getName();
				latitude = parkingElement.getLatitude();
				longitude = parkingElement.getLongitude();
			}

			
			// Calculate distance between origin point and all other points	
			double t1 = (double) Math.cos(a1) * (double) Math.cos(a2)
					* (double) Math.cos(b1) * (double) Math.cos(b2);
			double t2 = (double) Math.cos(a1) * (double) Math.sin(a2)
					* (double) Math.cos(b1) * (double) Math.sin(b2);
			double t3 = (double) Math.sin(a1) * (double) Math.sin(b1);
			tt = Math.acos(t1 + t2 + t3) * 6366000;

			// If next point is shortest than older shortest point, keep it
			if (tt < shortest) {
				System.out.println("*****shortest parcours: " + tt + " - " + name);
				shortest = tt;
				short_name = name;
				short_lat = latitude;
				short_long = longitude;
			}
		}
		
		
		// Create a array with data of the shortest point and return it
		System.out.println("Final shortest location is: " + short_name + " - " + short_lat + " - " + short_long);
		String[] result_shortest = { short_name, short_lat, short_long };

		return result_shortest;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
