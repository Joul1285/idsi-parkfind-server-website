// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ch.unige.idsi.y15.parkfind_roo;

import ch.unige.idsi.y15.parkfind_roo.ParkingPublique;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

privileged aspect ParkingPublique_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager ParkingPublique.entityManager;
    
    public static final List<String> ParkingPublique.fieldNames4OrderClauseFilter = java.util.Arrays.asList("name", "latitude", "longitude");
    
    public static final EntityManager ParkingPublique.entityManager() {
        EntityManager em = new ParkingPublique().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ParkingPublique.countParkingPubliques() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ParkingPublique o", Long.class).getSingleResult();
    }
    
    public static List<ParkingPublique> ParkingPublique.findAllParkingPubliques() {
        return entityManager().createQuery("SELECT o FROM ParkingPublique o", ParkingPublique.class).getResultList();
    }
    
    public static List<ParkingPublique> ParkingPublique.findAllParkingPubliques(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ParkingPublique o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ParkingPublique.class).getResultList();
    }
    
    public static ParkingPublique ParkingPublique.findParkingPublique(Long id) {
        if (id == null) return null;
        return entityManager().find(ParkingPublique.class, id);
    }
    
    public static List<ParkingPublique> ParkingPublique.findParkingPubliqueEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ParkingPublique o", ParkingPublique.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<ParkingPublique> ParkingPublique.findParkingPubliqueEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM ParkingPublique o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, ParkingPublique.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void ParkingPublique.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ParkingPublique.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ParkingPublique attached = ParkingPublique.findParkingPublique(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ParkingPublique.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ParkingPublique.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ParkingPublique ParkingPublique.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ParkingPublique merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
