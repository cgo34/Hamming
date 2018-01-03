/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hamming;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Geoffroy
 */
public class Entry {

    private int idEntry;
    private static int nextIdEntry = 1;
    
    public int couleurEntry;
    public int noyauxEntry;
    public int flagellesEntry;
    public int membraneEntry;
    private boolean haveCluster;
    private Cluster cluster;
    
   /* public Entry(int couleur, int noyaux, int flagelles, int membrane){
        idEntry = this.nextIdEntry;
        this.nextIdEntry++;
        this.couleurEntry = couleur;
        this.noyauxEntry = noyaux;
        this.flagellesEntry = flagelles;
        this.membraneEntry = membrane;
    }*/
    
    public Entry(int[] data){
        idEntry = this.nextIdEntry;
        this.nextIdEntry++;
        this.couleurEntry = data[0];
        this.noyauxEntry = data[1];
        this.flagellesEntry = data[2];
        this.membraneEntry = data[3];
        this.haveCluster = false;
    }
    
    public String toString(){
        return "Entry [ id : " + idEntry + " | couleur : " + couleurEntry + " | noyaux : " + noyauxEntry + " | flagelles : " + flagellesEntry + " | membrane : " + membraneEntry + " ]";
    }
    
    public String toValue(){
        return couleurEntry + " " + noyauxEntry + " " + flagellesEntry + " " + membraneEntry ;
    }
    
   public int getIdEntry(){
       return this.idEntry;
   }
    
    /**
     * @return the couleurEntry
     */
    public int getCouleurEntry() {
        return couleurEntry;
    }

    /**
     * @param couleurEntry the couleurEntry to set
     */
    public void setCouleurEntry(int couleurEntry) {
        this.couleurEntry = couleurEntry;
    }

    /**
     * @return the noyauxEntry
     */
    public int getNoyauxEntry() {
        return noyauxEntry;
    }

    /**
     * @param noyauxEntry the noyauxEntry to set
     */
    public void setNoyauxEntry(int noyauxEntry) {
        this.noyauxEntry = noyauxEntry;
    }

    /**
     * @return the flagellesEntry
     */
    public int getFlagellesEntry() {
        return flagellesEntry;
    }

    /**
     * @param flagellesEntry the flagellesEntry to set
     */
    public void setFlagellesEntry(int flagellesEntry) {
        this.flagellesEntry = flagellesEntry;
    }

    /**
     * @return the membraneEntry
     */
    public int getMembraneEntry() {
        return membraneEntry;
    }

    /**
     * @param membraneEntry the membranesEntry to set
     */
    public void setMembraneEntry(int membraneEntry) {
        this.membraneEntry = membraneEntry;
    }
    
    public int calculateHammingDistance(Entry entry) {
        int distance = 0;
        try {
            for (Field field : getClass().getFields()) {
                if (!field.get(this).equals(field.get(entry))) {
                  //  System.out.println(distance + " - ");
                    //System.out.println(field.get(entry));
                    distance++;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return distance;
    }
    
    public boolean haveCluster(){
        return this.haveCluster;
    }
    
    public Cluster getEntryCluster(){
        return this.cluster;
    }
    
    public void setEntryCluster(Cluster cluster){
        this.cluster = cluster;
        this.haveCluster = true;
    }
    
    public int calculateDistanceMaxWithCluster(Cluster cluster){        
        int distance = 0;
        ArrayList<Integer> listDistances = new ArrayList<Integer>();
        for (Entry e : cluster.entries) {            
                listDistances.add(calculateHammingDistance(e));
                distance = Collections.max(listDistances);
                
            
        }
        //System.out.println("calcul de la distance max avec le cluster " + cluster.getIdCluster() + " est " + distance);
        return distance;
    }
    
    public int calculateDistanceMinWithCluster(Cluster cluster){
        int distance = 0;
        ArrayList<Integer> listDistances = new ArrayList<Integer>();
        for (Entry e : cluster.entries) {            
                listDistances.add(calculateHammingDistance(e));              
                distance = Collections.min(listDistances);
                
            
        }
        //System.out.println("calcul de la distance min avec le cluster " + cluster.getIdCluster() + " est " + distance);
        return distance;
    }
    
    public int calculateDistanceWithCluster(Cluster cluster){
        int distance = 0;
        for(Entry e : cluster.entries){
            int newDistance = this.calculateHammingDistance(e);
            if(newDistance != 0){
                distance = newDistance;
            }
        }
        return distance;
    }
    
}
