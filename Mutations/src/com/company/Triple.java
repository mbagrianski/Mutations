package com.company;

/*
Name: Ethan Zhang
Course: ICS4U1-01
Teacher: Mr. Radulovic
Date Finished: December 8, 2019
Assignment: ADT-Gene Assignment
Description:
This class is used to store genes. The three essential information about genes are:
1) Gene string, which shows the actual gene
2) The probability required to reach this gene
3) The number of steps taken to reach this gene
*/
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