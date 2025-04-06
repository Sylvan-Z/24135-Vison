package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class Hello_World extends OpMode {

    @Override
    public void init() {
        telemetry.addLine("Hello World");
        telemetry.update();
    }

    @Override
    public void loop() {

    }
}
