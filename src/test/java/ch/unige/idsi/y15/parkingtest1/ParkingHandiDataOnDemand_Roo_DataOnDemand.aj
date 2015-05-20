// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.y15.parkingtest1;

import ch.unige.idsi.y15.parkfind_roo.ParkingHandi;
import ch.unige.idsi.y15.parkingtest1.ParkingHandiDataOnDemand;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Component;

privileged aspect ParkingHandiDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ParkingHandiDataOnDemand: @Component;
    
    private Random ParkingHandiDataOnDemand.rnd = new SecureRandom();
    
    private List<ParkingHandi> ParkingHandiDataOnDemand.data;
    
    public ParkingHandi ParkingHandiDataOnDemand.getNewTransientParkingHandi(int index) {
        ParkingHandi obj = new ParkingHandi();
        setLatitude(obj, index);
        setLongitude(obj, index);
        setName(obj, index);
        return obj;
    }
    
    public void ParkingHandiDataOnDemand.setLatitude(ParkingHandi obj, int index) {
        String latitude = "latitude_" + index;
        obj.setLatitude(latitude);
    }
    
    public void ParkingHandiDataOnDemand.setLongitude(ParkingHandi obj, int index) {
        String longitude = "longitude_" + index;
        obj.setLongitude(longitude);
    }
    
    public void ParkingHandiDataOnDemand.setName(ParkingHandi obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }
    
    public ParkingHandi ParkingHandiDataOnDemand.getSpecificParkingHandi(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        ParkingHandi obj = data.get(index);
        Long id = obj.getId();
        return ParkingHandi.findParkingHandi(id);
    }
    
    public ParkingHandi ParkingHandiDataOnDemand.getRandomParkingHandi() {
        init();
        ParkingHandi obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return ParkingHandi.findParkingHandi(id);
    }
    
    public boolean ParkingHandiDataOnDemand.modifyParkingHandi(ParkingHandi obj) {
        return false;
    }
    
    public void ParkingHandiDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = ParkingHandi.findParkingHandiEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'ParkingHandi' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<ParkingHandi>();
        for (int i = 0; i < 10; i++) {
            ParkingHandi obj = getNewTransientParkingHandi(i);
            try {
                obj.persist();
            } catch (final ConstraintViolationException e) {
                final StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    final ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
                }
                throw new IllegalStateException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
