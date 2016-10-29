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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * This is a simple Autonomous program I put together to test the new hardware file, obviously some
 * adjustments will happen and this file may be replaced or overwritten and that is fine.
 * Take this file as a guideline or just guide to learn how to make a simple Autonomous that uses
 * the hardware class. -Reece
 */

@Autonomous(name="Autonomous by Encoder", group="11904")
//@Disabled
public class SirvoBotAutoEncoder extends LinearOpMode {

    //Define local members
    private HardwareSirvoBot robot = new HardwareSirvoBot();

    //Define variables for max turn and drive speed
    private static final double DRIVE_SPEED = 0.6;
    private static final double TURN_SPEED = 0.35;

    //Technical details of wheel for accurate movement with encoder
    public static final double COUNTS_PER_MOTOR_REV = 1440;
    public static final double DRIVE_GEAR_REDUCTION = 2;
    public static final double WHEEL_DIAMETER_INCHES = 4;
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    //Define move robot method, use positive power to go forward, negative to go backward
    public void drive(double power, double leftInches, double rightInches, double timeoutSeconds) {

        //Make new integer to set left and right motor targets
        int leftTarget;
        int rightTarget;

        if (opModeIsActive()) {
            //Determine left and right target to move to
            leftTarget = robot.leftMotor.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            rightTarget = robot.rightMotor.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);

            //Set target and move to position
            robot.leftMotor.setTargetPosition(leftTarget);
            robot.rightMotor.setTargetPosition(rightTarget);
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Reset runtime and start motion
            robot.leftMotor.setPower(Math.abs(power * 0.75));
            robot.rightMotor.setPower(Math.abs(power * 0.75));

            //Test if motors are busy, runtime is less than timeout and motors are busy and then run code
            while (opModeIsActive() && robot.runtime.seconds() <= timeoutSeconds && robot.leftMotor.isBusy() && robot.rightMotor.isBusy()) {

                //Display path to driver
                telemetry.addData("> Moving to pos ", leftTarget + rightTarget);
            }

            //Stop motors after moved to position
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            //Set motors back to using run using encoder
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

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

        //Use encoders to make robot move a certain amount of inches
        drive(DRIVE_SPEED, 6, 6, 0);
        drive(TURN_SPEED, 3, -3, 0);
        drive(DRIVE_SPEED, -2, -2, 0);
        robot.moveArm(1, 250);
        robot.waitTime(250);
        robot.moveArm(-1, 250);
    }
}
