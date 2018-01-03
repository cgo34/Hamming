/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hamming;

import javax.swing.JPanel;

/**
 *
 * @author Geoffroy
 */
public class Distance {
    int x,y,h;
    
    public Distance(int entryX, int entryY, int distance){
        this.x = entryX;
        this.y = entryY;
        this.h = distance;
    }

    /**
     * @return the entryX
     */
    public int getEntryX() {
        return this.x + 1;
    }

    /**
     * @param entryX the coordinateX to set
     */
    public void setEntryX(int entryX) {
        this.x = entryX;
    }

    /**
     * @return the entryY
     */
    public int getEntryY() {
        return this.y + 1;
    }

    /**
     * @param entryY the coordinateY to set
     */
    public void setEntryY(int entryY) {
        this.y = entryY;
    }
    
    public int getDistanceHamming(){
        return this.h;
    }
    
    public String toString(){
        //System.out.println("La distance de hamming entre l'entrée " + x + " et l'entrée " + y + " est de -> " + h);
        return "La distance de hamming entre l'entrée " + x + " et l'entrée " + y + " est de -> " + h;
    }
}
