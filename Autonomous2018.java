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

@Autonomous(name="Autonomous", group="Pushbot")

public class Autonomous2018 extends LinearOpMode {

    /* Declare OpMode members. */
    private BotClass turingBot   = new BotClass();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();
    static final double     DRIVE_SPEED             = 0.39;
    static final double     TURN_SPEED              = 0.39;

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
    
        // Wait for the game to start (driver presses PLAY)

        waitForStart();
        
        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        turingBot.autoDrive(DRIVE_SPEED,  6.0,  -6.0, -6.0, 6.0); 
        sleep(2000);
        turingBot.idleMotor();
        turingBot.autoDrive(DRIVE_SPEED,  -12.0,  12.0, 12.0, -12.0);
        sleep(4000);// S1: Forward 47 Inches with 5 Sec timeout
        turingBot.idleMotor();
        turingBot.autoDrive(TURN_SPEED,   -12.0, -12.0, 12.0, 12.0); 
        sleep(2000);// S2: Turn Right 12 Inches with 4 Sec timeout
        turingBot.idleMotor();
        turingBot.autoDrive(DRIVE_SPEED, 18.0, -18.0, 18.0, -18.0);
        sleep(2000);// S3: Reverse 24 Inches with 4 Sec timeout
        turingBot.idleMotor();
        turingBot.autoLift();
        sleep(2000);// S4: Stop and close the claw.
        turingBot.autoDrive(DRIVE_SPEED, -3.0, 3.0, 3.0, -3.0)
        turingBot.idleMotor();
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
