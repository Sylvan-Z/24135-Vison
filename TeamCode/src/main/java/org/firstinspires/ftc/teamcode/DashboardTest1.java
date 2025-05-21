package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;

@TeleOp

public class DashboardTest1 extends OpMode {

    private DcMotor testedMotor=null;
    private PIDCoefficients MotorVel=null;

    @Override
    public void init() {
        testedMotor=hardwareMap.get(DcMotor.class, "FR_Motor");
        testedMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testedMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        testedMotor.setPower(0);
        telemetry=new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetry.addLine("Hello World");
        telemetry.update();
    }

    @Override
    public void loop() {
        telemetry.addData("Encoder Reading", testedMotor.getCurrentPosition());
        telemetry.update();
    }
}
