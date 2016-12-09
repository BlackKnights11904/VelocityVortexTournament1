package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsAnalogOpticalDistanceSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * To anyone who looks in this file, do not change without consent from programmers (Zach or Reece)
 * This file contains all the code that defines what our hardware is called in the phone our in our
 * program. Changing them could cause some serious damage, so don't please. Basically this holds all
 * of our methods to make autonomous easier to program. If you feel like we need to add or change
 * something in this file, let one of the programmers know. -Reece
 */

//Main constructor, don't change unless you've renamed file without refactor
public class HardwareSirvoBot {

    //Define members for accurate movement
    public static final double COUNTS_PER_MOTOR_REV = 1440;
    public static final double DRIVE_GEAR_REDUCTION = 2;
    public static final double WHEEL_DIAMETER_INCHES = 4;
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    //No idea what this does, but I think we need it
    static final double HEADING_THRESHOLD = 1 ;
    //Turning coefficient to turn faster or slower; Larger is more responsive, but less stable
    static final double P_TURN_COEFF = 0.1;
    //Driving coefficient to go faster or slower; Larger is more responsive, but less stable
    static final double P_DRIVE_COEFF = 0.15;

    //Define public members
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public DcMotor armMotor = null;
    public ModernRoboticsI2cGyro gyroSensor = null;
    //public OpticalDistanceSensor rangeSensor = null;
    //public ModernRoboticsI2cColorSensor colorSensor = null;
    public ElapsedTime runtime = new ElapsedTime();

    //Define private members
    public LinearOpMode mode = null;
    private ElapsedTime period = new ElapsedTime();

    /**
     * This giant chunk of similar looking code down here is where I have defined all the methods
     * making it easier for us to do autonomous mode. I could explain each one, but you can get a
     * basic idea by looking at the name of the method and its code. Don't change any, add some if
     * you want. Anytime you call that in a TeleOp or Autonomous program to do mode.waitTime(params);
     * rather than waitTime(params);
     */

    /**
     * Start of robot methods. Put all methods below this comment
     * Define wait time method
     */
    public void waitTime(int waitMilis) {

        //Program to catch an error
        try {

            //Make java's program execution sleep
            Thread.sleep(waitMilis);

        //Catch error
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //Define left turn method
    public void turnLeft(int degreeOfTurn) {

        //Do some math stuff so we can turn a certain amount of degrees
        int degrees = degreeOfTurn / 10 * 84;

        //Set the power of left and right motors
        leftMotor.setPower(-0.1125);
        rightMotor.setPower(0.1125);

        //Wait for time so it turns a certain amount of degrees
        waitTime(degrees);

        //Stop motors after a certain amount of degrees turned
        stopMovement(0);

        //Idle if op mode is active
        if (mode.opModeIsActive()) {
            mode.idle();
        }
    }

    //Define right turn method
    public void turnRight(int degreeOfTurn) {

        //Do some math stuff so we can turn a certain amount of degrees
        int degrees = degreeOfTurn / 10 * 84;

        //Set the power of left and right motors
        leftMotor.setPower(0.1125);
        rightMotor.setPower(-01125);

        //Wait for time so it turns a certain amount of degrees
        waitTime(degrees);

        //Stop movement
        stopMovement(0);

        //Idle if op mode is active
        if (mode.opModeIsActive()) {
            mode.idle();
        }
    }

    //Define go straight method
    public void goForward(double moveSpeed, int moveMilis) {

        //Limit max speed so we don't fall over
        double actualSpeed = moveSpeed * 0.5;

        //Set speed of left and right motor
        leftMotor.setPower(actualSpeed);
        rightMotor.setPower(actualSpeed);

        //Wait time specified
        waitTime(moveMilis);

        //Stop movement
        stopMovement(0);

        //Idle if op mode is active
        if (mode.opModeIsActive()) {
            mode.idle();
        }
    }

    //Define go backward method
    public void goBackward(double moveSpeed, int moveMilis) {

        //Limit maximum speed so we don't fall over
        double actualSpeed = moveSpeed * -0.5;

        //Set speed of left and right motor
        leftMotor.setPower(actualSpeed);
        rightMotor.setPower(actualSpeed);

        //Wait time specified
        waitTime(moveMilis);

        //Stop movement
        stopMovement(0);

        //Idle if op mode is active
        if (mode.opModeIsActive()) {
            mode.idle();
        }
    }

    //Define stop movement method
    public void stopMovement(int stopForTime) {

        //Set power of left and right motor
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        //Wait time if specified
        waitTime(stopForTime);

        //Idle if op mode is active
        if (mode.opModeIsActive()) {
            mode.idle();
        }
    }

    //Define arm forward method
    public void armForward(double armPower, int armMoveMilis) {

        //Set arm motor to low speed so it doesn't break
        armPower = armPower * 0.6;

        // Set power of arm motor
        armMotor.setPower(armPower);

        //Wait time specified
        waitTime(armMoveMilis);

        //Stop arm motors
        armMotor.setPower(0);

        //Idle if op mode is active
        if (mode.opModeIsActive()) {
            mode.idle();
        }
    }

    //Define arm backward method
    public void armBackward(double armPower, int armMoveMilis) {

        //Set power to -0.4 so it goes in reverse and goes at a lower speed so it doesn't break
        armPower = armPower * -0.6;

        //Set power of arm motor
        armMotor.setPower(armPower);

        //Wait time specified
        waitTime(armMoveMilis);

        //Stop arm motors
        armMotor.setPower(0);

        //Idle if op mode is active
        if (mode.opModeIsActive()) {
            mode.idle();
        }
    }
    //End of robot methods. Put all methods above this comment

    /**
     * Start of gyro component methods
     * Drive forward or backward a certain number of inches.
     */
    public void gyroDrive (double speed, double distance, double angle) {

        //Limit max speed
        speed = speed * 0.75;

        //Define members used in method
        int newLeftTarget;
        int newRightTarget;
        int moveCounts;
        double max;
        double error;
        double steer;
        double leftSpeed;
        double rightSpeed;

        //Run program if op mode is active
        if (mode.opModeIsActive()) {

            //Determine new target's position and tell motor controller
            moveCounts = (int)(distance * COUNTS_PER_INCH);
            newLeftTarget = leftMotor.getCurrentPosition() + moveCounts;
            newRightTarget = rightMotor.getCurrentPosition() + moveCounts;

            //Set target and move to that position
            leftMotor.setTargetPosition(newLeftTarget);
            rightMotor.setTargetPosition(newRightTarget);

            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Start movement
            speed = Range.clip(Math.abs(speed), 0.0, 1.0);
            leftMotor.setPower(speed);
            rightMotor.setPower(speed);

            //Loop program until on target and motors are stopped
            while (mode.opModeIsActive() && (leftMotor.isBusy() && rightMotor.isBusy())) {

                //Adjust speed by how far off target angle
                error = getError(angle);
                steer = getSteer(error, P_DRIVE_COEFF);

                //If driving in reversed, motor correction needs to be reversed
                if (distance < 0)
                    steer *= -1.0;

                leftSpeed = speed - steer;
                rightSpeed = speed + steer;

                //Normalize speeds so they don't go above maximum speed
                max = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
                if (max > 1.0)
                {
                    leftSpeed /= max;
                    rightSpeed /= max;
                }

                //Set motor speed to calculated speed to go a certain distance
                leftMotor.setPower(leftSpeed);
                rightMotor.setPower(rightSpeed);
            }

            //Stop motors
            leftMotor.setPower(0);
            rightMotor.setPower(0);

            //Set motors back to using encoder
            leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    //Turn using the gyro. Turn right using positive angle, left using negative angle
    public void gyroTurn (double speed, double angle) {

        //Keep looping while active
        while (mode.opModeIsActive() && !onHeading((speed * 0.75), angle, P_TURN_COEFF)) {
            //Update telemetry and allow time for other programs to run
            mode.telemetry.update();
        }
    }

    //Hold a turn using the gyro. Always use after a turn, use the same speed as the turn
    public void gyroHold(double speed, double angle, double holdTime) {

        ElapsedTime holdTimer = new ElapsedTime();

        // keep looping while we have time remaining.
        holdTimer.reset();
        while (mode.opModeIsActive() && (holdTimer.time() < holdTime)) {
            // Update telemetry & Allow time for other processes to run.
            onHeading((speed * 0.75), angle, P_TURN_COEFF);
        }

        //Stop motors
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    /**
     * onHeading boolean for gyros
     * I honestly don't know what this does, I'm looking at the code to figure it out so I can make
     * tweaks to it, but I'm going to keep this comment here so I know what the params do so I don't
     * forget later on.
     *
     * Perform one cycle of closed loop heading control.
     * @param speed    Speed of turn
     * @param angle    Absolute angle of turn (in degrees) relative to last calibration
     *                 0 is forward, +ve is left from forward, -ve is right from forward.
     *                 If relative angle is required, add/subtract from current heading.
     * @param PCoeff   Proportional Gain coefficient
     */
    boolean onHeading(double speed, double angle, double PCoeff) {

        //Define members used in method
        double error ;
        double steer ;
        boolean onTarget = false ;
        double leftSpeed;
        double rightSpeed;

        //Determine power based on +/- error
        error = getError(angle);

        if (Math.abs(error) <= HEADING_THRESHOLD) {
            steer = 0.0;
            leftSpeed  = 0.0;
            rightSpeed = 0.0;
            onTarget = true;
        }
        else {
            steer = getSteer(error, PCoeff);
            rightSpeed  = (speed * 0.75) * steer;
            leftSpeed   = -rightSpeed;
        }

        //Set motor speeds to proper power
        leftMotor.setPower(leftSpeed);
        rightMotor.setPower(rightSpeed);

        //Send back if on target
        return onTarget;
    }

    //Find difference between target angle and current angle
    public double getError(double targetAngle) {

        double robotError;

        //Find difference from target from -179 to 180 degrees  (
        robotError = targetAngle - gyroSensor.getIntegratedZValue();
        while (robotError > 180)  robotError -= 360;
        while (robotError <= -180) robotError += 360;
        return robotError;
    }

    //Finds steering force
    public double getSteer(double error, double PCoeff) {
        return Range.clip(error * PCoeff, -1, 1);
    }
    //End of gyro methods

    /**
     * Range sensor methods start here
     * Find distance from object robot is facing
     * If color is not specified, find distance from any object with any color
     */
    /*public void senseRGB(double r, double g, double b) {

        //Test for color if specified
        if (r >= colorSensor.red() / 85 || r <= colorSensor.red() / 85) {
            if (g >= colorSensor.green() / 85 || g <= colorSensor.green() / 85) {
                if (b >= colorSensor.blue() / 85 || b <= colorSensor.blue() / 85) {
                    mode.telemetry.addData("Say", "Hello! :)");
                } else {
                    mode.telemetry.addData("Say", "Could not find desired color");
                }
            } else {
                mode.telemetry.addData("Say", "Could not find desired color");
            }
        } else {
            mode.telemetry.addData("Say", "Could not find desired color");
        }
    } */

    //Find and calibrate components on hardware map
    public void init(HardwareMap hwMap) {

        //Hardware map motors
        leftMotor = hwMap.dcMotor.get("left motor");
        rightMotor = hwMap.dcMotor.get("right motor");
        armMotor = hwMap.dcMotor.get("arm motor");

        //Hardware map components
        gyroSensor = (ModernRoboticsI2cGyro) hwMap.gyroSensor.get("gyro sensor");
        //colorSensor = (ModernRoboticsI2cColorSensor) hwMap.colorSensor.get("color sensor");

        //Set direction of motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setDirection(DcMotor.Direction.FORWARD);

        //Turn off motors
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        armMotor.setPower(0);

        //Enable encoders on wheel motors and disable on arm motor
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /**
     * Please don't change anything in here unless you know what you're doing, basically what this
     * does is makes it so this class does nothing for the duration of the TeleOp or Autonomous
     * period. Thanks for reading through these comments, they should help getting through the
     * confusing language of Java. -Reece
     * @param periodMs
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        //Makes the script do nothing after the init phase
        if (remaining > 0)
            Thread.sleep(remaining);

        //Reset clock when this class is run again
        period.reset();
    }
}