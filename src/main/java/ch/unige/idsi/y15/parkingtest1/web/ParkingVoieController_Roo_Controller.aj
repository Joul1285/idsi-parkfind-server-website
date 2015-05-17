// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.y15.parkingtest1.web;

import ch.unige.idsi.y15.parkingtest1.ParkingVoie;
import ch.unige.idsi.y15.parkingtest1.web.ParkingVoieController;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ParkingVoieController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String ParkingVoieController.create(@Valid ParkingVoie parkingVoie, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, parkingVoie);
            return "parkingvoies/create";
        }
        uiModel.asMap().clear();
        parkingVoie.persist();
        return "redirect:/parkingvoies/" + encodeUrlPathSegment(parkingVoie.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String ParkingVoieController.createForm(Model uiModel) {
        populateEditForm(uiModel, new ParkingVoie());
        return "parkingvoies/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String ParkingVoieController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("parkingvoie", ParkingVoie.findParkingVoie(id));
        uiModel.addAttribute("itemId", id);
        return "parkingvoies/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String ParkingVoieController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("parkingvoies", ParkingVoie.findParkingVoieEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) ParkingVoie.countParkingVoies() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("parkingvoies", ParkingVoie.findAllParkingVoies(sortFieldName, sortOrder));
        }
        return "parkingvoies/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String ParkingVoieController.update(@Valid ParkingVoie parkingVoie, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, parkingVoie);
            return "parkingvoies/update";
        }
        uiModel.asMap().clear();
        parkingVoie.merge();
        return "redirect:/parkingvoies/" + encodeUrlPathSegment(parkingVoie.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String ParkingVoieController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, ParkingVoie.findParkingVoie(id));
        return "parkingvoies/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String ParkingVoieController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        ParkingVoie parkingVoie = ParkingVoie.findParkingVoie(id);
        parkingVoie.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/parkingvoies";
    }
    
    void ParkingVoieController.populateEditForm(Model uiModel, ParkingVoie parkingVoie) {
        uiModel.addAttribute("parkingVoie", parkingVoie);
    }
    
    String ParkingVoieController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}