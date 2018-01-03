/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hamming;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Geoffroy
 */
public class Cluster {
    
    public List<Entry> entries;
    private int idCluster;
    private static int nextIdCluster = 1;
    
    public Cluster() {
        entries = new ArrayList<>();
        idCluster = this.nextIdCluster;
        this.nextIdCluster++;
    }
    
    /**
     * @return the idCluster
     */
    public int getIdCluster() {
        return idCluster;
    }

    /**
     * @param idCluster the idCluster to set
     */
    public void setIdCluster(int idCluster) {
        this.idCluster = idCluster;
    }
    
    public List<Entry> getEntries() {
        return entries;
    }

    public void setData(List<Entry> entries) {
        this.entries = entries;
    }
    
    public Entry addEntry(Entry entry) {
       
        if (entries.add(entry)) {
            return entry;
        }
        return null;
    }

    public Entry removeEntry(int id) {
        return entries.remove(id);
    }
    
    public boolean isEmpty(){
        if(entries.size() != 0){
            return false;
        }
        return true;
    }
    
    public int getDistanceMin() {
        int distance = 0;
        ArrayList<Integer> listDistances = new ArrayList<Integer>();        
        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {  
                listDistances.add(entries.get(i).calculateHammingDistance(entries.get(j)));
                distance = Collections.min(listDistances);
            }
        }
        return distance;
    }
    
    public int getDistanceMax() {
        int distance = 0;
        ArrayList<Integer> listDistances = new ArrayList<Integer>();
        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {               
                listDistances.add(entries.get(i).calculateHammingDistance(entries.get(j)));
                distance = Collections.max(listDistances);
            }
        }
        System.out.println(distance);
        return distance;
    }
    
    public boolean isExist(int idEntry){
        for(Entry entry : entries){
            if(entry.getIdEntry() == idEntry){
                return true;
            }
        }
        return false;
    }
    
    public void sort(Entry entry, int distance, Cluster cluster){
        for(Entry e : entries){
            if(entry.calculateHammingDistance(e) <= distance){
                entries.remove(e.getIdEntry());
                cluster.entries.add(e);
            }
        }
        /*
        for(int i = 0; i < entries.size(); i++){
            for(int j = i + 1; j < entries.size(); j++){
                if(entries.get(i).calculateHammingDistance(entries.get(j)) < distance){
                    
                }
            }
        }*/
    }
}
