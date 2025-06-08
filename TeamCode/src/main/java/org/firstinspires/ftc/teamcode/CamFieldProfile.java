package org.firstinspires.ftc.teamcode;


import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.List;

public class CamFieldProfile {

    public double FOVx;
    public double FOVy;

    public double Sizex;
    public double Sizey;


    public CamFieldProfile(double FOVx, double FOVy, double Sizex, double Sizey){
        this.FOVx=FOVx;
        this.FOVy=FOVy;

        this.Sizex=Sizex;
        this.Sizey=Sizey;
    }

    public List<Double> PixelToAngle(Point screenPoint){
        List<Double> anglePoint=new ArrayList<>();
        anglePoint.add((screenPoint.x*FOVx)/Sizex);
        anglePoint.add((screenPoint.y*FOVy)/Sizey);
        return anglePoint;
    }
}
