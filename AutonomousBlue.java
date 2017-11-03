/*
Copyright (c) 2016 Robert Atkinson
All rights reserved.
Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.
NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.BotClass;
import com.qualcomm.robotcore.hardware.ColorSensor;



/**
 * This file illustrates the concept of driving a path based on encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the turingBot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that the drive Motors have been configured such that a positive
 *  power command moves them forwards, and causes the encoders to count UP.
 *
 *   The desired path in this example is:
 *   - Drive forward for 48 inches
 *   - Spin right for 12 Inches
 *   - Drive Backwards for 24 inches
 *   - Stop and close the claw.
 *
 *  The code is written using a method called: encoderDrive(speed, leftInches, rightInches, timeoutS)
 *  that performs the actual movement.
 *  This methods assumes that each movement is relative to the last stopping place.
 *  There are other ways to perform encoder based moves, but this method is probably the simplest.
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="AutonomousBlue", group="Pushbot")

public class AutonomousBlue extends LinearOpMode {

    /* Declare OpMode members. */
    private BotClass turingBot   = new BotClass();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    
    static final double     GEAR_DIAMETER   = 1.75 ;     // For figuring circumference
    static final    double LIFT_GEAR_REDUCTION = .75;
    static final    double     COUNTS_INCH         = (COUNTS_PER_MOTOR_REV * LIFT_GEAR_REDUCTION) /
                (GEAR_DIAMETER * Math.PI);
    static final double     DRIVE_SPEED             = 0.39;
    static final double     TURN_SPEED              = 0.39;
    
    
    public void autoDrive(double speed, double frontLeft, double frontRight, double backLeft, double backRight,
                             double timeoutS) {
        int newLeftFTarget;
        int newRightFTarget;
        int newLeftRTarget;
        int newRightRTarget;
        
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftFTarget = turingBot.frontLeftMotor.getCurrentPosition() + (int)(frontLeft * COUNTS_PER_INCH);
        newRightFTarget = turingBot.frontRightMotor.getCurrentPosition() + (int)(frontRight * COUNTS_PER_INCH);
        turingBot.frontLeftMotor.setTargetPosition(newLeftFTarget);
        turingBot.frontRightMotor.setTargetPosition(newRightFTarget);
        newLeftRTarget = turingBot.backLeftMotor.getCurrentPosition() + (int)(backLeft * COUNTS_PER_INCH);
        newRightRTarget = turingBot.backRightMotor.getCurrentPosition() + (int)(backRight * COUNTS_PER_INCH);
        turingBot.backLeftMotor.setTargetPosition(newLeftRTarget);
        turingBot.backRightMotor.setTargetPosition(newRightRTarget);
            

            // Turn On RUN_TO_POSITION
            turingBot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turingBot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turingBot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turingBot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            turingBot.frontLeftMotor.setPower(Math.abs(speed));
        turingBot.frontRightMotor.setPower(Math.abs(speed));
        turingBot.backLeftMotor.setPower(Math.abs(speed));
        turingBot.backRightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                   (runtime.seconds() < timeoutS) &&
                   (turingBot.frontLeftMotor.isBusy() && turingBot.frontRightMotor.isBusy() && turingBot.backLeftMotor.isBusy() && turingBot.backRightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to 7d :7d");
                telemetry.addData("Path2",  "Running at 7d :7d",
                                    turingBot.frontLeftMotor.getCurrentPosition(),
        turingBot.frontRightMotor.getCurrentPosition(),
        turingBot.backLeftMotor.getCurrentPosition(),
        turingBot.backRightMotor.getCurrentPosition(),
                telemetry.update());
            }

            // Stop all motion;
            turingBot.frontLeftMotor.setPower(0);
            turingBot.frontRightMotor.setPower(0);
            turingBot.backLeftMotor.setPower(0);
            turingBot.backRightMotor.setPower(0);


            // Turn off RUN_TO_POSITION
            turingBot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            turingBot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            turingBot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            turingBot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    
    public void autoLift(double inchesRaise,double timeoutS){
        
        int elevation;
        double speed = 0.3;
        
        // Ensure that the opmode is still active
        if (opModeIsActive()) {
        // Determine new target position, and pass to motor controller
        elevation = turingBot.lift.getCurrentPosition() + (int)(inchesRaise * COUNTS_INCH);
        // Set new position to move motors
        turingBot.lift.setTargetPosition(elevation);
        // Turn On RUN_TO_POSITION
        turingBot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        // Reset the timeout time and start motion.
        runtime.reset();
            
        turingBot.lift.setPower(Math.abs(speed));
        // Keep looping while we are still active, and there is time left, and both motors are running.
        while (opModeIsActive() &&
                   (runtime.seconds() < timeoutS) &&
                   (turingBot.lift.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to 7d :7d");
                telemetry.addData("Path2",  "Running at 7d :7d",
                                    turingBot.lift.getCurrentPosition(),
                telemetry.update());
        
            }
            turingBot.lift.setPower(0);


            // Turn off RUN_TO_POSITION
            turingBot.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
    
    @Override
    public void  runOpMode() {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        turingBot.frontRightMotor=hardwareMap.dcMotor.get("frontRightMotor");
        turingBot.frontLeftMotor=hardwareMap.dcMotor.get("frontLeftMotor");
        turingBot.backRightMotor=hardwareMap.dcMotor.get("backRightMotor");
        turingBot.backLeftMotor=hardwareMap.dcMotor.get("backLeftMotor");
        turingBot.lift=hardwareMap.dcMotor.get("lift");
        turingBot.leftGrip=hardwareMap.servo.get("leftGrip");
        turingBot.rightGrip=hardwareMap.servo.get("rightGrip");
        turingBot.arm=hardwareMap.servo.get("arm");
        turingBot.slideServo=hardwareMap.servo.get("slideServo");
        turingBot.cSensor=hardwareMap.colorSensor.get("cSensor");

        // Send telemetry message to signify turingBot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        turingBot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turingBot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turingBot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turingBot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turingBot.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        


        turingBot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turingBot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turingBot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turingBot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turingBot.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d",
                turingBot.backLeftMotor.getCurrentPosition(),
                turingBot.backRightMotor.getCurrentPosition());
        telemetry.update();
        
        int red = turingBot.cSensor.red();
        int green = turingBot.cSensor.green();
        int blue = turingBot.cSensor.blue();
        boolean teamBlue = true;
        boolean teamRed = false;
        boolean rightColor = false;
        // Wait for the game to start (driver presses PLAY)

        waitForStart();
        
        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        //read pictograph and wait 
        if(teamBlue)
                    rightColor = blue > red && blue > green;
        if(teamRed)
                    rightColor = red > blue && red > green;
                    
        if (rightColor){
            turingBot.grab();
            sleep(500);
            autoLift(-3, 0.5);
            turingBot.armDown();
            autoDrive(DRIVE_SPEED,  -4.0,  4.0, 4.0, -4.0, 1.0);
            turingBot.armUp();
            
        } else {
            turingBot.grab();
            sleep(500);
            autoLift(-3, 0.5);
            turingBot.armDown();
            autoDrive(DRIVE_SPEED,  4.0,  -4.0, -4.0, 4.0, 1.0);
            turingBot.armUp();
        }
        
        turingBot.idleMotor();
        sleep(500);
        
        autoDrive(DRIVE_SPEED,  4.0,  -4.0, -4.0, 4.0, 1.0);
        turingBot.idleMotor();
        sleep(500);// S1: Forward 47 Inches with 5 Sec timeout
        turingBot.idleMotor();
        autoDrive(TURN_SPEED,   -12.0, 12.0, -12.0, 12.0, 1.0); 
        // if image = column1 then move 15in
        // if image = column2 then move 22in
        // if image = column3 then move 29in
        turingBot.idleMotor();
        sleep(1000);// S2: Turn Right 12 Inches with 4 Sec timeout
        
        autoLift(2, 0.5);
        sleep(500);
        autoDrive(DRIVE_SPEED, 15.0, -15.0, -15.0, 15.0, 1.0);
        // S3: Reverse 24 Inches with 4 Sec timeout
        turingBot.idleMotor();
        sleep(2000);
        autoDrive(TURN_SPEED,   -12.0, 12.0, -12.0, 12.0, 1.0); 
        turingBot.idleMotor();
        sleep(2000);
        autoLift(-12, 1);
        sleep(2000);// S4: Stop and close the claw.
        autoDrive(DRIVE_SPEED, 8.0, -8.0, -8.0, 8.0, 1.0);
        turingBot.idleMotor();
        sleep(500);
        turingBot.release();
        sleep(500);
        autoDrive(DRIVE_SPEED, -5.0, 5.0, 5.0, -5.0, 1.0);
        sleep(1000);
        autoLift(12, 1);
        sleep(1000);
        autoDrive(DRIVE_SPEED, 3.0, -3.0, -3.0, 3.0, 1.0);
        turingBot.idleMotor();
        sleep(2000);
        
        // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
    }
    
    
    /*
     *  Method to perfmorm a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */


}
