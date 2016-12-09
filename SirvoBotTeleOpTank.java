package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This is our main TeleOp for the competition at the Burnsville Center, in this program we use tank
 * controls and the bumpers or dpad for our sweeper.
 */

@TeleOp(name="TeleOp Tank Controls", group="11904")
//@Disabled
public class SirvoBotTeleOpTank extends LinearOpMode {

    // Link to hardware map
    HardwareSirvoBot robot = new HardwareSirvoBot();

    // Code ran on OpMode start, initializes hardware map
    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize all hardware
        robot.init(hardwareMap);

        // Signalize program has started in telemetry
        telemetry.addLine("> Initializing program...");
        telemetry.addLine("> Program successfully started.");
        telemetry.update();

        // Wait for driver to press play
        waitForStart();

        // Code loops until driver period ends
        while (opModeIsActive()) {

            // Tank controls using left and right sticks
            if (!gamepad1.dpad_right) {

                robot.leftMotor.setPower(-gamepad1.left_stick_y * 1);
                robot.rightMotor.setPower(-gamepad1.right_stick_y * 1);
            }

            // Use the sweeper with left and right bumpers, or up and down dpad
            if (gamepad1.right_bumper && !gamepad1.left_bumper && !gamepad1.dpad_up && !gamepad1.dpad_down) {

                // Shoot ball(s) with very high speed for Corner Vortex
                robot.sweeperMotor.setPower(1);
            } if (gamepad1.left_bumper && !gamepad1.right_bumper && !gamepad1.dpad_up && !gamepad1.dpad_down) {

                // Take ball(s) into bot's holder
                robot.sweeperMotor.setPower(-0.5);
            } if (gamepad1.dpad_up && !gamepad1.right_bumper && !gamepad1.left_bumper && !gamepad1.dpad_down) {

                // Sweep out ball(s) slowly to give to shooter
                robot.sweeperMotor.setPower(0.1);
            } if (gamepad1.dpad_down && !gamepad1.right_bumper && !gamepad1.left_bumper && !gamepad1.dpad_up) {

                // Take ball(s) in slowly to not break sweeper
                robot.sweeperMotor.setPower(-0.1);
            } if (!gamepad1.right_bumper && !gamepad1.left_bumper && !gamepad1.dpad_up && !gamepad1.dpad_down) {

                // Turn off sweeper motor
                robot.sweeperMotor.setPower(0);
            }

            // Push against beacons slowly to not damage the robot or wall
            if (gamepad1.dpad_right) {

                robot.leftMotor.setPower(-0.3);
                robot.rightMotor.setPower(-0.3);
            } if (!gamepad1.dpad_right && gamepad1.left_stick_y == 0 && gamepad1.right_stick_y == 0) {

                robot.leftMotor.setPower(0);
                robot.rightMotor.setPower(0);
            }

            /**
             * All this telemetry code outputs of the right x and y axis and left x and y axis.
             * The rest gives gives what speed variable you're in (essentially gear) and what percent
             * of maximum speed you are going. -Reece
             */
            telemetry.addLine("> Using TeleOp tank controls");
            telemetry.addData("> Left Y ", -gamepad1.left_stick_y);
            telemetry.addData("> Right Y ", -gamepad1.right_stick_y);
            telemetry.addData("> Speed ", ((-gamepad1.left_stick_y * -gamepad1.right_stick_y) * 0.5) * 100 + "%");
            telemetry.update();

            // Keep idle to stop the while loop from going forever
            idle();
        }
    }
}