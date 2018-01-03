/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hamming;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author Geoffroy
 */
public class Hamming {
    
    public static final int NB_ENTRIES = 10;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Matrice myMatrice = new Matrice(NB_ENTRIES);
        String ligne = "";
        String fichier = "";
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Quel est le nom de votre fichier ?");
        fichier = clavier.readLine();
        BufferedReader ficTexte;
        try {
            ficTexte = new BufferedReader(new FileReader(new File(fichier)));
            if (ficTexte == null) {
                throw new FileNotFoundException("Fichier non trouvé: " + fichier);
            }
            do {
                ligne = ficTexte.readLine();
                if (ligne != null) {
                        String[] entriesString = ligne.split(" ");
                        int[] entriesData = new int [4];
                        int cpt = 0;
                        for(String entry : entriesString){
                            entriesData[cpt] = Integer.parseInt(entry);
                            cpt++;
                        }
                        myMatrice.generateData(entriesData);
                        
                        
                }
            } while (ligne != null);
            ficTexte.close();
            System.out.println("\n");
            
            myMatrice.generateDistancesHamming();
            myMatrice.getListEntry();
            myMatrice.displayMatrice();
            myMatrice.displayDistances();
            Scanner saisieUtilisateur = new Scanner(System.in); 
            System.out.println("Combien de cluster souhaitez vous créer ?"); 
            int nbClusters = saisieUtilisateur.nextInt(); 
            myMatrice.generateClusters(nbClusters);
            myMatrice.sortEntries();
            myMatrice.displayCluster();
            
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        
    }

}
