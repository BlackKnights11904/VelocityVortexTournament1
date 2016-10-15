package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * To anyone who looks in this file, do not change without consent from programmers (Zach or Reece)
 * This file contains all the code that defines what our hardware is called in the phone our in our
 * program. Changing them could cause some serious damage, so don't please. Basically this holds all
 * of our methods to make autonomous easier to program. If you feel like we need to add or change
 * something in this file, let one of the programmers know. -Reece
 */

//Main constructor, don't change unless you've renamed file without refactor
public class HardwareSirvoBot
{
    //Define public members
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public ElapsedTime runtime = new ElapsedTime();

    //Define private members
    private ElapsedTime period = new ElapsedTime();

    /**
     * This giant chunk of similar looking code down here is where I have defined all the methods
     * making it easier for us to do autonomous mode. I could explain each one, but you can get a
     * basic idea by looking at the name of the method and its code. Don't change any, add some if
     * you want. Anytime you call that in a TeleOp or Autonomous program to do robot.waitTime(params);
     * rather than waitTime(params);
     */

    //Define wait time method
    public void waitTime(int waitMilis) {
        try {
            Thread.sleep(waitMilis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //Define left turn method
    public void turnLeft(int degreeOfTurn) {
        int degrees = degreeOfTurn / 10 * 56;
        leftMotor.setPower(-0.1125);
        rightMotor.setPower(0.1125);
        waitTime(degrees);
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    //Define right turn method
    public void turnRight(int degreeOfTurn) {
        int degrees = degreeOfTurn / 10 * 56;
        leftMotor.setPower(0.1125);
        rightMotor.setPower(-01125);
        waitTime(degrees);
        leftMotor.setPower(0);
        rightMotor.setPower(0);
    }

    //Define go straight method
    public void goForward(double motorSpeed, int moveMilis) {
        double actualSpeed = motorSpeed * 0.8;
        leftMotor.setPower(actualSpeed);
        rightMotor.setPower(actualSpeed);
        waitTime(moveMilis);
    }

    //Define go backward method
    public void goBackward(double motorSpeed, int moveMilis) {
        double actualSpeed = motorSpeed * -0.8;
        leftMotor.setPower(actualSpeed);
        rightMotor.setPower(actualSpeed);
        waitTime(moveMilis);
    }

    //Define stop movement method
    public void stopMovement(int stopTime) {
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        waitTime(stopTime);
    }

    /**
     * The giant code block of methods is done here, if you plan on putting any here make sure you
     * put it above this comment with the same style, starting with a public void constructor and
     * putting a comment above it with the name. It's better if (almost) everything below this
     * comment doesn't get changed unless we are adding a new hardware map for (example) a gyro or
     * something. -Reece
     */

    //Constructor
    public HardwareSirvoBot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {

        //Hardware map motors
        leftMotor = hwMap.dcMotor.get("left motor");
        rightMotor = hwMap.dcMotor.get("right motor");

        //Set direction of motors
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        //Turn off motors
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        //Disable encoders on motors (Turn back on when we get new motors -Reece)
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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