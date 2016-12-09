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

/**
 * This is a simple Autonomous program I put together to test the new hardware file, obviously some
 * adjustments will happen and this file may be replaced or overwritten and that is fine.
 * Take this file as a guideline or just guide to learn how to make a simple Autonomous that uses
 * the hardware class. -Reece
 */

@Autonomous(name="Autonomous by Gyro", group="11904")
//@Disabled
public class SirvoBotAutoGyro extends LinearOpMode {

    //Define local members
    private HardwareSirvoBot robot = new HardwareSirvoBot();

    //Define variable
    double maxTime = 30;

    //Code run in initialization
    @Override
    public void runOpMode() throws InterruptedException {

        //Uses code from HardwareSirvoBot to map all the hardware for us
        robot.init(hardwareMap);

        //Send messages through telemetry
        telemetry.addData("Status", "Initialization");
        telemetry.addData("Say", "Program is running!");
        telemetry.update();

        //Waits for driver to press play
        waitForStart();

        //Code run until driver presses stop
        while (opModeIsActive()) {

            //Calibrate gyro
            robot.gyroSensor.calibrate();

            //Test if gyro is still calibrating
            while (!isStopRequested() && robot.gyroSensor.isCalibrating()) {

                //Make program idle until gyro is done calibrating
                robot.waitTime(50);
                idle();
            }

            //Display message that gyro is done calibrating
            telemetry.addData("Gyro", "Done!");
            telemetry.update();
            robot.gyroSensor.resetZAxisIntegrator();

            //Start of movement using gyro
            robot.gyroDrive(1, 24, 0.0);
            robot.gyroTurn(1, 45);
            robot.gyroDrive(1, 12, 45);
            robot.gyroTurn(1, 90);
            robot.gyroDrive(1, -30, 0.0);

            //OpMode won't function without the code below this comment, so don't remove it
            idle();
        }
    }
}
