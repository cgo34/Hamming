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
    
    public Cluster calculateDistanceWithClusters(Cluster cluster1, Cluster cluster2){
        /*int distance = 0;
        ArrayList<Integer> listDistances = new ArrayList<Integer>();
        for(Entry e : cluster1.entries){
            
            listDistances.add(this.calculateHammingDistance(e));              
            distance = Collections.max(listDistances);
        }
        for(Entry e : cluster2.entries){
            listDistances.add(this.calculateHammingDistance(e));              
            distance = Collections.min(listDistances);
        }*/
        Cluster finalCluster = cluster1;
        boolean reponse = false;
        for(int i = 0; i < cluster1.count(); i++){
            for(int j = 0; j < cluster2.count(); j++){
                for(int k = i + 1; k < cluster1.count(); k++){
                    //System.out.println("i : " +i+ " | k : " +k+ " | j : " +j);                    

                    if(this.calculateHammingDistance(cluster1.getEntries().get(i)) < this.calculateHammingDistance(cluster2.getEntries().get(j))){
                        //System.out.println("Distance entre " + this.idEntry + " et " + cluster1.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster1.getEntries().get(i)));
                        //System.out.println("Distance entre " + this.idEntry + " et " + cluster2.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster2.getEntries().get(j)));
                        //System.out.println("Distance entre " + this.idEntry + " et " + cluster1.getEntries().get(k).idEntry + " = " + this.calculateHammingDistance(cluster1.getEntries().get(k)));
                        //System.out.println("Distance entre " + this.idEntry + " et " + cluster1.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster1.getEntries().get(i)) + " < Distance entre " + this.idEntry + " et " + cluster2.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster2.getEntries().get(j)));
                        //System.out.println("VRAI");
                        reponse = true;
                        //cluster1.addEntry(this);
                        finalCluster = cluster1;
                        //System.out.println("-----------------------------------------------");

                        //System.out.println("Distance entre " + cluster1.getEntries().get(i).idEntry + " et " + cluster2.getEntries().get(j).idEntry + " = " + cluster1.getEntries().get(i).calculateHammingDistance(cluster2.getEntries().get(j)));
                        /*if(this.calculateHammingDistance(cluster1.getEntries().get(i)) < cluster1.getEntries().get(i).calculateHammingDistance(cluster2.getEntries().get(j))){
                            System.out.println("Distance entre " + this.getIdEntry() + " et " + cluster1.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster1.getEntries().get(i)) + " \nDistance entre " + cluster1.getEntries().get(i).getIdEntry() + " et " + cluster2.getEntries().get(j).getIdEntry() + " = " + cluster1.getEntries().get(i).calculateHammingDistance(cluster2.getEntries().get(j)));
                            System.out.println("Distance entre " + this.getIdEntry() + " et " + cluster1.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster1.getEntries().get(i)) + " < Distance entre " + this.getIdEntry() + " et " + cluster2.getEntries().get(j).getIdEntry() + " = " + this.calculateHammingDistance(cluster2.getEntries().get(j)));
                            System.out.println("VRAI");
                            reponse = true; 
                        }else{
                            System.out.println("Distance entre " + this.getIdEntry() + " et " + cluster1.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster1.getEntries().get(i)) + " \nDistance entre " + cluster1.getEntries().get(i).getIdEntry() + " et " + cluster2.getEntries().get(j).getIdEntry() + " = " + cluster1.getEntries().get(i).calculateHammingDistance(cluster2.getEntries().get(j)));
                            System.out.println("Distance entre " + this.getIdEntry() + " et " + cluster1.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster1.getEntries().get(i)) + " < Distance entre " + cluster1.getEntries().get(i).getIdEntry() + " et " + cluster2.getEntries().get(j).getIdEntry() + " = " + cluster1.getEntries().get(i).calculateHammingDistance(cluster2.getEntries().get(j)));
                            System.out.println("FAUX");
                            reponse = false;
                        }*/


                    }else{
                        //cluster2.addEntry(this);
                        finalCluster = cluster2;
                        //System.out.println("FAUX");
                        reponse = false;
                    }
                
                }
               //System.out.println("Distance entre " + this.idEntry + " et " + cluster1.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster1.getEntries().get(i)) + " < Distance entre " + this.idEntry + " et " + cluster2.getEntries().get(i).idEntry + " = " + this.calculateHammingDistance(cluster2.getEntries().get(j))); 
            }
        }
        
        //System.out.println("Bonne Réponse : " + reponse);
        return finalCluster;
    }
    
    public void check(Cluster cluster1, Cluster cluster2){
        for(int i = 0; i < cluster1.count(); i++){
            for(int j = i + 1; j < cluster1.count(); j++){
                for(int k = 0; j < cluster2.count(); j++){
                    if(cluster1.getEntries().get(i).calculateHammingDistance(cluster1.getEntries().get(j)) < cluster1.getEntries().get(i).calculateHammingDistance(cluster2.getEntries().get(k))){
                        System.out.println(cluster1.getEntries().get(i).getIdEntry() + " et " + cluster1.getEntries().get(j).getIdEntry() + " = " + cluster1.getEntries().get(i).calculateHammingDistance(cluster1.getEntries().get(j)) );
                        System.out.println("true");

                        //System.out.println("Distance entre " + cluster1.getEntries().get(i).idEntry + " et " + cluster2.getEntries().get(j).idEntry + " = " + cluster1.getEntries().get(i).calculateHammingDistance(cluster2.getEntries().get(j)));

                    }else{
                        System.out.println(cluster1.getEntries().get(i).getIdEntry() + " et " + cluster1.getEntries().get(j).getIdEntry() + " = " + cluster1.getEntries().get(i).calculateHammingDistance(cluster1.getEntries().get(j)) );
                        System.out.println("false");
                    }
                }
                
                
            }
        }
    }
    
}
