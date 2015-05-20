package ch.unige.idsi.y15.parkingtest1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LineString;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

/**
* Get and store KML files from sitg services about "parking handicapé", "parking public" and "parking voie public".
* Parse those files to retrieve latitude and longitude point.
* Create new object Parkings with latitude and longitude points.
* 
*
* @author  Ludmila Banaru
* @author  Julien Burn
* @version 1.0
* @since   2015-05-20 
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
	 * Initialize urls needed and sent it to GetKMZ function
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		System.out.println("process start...");

		URL urlHandi = new URL(
				"http://ge.ch/ags1/rest/services/Mobilite/MapServer/33/query?text=&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=1%3D1&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=&f=kmz");
		getKMZ(urlHandi, "Handi");

		URL urlPublique = new URL(
				"http://ge.ch/ags1/rest/services/Mobilite/MapServer/32/query?text=&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=1%3D1&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=&f=kmz");
		getKMZ(urlPublique, "Publique");

		URL urlVoie = new URL(
				"http://ge.ch/ags1/rest/services/Mobilite/MapServer/36/query?text=&geometry=&geometryType=esriGeometryPoint&inSR=&spatialRel=esriSpatialRelIntersects&relationParam=&objectIds=&where=1%3D1&time=&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&maxAllowableOffset=&outSR=&outFields=&f=kmz");
		getKMZ(urlVoie, "Voie");

		System.out.println("process end");
	}

	/**
	 * 
	 * Get KMZ file from URL in parameter, store the file depending of the type (String) 
	 * 
	 * @param u
	 * @param type
	 * @throws IOException
	 */
	public static void getKMZ(URL u, String type) throws IOException {
		URLConnection uc = u.openConnection();
		uc.connect();
		InputStream in = uc.getInputStream();
		File file = new File(type + ".kmz");
		OutputStream out = new FileOutputStream(file);

		final int BUF_SIZE = 1 << 8;
		byte[] buffer = new byte[BUF_SIZE];
		int bytesRead = -1;
		while ((bytesRead = in.read(buffer)) > -1) {
			out.write(buffer, 0, bytesRead);
		}
		in.close();
		out.close();

		parseKMZ(file, type);
	}

	/**
	 * Parse KMZ file depending of the type.
	 * Get latitude and longitude points and send it to newParking function
	 * 
	 * @param file
	 * @param type
	 * @throws IOException
	 */
	public static void parseKMZ(File file, String type) throws IOException {

		// Open and parse KML file
		final Kml[] kml = Kml.unmarshalFromKmz(file);
		System.out.println("process run");
		final Document document = (Document) kml[0].getFeature();
		System.out.println(document.getName());

		List<Feature> t = document.getFeature();
		for (Object o : t) {
			Folder f = (Folder) o;
			List<Feature> tg = f.getFeature();
			for (Object ftg : tg) {

				Placemark g = (Placemark) ftg;

				List<Coordinate> coordinates = null;

				// check if the node under placemark is MultiGeometry
				if ((g.getGeometry() instanceof MultiGeometry)) {

					MultiGeometry mpg = (MultiGeometry) g.getGeometry();
					List<Geometry> gmList = mpg.getGeometry();
					// Get all the geometries and traverse them one by one
					for (Geometry geoItr : gmList) {
						LineString multiGeoPoly = (LineString) geoItr;
						coordinates = multiGeoPoly.getCoordinates();

					}

				}
				// Else parse only point
				else {
					Point point = (Point) g.getGeometry();
					coordinates = point.getCoordinates();

				}
				for (Coordinate c : coordinates) {

					System.out.println(c.getLatitude());
					System.out.println(c.getLongitude());
					//call newParking function with parking's name, latitude and longitude points 
					newParking(type, g.getName(),
							String.valueOf(c.getLatitude()),
							String.valueOf(c.getLongitude()));
				}

			}
		}
	}


	/**
	 * Store new java object depending of the type with the name, latitude and longitude points recieve in parameter
	 * 
	 * @param type
	 * @param name
	 * @param latitude
	 * @param longitude
	 */
	@Transactional
	public static void newParking(String type, String name, String latitude,
			String longitude) {
		//store different type of parking depending of type recieve
		if (type.equals("Handi")) {
			//count number of parking before and after for debuging
			System.out.println("before: " + ParkingHandi.countParkingHandis());
			ParkingHandi newObject = new ParkingHandi();
			newObject.setName(name);
			newObject.setLatitude(latitude);
			newObject.setLongitude(longitude);
			newObject.persist();
			//count number of parking before and after for debuging
			System.out.println("after: " + ParkingHandi.countParkingHandis());
		}
		if (type.equals("Publique")) {
			System.out.println("before: " + ParkingPublique.countParkingPubliques()); 
			ParkingPublique newObject = new ParkingPublique();
			newObject.setName(name);
			newObject.setLatitude(latitude);
			newObject.setLongitude(longitude);
			newObject.persist();
			System.out.println("after: " + ParkingPublique.countParkingPubliques());
		}
		if (type.equals("Voie")) {
			System.out.println("before: " + ParkingVoie.countParkingVoies());
			ParkingVoie newObject = new ParkingVoie();
			newObject.setName(name);
			newObject.setLatitude(latitude);
			newObject.setLongitude(longitude);
			newObject.persist();
			System.out.println("after: " + ParkingVoie.countParkingVoies());
		}

		
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
