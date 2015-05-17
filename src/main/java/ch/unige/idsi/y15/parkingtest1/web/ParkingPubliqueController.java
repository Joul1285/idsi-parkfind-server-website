package ch.unige.idsi.y15.parkingtest1.web;
import ch.unige.idsi.y15.parkingtest1.ParkingPublique;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/parkingpubliques")
@Controller
@RooWebScaffold(path = "parkingpubliques", formBackingObject = ParkingPublique.class)
public class ParkingPubliqueController {
}
