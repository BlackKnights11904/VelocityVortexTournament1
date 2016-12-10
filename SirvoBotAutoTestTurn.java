package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

// import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * Basic autonomous encoder program. Duplicate this program to make actual ones.
 */

@Autonomous(name="Autonomous Test Turn", group="11904")
//@Disabled
public class SirvoBotAutoTestTurn extends LinearOpMode {

    // Hook into hardware file
    HardwareSirvoBot robot = new HardwareSirvoBot();

    // Use ElapsedTime for encoders
    private ElapsedTime runtime = new ElapsedTime();

    // Calibrate encoder
    static final double COUNTS_PER_MOTOR_REV = 1120;
    static final double DRIVE_GEAR_REDUCTION = 1;
    static final double WHEEL_DIAMETER_INCHES = 4;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);

    // Set speed variable for driving and turning
    static final double DRIVE_SPEED = 1;
    static final double TURN_SPEED = 0.8;

    @Override
    public void runOpMode() {

        // Call from hardware file
        robot.init(hardwareMap);

        // Send telemetry to signify resetting encoders
        telemetry.addData("Status", "Resetting Encoders");
        telemetry.update();

        // Reset encoders
        robot.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        // Set motors to run using encoders
        robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry to show current position of motors
        telemetry.addData("Path0",  "Starting at %7d :%7d", robot.leftMotor.getCurrentPosition(), robot.rightMotor.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Use encoder drive to move. Move robot 5ft 2in from corner vortex
        encDrive(DRIVE_SPEED, 2, 2);
        encTurn(TURN_SPEED, 360);

        // Add telemetry to signify the robot has reached it's destination
        telemetry.addLine("> Path complete");
        telemetry.update();
    }

    // encoderDrive method. Drives using the motor's encoders
    public void encDrive(double speed, double leftInches, double rightInches) {

        // Create integers holding encoder count to reach destination
        int leftTarget;
        int rightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position
            leftTarget = robot.leftMotor.getCurrentPosition() + (int)((leftInches - 2.5) * COUNTS_PER_INCH);
            rightTarget = robot.rightMotor.getCurrentPosition() + (int)((rightInches - 2.5) * COUNTS_PER_INCH);

            // Set motor's target position
            robot.leftMotor.setTargetPosition(leftTarget);
            robot.rightMotor.setTargetPosition(rightTarget);

            // Set motors to the mode: Run to position
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // Reset the timeout and set the speed of the motors
            runtime.reset();
            robot.leftMotor.setPower(Math.abs(speed));
            robot.rightMotor.setPower(Math.abs(speed));

            // Keep looping until timeout is over and motors are done moving
//            while (opModeIsActive() && (runtime.seconds() < waitSec) && (robot.leftMotor.isBusy() && robot.rightMotor.isBusy())) {
                while (opModeIsActive()  && (robot.leftMotor.isBusy() && robot.rightMotor.isBusy())) {

                // Display position and target to driver
                telemetry.addData(">",  "Going to %7d :%7d", leftTarget, rightTarget);
                telemetry.addData(">",  "Current position %7d :%7d", robot.leftMotor.getCurrentPosition(), robot.rightMotor.getCurrentPosition());
                telemetry.update();

                // Allow time for process to run
                idle();
            }

            // Stop motors
            robot.leftMotor.setPower(0);
            robot.rightMotor.setPower(0);

            // Turn off motor's mode: Run to position
            robot.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //robot.waitTime(250);
        }
    }

    // encTurn method, hopefully turns the robot accurately
    public void encTurn(double speed, int angle) {

        encDrive(speed, (angle / 4.75), -(angle / 4.75));
    }
}