package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class botClass {
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;




    public void forward(double speed){
        // go forward
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);

        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }
    public void backward(double speed){
        // go forward
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);

        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }
    public void leftTurn(double speed){
        // issa left
        frontLeftMotor.setDirection(REVERSE);
        backLeftMotor.setDirection(REVERSE);
        frontRightMotor.setDirection(FORWARD);
        backRightMotor.setDirection(FORWARD);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);

        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }
    public void rightTurn(double speed){
        // issa left
        frontRightMotor.setDirection(REVERSE);
        backRightMotor.setDirection(REVERSE);
        frontLeftMotor.setDirection(FORWARD);
        backLeftMotor.setDirection(FORWARD);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);

        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }
}