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

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * This is a simple TeleOp program I put together to test the new hardware file, obviously some
 * adjustments will happen and this file may be replaced or overwritten and that is fine.
 * Take this file as a guideline or just guide to learn how to make a simple TeleOp that uses the
 * hardware class. -Reece
 */

@TeleOp(name="TeleOp Race Controls", group="11904")
//@Disabled
public class SirvoBotTeleOpRace extends LinearOpMode {

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

            //Set motor speed based on gamepad triggers and sticks for race like controls
            robot.leftMotor.setPower((gamepad1.left_trigger * 0.5) - (gamepad1.right_trigger * 0.5) + (gamepad1.left_stick_x * 0.15));
            robot.rightMotor.setPower((gamepad1.left_trigger * 0.5) - (gamepad1.right_trigger * 0.5) - (gamepad1.left_stick_x * 0.15));

            //Make arm turn once
            int turnedOn = 2;

            //Change position of arm motor when Y (to raise) A (to lower)
            if (gamepad1.left_bumper && turnedOn == 1) {

                //Set arm variable to true so it won't run again
                turnedOn += 1;
                robot.moveArm(1, 250);
            } if (gamepad1.right_bumper && turnedOn == 2) {

                //Set arm variable to false so it won't run again
                turnedOn -= 1;
                robot.moveArm(-1, 250);
            } if (turnedOn > 2 || turnedOn < 1) {
                turnedOn = 1;
            }

            /**
             * All this telemetry code outputs of the right x and y axis and left x and y axis.
             * The rest gives gives what speed variable you're in (essentially gear) and what percent
             * of maximum speed you are going. -Reece
             */
            telemetry.addData("> FWD Speed", gamepad1.left_trigger);
            telemetry.addData("> BWD Speed", gamepad1.right_trigger);
            telemetry.addData("> Speed", gamepad1.left_trigger - gamepad1.right_trigger);
            telemetry.update();

            //OpMode won't function without the code below this comment, so don't remove it
            idle();
        }
    }
}
