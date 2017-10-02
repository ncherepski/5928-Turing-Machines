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

        import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
        import com.qualcomm.robotcore.eventloop.opmode.Disabled;
        import com.qualcomm.robotcore.eventloop.opmode.OpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.ColorSensor;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.hardware.DistanceSensor;
        import com.qualcomm.robotcore.hardware.Gamepad;
        import com.qualcomm.robotcore.hardware.HardwareDevice;
        import com.qualcomm.robotcore.hardware.I2cController;
        import com.qualcomm.robotcore.hardware.I2cDevice;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.util.ElapsedTime;

        import org.firstinspires.ftc.robotcontroller.external.samples.SensorMRRangeSensor;
        import org.firstinspires.ftc.teamcode.Bot;

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

        @TeleOp(name="Teleop", group="Iterative Opmode")  // @Autonomous(...) is the other common choice

        public class TeleOp5928 extends OpMode
        {
            /* Declare OpMode members. */
            private ElapsedTime runtime = new ElapsedTime();

            private Bot turingBot = new Bot();

            public Servo poker;
            public Servo fBsktServo;
            public Servo bBsktServo;

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
                fBsktServo = hardwareMap.servo.get("fBsktServo");
                bBsktServo = hardwareMap.servo.get("bBsktServo");

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
            }

            /*
             * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
             */
            @Override
            public void loop() {
                int red = cSensor.red();
                int green = cSensor.green();
                int blue = cSensor.blue();

                telemetry.addData("Status", "Running: " + runtime.toString());
                telemetry.addData("Red","" + red);
                telemetry.addData("Green","" + green);
                telemetry.addData("Blue","" + blue);

                // eg: Run wheels in tank mode (note: The joystick goes negative when pushed forwards)
                // leftMotor.setPower(-gamepad1.left_stick_y);
                // rightMotor.setPower(-gamepad1.right_stick_y);

                double leftX = gamepad1.left_stick_x;
                double leftY = -gamepad1.left_stick_y;

                double rightX = gamepad1.right_stick_x;
                double rightY = -gamepad1.right_stick_y;

                double throttle = gamepad1.right_trigger;

                if(rightX >= .5){
                    turingBot.cWise(throttle);

                    telemetry.addData("Direction", "Clock-wise");
                }

                else if(rightX <= -0.5){
                    turingBot.cCWise(throttle);

                    telemetry.addData("Direction", "Counter Clock-wise");
                }

                else if(leftX <= -.5 && (leftY < .5 && leftY > -.5)){
                    turingBot.left(throttle);

                    telemetry.addData("Direction", "Left");
                }

                else if(leftX >= .5 && (leftY < .5 && leftY > -.5)){
                    turingBot.right(throttle);

                    telemetry.addData("Direction","Right");
                }

                else if(leftY >= .5){
                    if(leftX > .5) {
                        turingBot.forRight(throttle);

                        telemetry.addData("Direction", "Forward-Right");
                    }

                    else if(leftX < -.5){
                        turingBot.forLeft(throttle);

                        telemetry.addData("Direction", "Forward-Left");
                    }

                    else if(leftX >= -.5 && leftX <= .5){
                        turingBot.forward(throttle);

                        telemetry.addData("Direction", "Forward");
                    }
                }
                else if(leftY <= -.5){
                    if(leftX > .5){
                        turingBot.backwRight(throttle);

                        telemetry.addData("Direction", "Back-Right");
                    }

                    else if(leftX < -.5){
                        turingBot.backwLeft(throttle);

                        telemetry.addData("Direction", "Back-Left");
                    }

                    else if(leftX >= -.5 && leftX <= .5){
                        turingBot.back(throttle);

                        telemetry.addData("Direction", "Back");
                    }
                }
                else{
                    turingBot.forward(0);
                }

                if(gamepad1.dpad_up){
                    turingBot.elevate(1);

                    telemetry.addData("Elevator", "Active");
                }
                else{
                    turingBot.elevate(0);
                }

                if(rightY >= .5){
                    turingBot.moveClaws(rightY * .5);

                    telemetry.addData("Claw", "Raising");
                }
                else if(rightY <= -.5){
                    turingBot.moveClaws(rightY * .1);

                    telemetry.addData("Claw", "Lowering");
                }
                else{
                    turingBot.moveClaws(0);
                }

                if(gamepad1.left_bumper) {
                        poker.setPosition(1);
                }
                else if(gamepad1.left_trigger > .49){
                    poker.setPosition(0); // 0 = forward
                }
                else{
                    poker.setPosition(.5);
                }


                if(gamepad1.dpad_right){
                    bBsktServo.setPosition(1);
                    fBsktServo.setPosition(0);
                }
                else if(gamepad1.dpad_down){
                    bBsktServo.setPosition(.25); //1 = in; 0 = out .25
                    fBsktServo.setPosition(.75); //0 = in;1 = out .75
                }
                else if(gamepad1.dpad_left){
                    bBsktServo.setPosition(0);
                    fBsktServo.setPosition(1);
                }

            }

            /*
             * Code to run ONCE after the driver hits STOP
             */
            @Override
            public void stop() {
                turingBot.shutdown();
            }

        }