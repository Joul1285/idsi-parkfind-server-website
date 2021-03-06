package ch.unige.idsi.y15.parkfind_roo.web;
import ch.unige.idsi.y15.parkfind_roo.ParkingHandi;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/parkinghandis")
@Controller
@RooWebScaffold(path = "parkinghandis", formBackingObject = ParkingHandi.class)
public class ParkingHandiController {
}
