package com.company;

public class Triple{
    //After making the variables public, we can directly change the values instead of writing getters/setters.
    public String x;
    public double y;
    public int z;

    public Triple(String x, double y, int z){
        //Sets the values of Triple
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
