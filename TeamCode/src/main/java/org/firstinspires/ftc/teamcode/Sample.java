package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.opencv.core.Point;
import java.util.List;

public class Sample {

    public Point ViscenterPoint;
    public Point relPos;
    public ColorBlobLocatorProcessor.Blob blob;

    public Sample(ColorBlobLocatorProcessor.Blob blob, Pose3D relCam, CamFieldProfile CamProfile){
        List<Double> anglePoint=CamProfile.PixelToAngle(blob.getBoxFit().center);

        double a=-relCam.getOrientation().getPitch();
        double h=-relCam.getPosition().z;

        double x=anglePoint.get(0);
        double y=anglePoint.get(1);

        this.relPos=new Point(
                h*(-(Math.tan(x)*Math.cos(a)*h/Math.tan(a-y))-(Math.tan(x)*Math.sin(a))),
                h*(-1/Math.tan(a-y)));

        this.blob = blob;
    }
}