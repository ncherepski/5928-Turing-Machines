package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import com.qualcomm.robotcore.util.ElapsedTime;

public class BotClass{
    // all of the following values apply solely to autonomous
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);

    // all of the above values apply solely to autonomous
    public DcMotor backLeftMotor;
    public DcMotor backRightMotor;
    public DcMotor frontLeftMotor;
    public DcMotor frontRightMotor;
    public Servo leftGrip;
    public Servo rightGrip;
    public DcMotor lift;
    


    public void grab(){
        leftGrip.setDirection(Servo.Direction.FORWARD);
        rightGrip.setDirection(Servo.Direction.FORWARD);
        leftGrip.setPosition(0.7);
        rightGrip.setPosition(0.3);
    }
    
    public void release(){
        leftGrip.setDirection(Servo.Direction.FORWARD);
        rightGrip.setDirection(Servo.Direction.FORWARD);
        leftGrip.setPosition(0.001);
        rightGrip.setPosition(0.99);
    }
    
    public void autoLift(){
        double     GEAR_DIAMETER   = 1.75 ;     // For figuring circumference
        double LIFT_GEAR_REDUCTION = .75;
        double     COUNTS_INCH         = (COUNTS_PER_MOTOR_REV * LIFT_GEAR_REDUCTION) /
                (GEAR_DIAMETER * Math.PI);
        int elevation;
        double speed = 0.01;
        int inchesRaise = -12;
        // Ensure that the opmode is still active
        // Determine new target position, and pass to motor controller
        elevation = lift.getCurrentPosition() + (int)(inchesRaise * COUNTS_INCH);
        // Set new position to move motors
        lift.setTargetPosition(elevation);
        // Turn On RUN_TO_POSITION
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Reset the timeout time and start motion.
        lift.setPower(Math.abs(speed));
        // Keep looping while we are still active, and there is time left, and both motors are running.
    
        
    }
    public void autoLower(){
        double     GEAR_DIAMETER   = 1.75 ;     // For figuring circumference
        double LIFT_GEAR_REDUCTION = 0.75;
        double     COUNTS_INCH         = (COUNTS_PER_MOTOR_REV * LIFT_GEAR_REDUCTION) /
                (GEAR_DIAMETER * Math.PI);
        int elevation;
        double speed = 0.01;
        int inchesRaise = 12;
        // Ensure that the opmode is still active
        // Determine new target position, and pass to motor controller
        elevation = lift.getCurrentPosition() + (int)(inchesRaise * COUNTS_INCH);
        // Set new position to move motors
        lift.setTargetPosition(elevation);
        // Turn On RUN_TO_POSITION
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Reset the timeout time and start motion.
        lift.setPower(Math.abs(speed));
        // Keep looping while we are still active, and there is time left, and both motors are running.
        
        
    }
    public void lift(){
        lift.setPower(-0.65);
    }
    public void lower(){
        lift.setPower(0.3);
    }
    
    public void idleMotor(){
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);

        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }
    
    
    public void idleLift(){
        lift.setPower(0);
    }
    
    public void forward(double speed){
        // go forward
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(-speed);
        frontRightMotor.setPower(speed);
        backRightMotor.setPower(-speed);
    }
    
    public void backward(double speed){
        // go forward
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(-speed);
        frontRightMotor.setPower(speed);
        backRightMotor.setPower(-speed);
    }
    
    public void leftTurn(double speed){
        // issa left
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }
    
    public void rightTurn(double speed){
        // issa left
        frontRightMotor.setDirection(DcMotor.Direction.FORWARD);
        backRightMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeftMotor.setPower(speed);
        backLeftMotor.setPower(speed);
        frontRightMotor.setPower(speed);
        backRightMotor.setPower(speed);
    }
    
    public void autoDrive(double speed, double frontLeft, double frontRight, double backLeft, double backRight){

        int newLeftFTarget;
        int newRightFTarget;
        int newLeftRTarget;
        int newRightRTarget;

        // Ensure that the opmode is still active


        // Determine new target position, and pass to motor controller
        newLeftFTarget = frontLeftMotor.getCurrentPosition() + (int)(frontLeft * COUNTS_PER_INCH);
        newRightFTarget = frontRightMotor.getCurrentPosition() + (int)(frontRight * COUNTS_PER_INCH);
        frontLeftMotor.setTargetPosition(newLeftFTarget);
        frontRightMotor.setTargetPosition(newRightFTarget);
        newLeftRTarget = backLeftMotor.getCurrentPosition() + (int)(backLeft * COUNTS_PER_INCH);
        newRightRTarget = backRightMotor.getCurrentPosition() + (int)(backRight * COUNTS_PER_INCH);
        backLeftMotor.setTargetPosition(newLeftRTarget);
        backRightMotor.setTargetPosition(newRightRTarget);

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
        
        frontLeftMotor.getCurrentPosition();
        frontRightMotor.getCurrentPosition();
        backLeftMotor.getCurrentPosition();
        backRightMotor.getCurrentPosition();

    }
}
