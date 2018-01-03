/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hamming;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Geoffroy
 */
public class Matrice {
    
    int col, row, nbCase;
    public List<Entry> entries = new ArrayList<Entry>();
    public List<Cluster> clusters = new ArrayList<Cluster>();
    public List<Distance> distances = new ArrayList<Distance>(); 
    private int distanceMin, distanceMax;
    
    int[][] matrice;
    
    public Matrice(int nbEntries){
        this.col = nbEntries;
        this.row = nbEntries;
        this.nbCase = nbEntries * nbEntries;
        this.matrice = new int[nbEntries][nbEntries];
    }
    
    public void generateData(int[] data) {
        addEntry(new Entry(data));
    }
    
    public Entry addEntry(Entry entry) {
        if (entries.add(entry)) {
            return entry;
        }
        return null;
    }
    
    public int[][] getMatrice() {
        return matrice;
    }
    
    public void generateDistancesHamming(){
        for(int i = 0; i < this.entries.size() ; i++){            
            for(int j = i + 1 ; j < this.entries.size() ; j++){
                if(i != j){                    
                    Distance distanceHamming = new Distance(i, j, entries.get(i).calculateHammingDistance(entries.get(j)));
                    distances.add(distanceHamming);              
                }else{                    
                    Distance distanceHamming = new Distance(i, j, 0);
                    distances.add(distanceHamming);
                }                      
            }
        }          
    }
    
    public void displayMatrice(){
        System.out.println("Affichage de la matrice :");
        System.out.println("+----------+----------+----------+----------+----------+----------+----------+----------+----------+----------+");
        for (int i = 0; i < entries.size(); i++) {
            for (int j = 0; j < entries.size(); j++) {
                
                int x = i + 0;
                int y = j + 0;
                
                if(i == j){
                    System.out.print("| "+ x +":"+ y + " -> 0 ");
                }else{                    
                    // Appel de la fonction permettant de calculer la distance de Hamming x = Distance Hamming                    
                    System.out.print("| "+ x +":"+ y + " -> " + entries.get(i).calculateHammingDistance(entries.get(j)) + " ");               
                }                       
            }
            System.out.println("|\n+----------+----------+----------+----------+----------+----------+----------+----------+----------+----------+");
            
        }
        System.out.println("\n");
    }
    
    public void displayDistances(){
        System.out.println("Liste des distances de hamming entres chaques entrées :");
        for (int i = 0; i < entries.size(); i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                System.out.println("La distance de hamming entre les entrées " + entries.get(i).getIdEntry()+ " et " + entries.get(j).getIdEntry()+ " -> " + entries.get(i).calculateHammingDistance(entries.get(j)));
                
            }
        }
        System.out.println("\n");
    }
    
    public void displayDistancesBetweenEntries(int idEntryX, int idEntryY){
        int distance = entries.get(idEntryX - 1).calculateHammingDistance(entries.get(idEntryY - 1));
        System.out.println("La distance de hamming entre l'entrée " + idEntryX + " et l'entrée " + idEntryY + " est de -> " + distance);
        System.out.println("\n");
    }
    
    public void getListEntry(){
        System.out.println("Liste des entrées :");
        for (Entry myentry : this.entries) {
            System.out.println(myentry.toString());
            
        }
        System.out.println("\n");
    }
    
    
   
    
    public void generateClusters(int nbCluster){
        for (int i = 0; i < nbCluster; i++) {
            clusters.add(new Cluster());
        }
    }
    
    public Cluster getFirstCluster() {
        return clusters.get(0);
    }
    
    public Cluster getSecondCluster() {
        return clusters.get(1);
    }
    
    public Cluster getFirstClusterEmpty() {
        Cluster cluster = clusters.get(0);
        for(Cluster c : clusters){
            if(c.isEmpty()){
                return c;
            }
        }
        return cluster;
    }
    
    public Cluster getLastClusterNotEmpty() {
        Cluster cluster = clusters.get(0);
        for(Cluster c : clusters){
            if(!c.isEmpty()){
                cluster = c;
            }
        }
        return cluster;
    }
    
    public void sortEntries(){
        for(Entry entry : entries){
            Cluster c = getFirstClusterEmpty();
            if(c.isEmpty()){
                c.addEntry(entry);
            }else{
                for(int i = 0; i < clusters.size(); i++){
                    for(int j = i + 1; j < clusters.size(); j++){
                        if(entry.calculateDistanceMaxWithCluster(clusters.get(i)) <= entry.calculateDistanceMinWithCluster(clusters.get(j))){
                            clusters.get(i).addEntry(entry);
                        }else{
                            clusters.get(j).addEntry(entry); 
                        }
                    }
                }
            }
            
        }
        /*for(Entry entry : entries){
            if(entry.getIdEntry() == 1){
                clusters.get(0).addEntry(entry);  
            }
            if(entry.getIdEntry() == 2){
                clusters.get(1).addEntry(entry);  
            }
            if(entry.getIdEntry() > 2){
                for(int i = 0; i < clusters.size(); i++){
                    for(int j = i + 1; j < clusters.size(); j++){
                        if(entry.calculateDistanceMaxWithCluster(clusters.get(i)) <= entry.calculateDistanceMinWithCluster(clusters.get(j))){
                            clusters.get(i).addEntry(entry);
                        }else{
                            clusters.get(j).addEntry(entry);
                        }
                    }
                }
            }                        
        }*/
    }
    
    public void displayCluster(){
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("\nCluster " + (i+1) + " :");
            for (Entry entry : clusters.get(i).getEntries()) {
                System.out.println(entry.toString());
            }
        }
        System.out.println("\n");
    }
    
    public int getDistanceMax(Cluster cluster){
        int distance = 0;
        for(int i = 0; i < cluster.entries.size(); i++){
            for(int j = i + 1; j < cluster.entries.size(); j++){
                int newDistance = cluster.entries.get(i).calculateHammingDistance(cluster.entries.get(j));
                if(newDistance > distance){
                    distance = newDistance;
                    return distance;
                }
            }
            
        }
        return distance;
    }

    
}
