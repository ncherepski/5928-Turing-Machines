package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.util.ElapsedTime;

public class botClass {
    // all of the following values apply solely to autonomous
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    static final double     DRIVE_SPEED             = 0.39;
    static final double     TURN_SPEED              = 0.39;
    // all of the above values apply solely to autonomous
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public Servo leftGrip;
    public Servo rightGrip;
    public DcMotor lift;


    public void grab(){
        leftGrip.setPosition(0.7);
        rightGrip.setPosition(-0.7);
    }
    public void lift(){
            int elevation;
            double speed = 0.6;
            int inchesRaise = 12;
            // Ensure that the opmode is still active


            // Determine new target position, and pass to motor controller
            elevation = lift.getCurrentPosition() + (int)(inchesRaise * COUNTS_PER_INCH);

            lift.setTargetPosition(elevation);


            // Turn On RUN_TO_POSITION
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            // reset the timeout time and start motion.

            lift.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while
                    (lift.isBusy()) {

            }
            lift.getCurrentPosition();
    }

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
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);

        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }
    public void rightTurn(double speed){
        // issa left
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);

        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }
    public void autoDrive(double speed, double leftInches, double rightInches){

        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active


            // Determine new target position, and pass to motor controller
            newLeftTarget = frontLeftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = frontRightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            backLeftMotor.setTargetPosition(newLeftTarget);
            backRightMotor.setTargetPosition(newRightTarget);
            newLeftTarget = backLeftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            newRightTarget = backRightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
            frontLeftMotor.setTargetPosition(newLeftTarget);
            frontRightMotor.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.

            frontLeftMotor.setPower(Math.abs(speed));
            frontRightMotor.setPower(Math.abs(speed));
            backLeftMotor.setPower(Math.abs(speed));
            backRightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while
                    (frontLeftMotor.isBusy() && backRightMotor.isBusy() && backLeftMotor.isBusy() && frontRightMotor.isBusy()) {

            }
        frontLeftMotor.getCurrentPosition();
        frontRightMotor.getCurrentPosition();
        backLeftMotor.getCurrentPosition();
        backRightMotor.getCurrentPosition();

    }
}