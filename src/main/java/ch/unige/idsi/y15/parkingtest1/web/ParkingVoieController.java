package ch.unige.idsi.y15.parkingtest1.web;
import ch.unige.idsi.y15.parkingtest1.ParkingVoie;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/parkingvoies")
@Controller
@RooWebScaffold(path = "parkingvoies", formBackingObject = ParkingVoie.class)
public class ParkingVoieController {
}
