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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="AutonomousRed", group="Iterative Opmode")  // @Autonomous(...) is the other common choice

public class Autonomous5928Red extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime prcTime = new ElapsedTime();

    // private DcMotor leftMotor = null;
    // private DcMotor rightMotor = null;
    Bot turingBot = new Bot();

    public Servo poker;
    public ColorSensor cSensor;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

                /* eg: Initialize the hardware variables. Note that the strings used here as parameters
                 * to 'get' must correspond to the names assigned during the robot configuration
                 * step (using the FTC Robot Controller app on the phone).
                 */
        // leftMotor  = hardwareMap.dcMotor.get("left_drive");
        // rightMotor = hardwareMap.dcMotor.get("right_drive");

        turingBot.frontLeft  = hardwareMap.dcMotor.get("frontLeft");
        turingBot.frontRight  = hardwareMap.dcMotor.get("frontRight");
        turingBot.backLeft  = hardwareMap.dcMotor.get("backLeft");
        turingBot.backRight  = hardwareMap.dcMotor.get("backRight");

        turingBot.frontLift = hardwareMap.dcMotor.get("frontLift");
        turingBot.backLift = hardwareMap.dcMotor.get("backLift");

        turingBot.frontClaw = hardwareMap.dcMotor.get("frontClaw");
        turingBot.backClaw = hardwareMap.dcMotor.get("backClaw");

        poker = hardwareMap.servo.get("poker");
        cSensor = hardwareMap.colorSensor.get("cSensor");


        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        //  rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        // telemetry.addData("Status", "Initialized");


            /*
             * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
             */


    }
    @Override
    public void init_loop() {
    }
            /*
             * Code to run ONCE when the driver hits PLAY
             */


    @Override
    public void start() {
        runtime.reset();
        prcTime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());

        // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        // leftMotor.setPower(-gamepad1.left_stick_y);
        // rightMotor.setPower(-gamepad1.right_stick_y);

        double time = runtime.milliseconds();
        double indtime = prcTime.milliseconds();

        int red = cSensor.red();
        int green = cSensor.green();
        int blue = cSensor.blue();

        boolean redFound = red >= blue * 2;
        //if red is over red and green then the color is red; or configure to the room light with magic numbers

        if(time < 1000){
            turingBot.cCWise(1);
        }
        else if(time < 4000){
            turingBot.left(1);
        }
        else if(time < 6700){
            turingBot.backwLeft(1);
        }
        else if(time < 11000){
            if(redFound){
                turingBot.forward(0);
                poker.setPosition(0);
            }
            else{
                turingBot.back(.3);
                poker.setPosition(.5);
            }
        }
        else if(time < 13000){
            turingBot.back(.3);
            poker.setPosition(1);
        }
        else if(time < 22000){
            if(redFound){
                turingBot.forward(0);
                poker.setPosition(0);
            }
            else{
                turingBot.back(.3);
                poker.setPosition(.5);
            }
        }
        else{
            turingBot.shutdown();
        }



    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {turingBot.shutdown();}

}
