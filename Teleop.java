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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.BotClass;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.ColorSensor;


/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive teleop for a PushBot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Teleop2018", group="Iterative Opmode")  // @Autonomous(...) is the other common choice

public class Teleop extends OpMode
{
    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    private BotClass turingBot = new BotClass();
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI);
    
    static final double     GEAR_DIAMETER   = 1.75 ;     // For figuring circumference
    static final    double LIFT_GEAR_REDUCTION = .75;
    static final    double     COUNTS_INCH         = (COUNTS_PER_MOTOR_REV * LIFT_GEAR_REDUCTION) /
                (GEAR_DIAMETER * Math.PI);
    // private DcMotor leftMotor = null;
    // private DcMotor rightMotor = null;

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
        // leftMotor  = hardwareMap.dcMotor.get("left motor");
        // rightMotor = hardwareMap.dcMotor.get("right motor");
        turingBot.frontRightMotor=hardwareMap.dcMotor.get("frontRightMotor");
        turingBot.frontLeftMotor=hardwareMap.dcMotor.get("frontLeftMotor");
        turingBot.backRightMotor=hardwareMap.dcMotor.get("backRightMotor");
        turingBot.backLeftMotor=hardwareMap.dcMotor.get("backLeftMotor");
        turingBot.lift=hardwareMap.dcMotor.get("lift");
        turingBot.leftGrip=hardwareMap.servo.get("leftGrip");
        turingBot.rightGrip=hardwareMap.servo.get("rightGrip");
        turingBot.slideSend=hardwareMap.dcMotor.get("slideSend");
        turingBot.slideReturn=hardwareMap.dcMotor.get("slideReturn");
        turingBot.slideServo=hardwareMap.servo.get("slideServo");
        turingBot.slideGrip=hardwareMap.servo.get("slideGrip");
        
        turingBot.lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        turingBot.lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        // eg: Set the drive motor directions:
        // Reverse the motor that runs backwards when connected directly to the battery
        // leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        //  rightMotor.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        telemetry.addData("Status", "Initialized");
        
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }
    
    

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        telemetry.addData("Status", "Running: " + runtime.toString());
        
        

        double leftY = -gamepad1.left_stick_y;
        double rightY = -gamepad1.right_stick_y;
        // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
        if (rightY >= .5){
            if (leftY >= .5) {
                turingBot.forward(0.75); //these forward backward commands run at 75% power
            } if (leftY <= -.5){
                turingBot.leftTurn(0.50); // turns run at 50%
            }
        } if (leftY >= .5) {
            if (rightY >= .5) {
                turingBot.forward(.75); //these forward backward commands run at 75% power
            } if (rightY <= -.5) {
                turingBot.rightTurn(0.50); // turns run at 50%
            }
        } if (leftY <= -.5) {
            if (rightY <= -.5){
                turingBot.backward(0.75); //these forward backward commands run at 75% power
            }
        }
        if (leftY == 0) {
            if (rightY == 0){
                turingBot.idleMotor();
            }
        }
        if (gamepad1.dpad_up){
            turingBot.lift();
            
        }
        if (gamepad1.dpad_down){
            
            turingBot.lower();
        }
        if(!gamepad1.dpad_down && !gamepad1.dpad_up){
            turingBot.idleLift();
        }
        if (gamepad1.left_bumper){
            turingBot.release();
        }
        if (gamepad1.right_bumper){
            turingBot.grab();
        }
        if (gamepad1.right_trigger > 0.1){
            turingBot.slide(0.99);
        }
        if (gamepad1.left_trigger > 0.1){
            turingBot.slide(-0.99);
        }
        if((gamepad1.left_trigger + gamepad1.right_trigger) < 0.1){
            turingBot.idleSlide();
        }
        if (gamepad1.b){
            turingBot.slideGrab();
        }
        if (gamepad1.y){
            turingBot.slideRelease();
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
