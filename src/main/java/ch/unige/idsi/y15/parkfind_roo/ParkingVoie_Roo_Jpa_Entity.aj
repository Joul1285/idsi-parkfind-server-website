// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.y15.parkfind_roo;

import ch.unige.idsi.y15.parkfind_roo.ParkingVoie;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect ParkingVoie_Roo_Jpa_Entity {
    
    declare @type: ParkingVoie: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long ParkingVoie.id;
    
    @Version
    @Column(name = "version")
    private Integer ParkingVoie.version;
    
    public Long ParkingVoie.getId() {
        return this.id;
    }
    
    public void ParkingVoie.setId(Long id) {
        this.id = id;
    }
    
    public Integer ParkingVoie.getVersion() {
        return this.version;
    }
    
    public void ParkingVoie.setVersion(Integer version) {
        this.version = version;
    }
    
}
