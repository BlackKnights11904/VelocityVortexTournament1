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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This is a simple TeleOp program I put together to test the new hardware file, obviously some
 * adjustments will happen and this file may be replaced or overwritten and that is fine.
 * Take this file as a guideline or just guide to learn how to make a simple TeleOp that uses the
 * hardware class. -Reece
 */

@TeleOp(name="TeleOp Tank Controls with Spinner", group="11904")
//@Disabled
public class SirvoBotTeleOpTank extends LinearOpMode {

    //Define local members
    HardwareSirvoBot robot = new HardwareSirvoBot();

    //Code run in initialization
    @Override
    public void runOpMode() throws InterruptedException {

        //Uses code from HardwareSirvoBot to map all the hardware for us
        robot.init(hardwareMap);

        //Send message through telemetry
        telemetry.addLine("> Initializing program...");
        telemetry.addLine("> Program successfully started.");
        telemetry.update();

        //Waits for driver to press play
        waitForStart();

        //Code run until driver presses stop
        while (opModeIsActive()) {

            //Set motor speed based on gamepad sticks
            robot.leftMotor.setPower(-gamepad1.left_stick_y * 0.6);
            robot.rightMotor.setPower(-gamepad1.right_stick_y * 0.6);

            //Change position of arm motor when Right Bumper (to raise) Left Bumper (to lower) are pressed
            /*if (gamepad1.right_bumper && !gamepad1.left_bumper) {

                //Set arm power
                robot.armMotor.setPower(0.6);
            } if (gamepad1.left_bumper && !gamepad1.right_bumper) {

                //Set arm power
                robot.armMotor.setPower(-0.45);
            } if (!gamepad1.right_bumper && !gamepad1.left_bumper) {

                //Turn off arms
                robot.armMotor.setPower(0);
            } */

            //Turn on and off spinner with the right and left bumpers
            if (gamepad1.right_bumper && !gamepad1.left_bumper && !gamepad1.dpad_up) {

                //Shoot out the ball really fast
                robot.spinnerMotor.setPower(1);

            } if (gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_up) {

                //Take ball into chamber
                robot.spinnerMotor.setPower(-0.5);

            } if (gamepad1.dpad_up && !gamepad1.right_bumper && !gamepad1.left_bumper) {

                //Shoot ball out slowly so we can give ball to shooter bot
                robot.spinnerMotor.setPower(0.25);

            } if (!gamepad1.right_bumper && !gamepad1.left_bumper && !gamepad1.dpad_up) {

                //Turn off motors
                robot.spinnerMotor.setPower(0);

            }

            /**
             * All this telemetry code outputs of the right x and y axis and left x and y axis.
             * The rest gives gives what speed variable you're in (essentially gear) and what percent
             * of maximum speed you are going. -Reece
             */
            telemetry.addData("> Left Y ", -gamepad1.left_stick_y);
            telemetry.addData("> Right Y ", -gamepad1.right_stick_y);
            telemetry.addData("> Speed ", ((-gamepad1.left_stick_y * -gamepad1.right_stick_y) * 0.5) * 100 + "%");
            telemetry.update();

            //OpMode won't function without the code below this comment, so don't remove it
            idle();
        }
    }
}