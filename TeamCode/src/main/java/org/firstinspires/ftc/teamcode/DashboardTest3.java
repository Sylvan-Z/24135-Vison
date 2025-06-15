package org.firstinspires.ftc.teamcode;

import android. util. Size;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.opencv.ColorBlobLocatorProcessor;
import org.firstinspires.ftc.vision.opencv.ColorRange;
import org.firstinspires.ftc.vision.opencv.ImageRegion;
import org.opencv.core.RotatedRect;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import org.firstinspires.ftc.teamcode.CamFieldProfile;
import org.firstinspires.ftc.teamcode.Sample;

import java.util.ArrayList;
import java.util.List;

@Autonomous

public class DashboardTest3 extends LinearOpMode {

    @Override
    public void runOpMode()
    {
        ColorBlobLocatorProcessor colorLocator = new ColorBlobLocatorProcessor.Builder()
                .setTargetColorRange(ColorRange.BLUE)         // use a predefined color match
                .setContourMode(ColorBlobLocatorProcessor.ContourMode.ALL_FLATTENED_HIERARCHY)    // exclude blobs inside blobs
                .setRoi(ImageRegion.asUnityCenterCoordinates(-1.0, 1.0, 1.0, -1.0))  // search central 1/4 of camera view
                .setDrawContours(true)                        // Show contours on the Stream Preview
                .setBlurSize(7)                               // Smooth the transitions between different colors in image
                .build();

        VisionPortal portal = new VisionPortal.Builder()
                .addProcessor(colorLocator)
                .setCameraResolution(new Size(320, 240))
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        CamFieldProfile camFieldProfile = new CamFieldProfile(65.36816529,36.76959297,320,240);
        Pose3D relCam = new Pose3D(new Position(DistanceUnit.CM,0.0,0.0,25.0,0),new YawPitchRollAngles(AngleUnit.DEGREES,0,30,0.0,0));

        List<Sample> Samples = new ArrayList<Sample>();

        telemetry=new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        telemetry.setMsTransmissionInterval(50);   // Speed up telemetry updates, Just use for debugging.
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.MONOSPACE);

        // WARNING:  To be able to view the stream preview on the Driver Station, this code runs in INIT mode.
        while (opModeIsActive() || opModeInInit())
        {
            telemetry.addData("preview on/off", "... Camera Stream\n");

            // Read the current list
            List<ColorBlobLocatorProcessor.Blob> blobs = colorLocator.getBlobs();

            ColorBlobLocatorProcessor.Util.filterByArea(100, 20000, blobs);  // filter out very small blobs.

            telemetry.addLine(" Area Density Aspect  Center");

            // Display the size (area) and center location for each Blob.
            for(ColorBlobLocatorProcessor.Blob b : blobs) {
                RotatedRect boxFit = b.getBoxFit();
                RotatedRect centerMarker = new RotatedRect(boxFit.center,new org.opencv.core.Size(20,20),45);
                Samples.add(new Sample(b,relCam,camFieldProfile));
            }


            telemetry.addData("Samples: ",Samples.size());
            for(Sample active : Samples){
                telemetry.addLine(String.format("%5d  %4.2f   %5.2f  (%3d,%3d)  (%3d,%3d)  (%3d,%3d)",
                        active.blob.getContourArea(), active.blob.getDensity(), active.blob.getAspectRatio(), (int) active.blob.getBoxFit().center.x, (int) active.blob.getBoxFit().center.y, (int) active.ViscenterPoint.x, (int) active.ViscenterPoint.y, (int) active.relPos.x, (int) active.relPos.y));
            }

            FtcDashboard.getInstance().startCameraStream(portal, 0);
            telemetry.update();
            Samples.clear();
            sleep(50);
        }
    }
}
