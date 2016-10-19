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
public class HardwareSirvoBot {

    //Define public members
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public DcMotor armMotor = null;
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

        //Wait time so program doesn't bug
        waitTime(50);
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

        //Wait time so program doesn't bug
        waitTime(50);
    }

    //Define go straight method
    public void goForward(double motorSpeed, int moveMilis) {

        //Limit maximum speed so we don't fall over
        double actualSpeed = motorSpeed * 0.8;

        //Set speed of left and right motor
        leftMotor.setPower(actualSpeed);
        rightMotor.setPower(actualSpeed);

        //Wait time specified
        waitTime(moveMilis);

        //Stop movement
        stopMovement(0);

        //Wait time so program doesn't bug
        waitTime(50);
    }

    //Define go backward method
    public void goBackward(double motorSpeed, int moveMilis) {

        //Limit maximum speed so we don't fall over
        double actualSpeed = motorSpeed * -0.8;

        //Set speed of left and right motor
        leftMotor.setPower(actualSpeed);
        rightMotor.setPower(actualSpeed);

        //Wait time specified
        waitTime(moveMilis);

        //Stop movement
        stopMovement(0);

        //Wait time so program doesn't bug
        waitTime(50);
    }

    //Define stop movement method
    public void stopMovement(int stopForTime) {

        //Set power of left and right motor
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        //Wait time if specified
        waitTime(stopForTime);

        //Wait time so program doesn't bug
        waitTime(50);
    }

    //Define arm forward method
    public void armForward(double armPower, int armMoveMilis) {

        //Set arm motor to low speed so it doesn't break
        armPower *= 0.4;

        // Set power of arm motor
        armMotor.setPower(armPower);

        //Wait time specified
        waitTime(armMoveMilis);

        //Stop arm motors
        armMotor.setPower(0);

        //Wait time so program doesn't bug
        waitTime(50);
    }

    //Define arm backward method
    public void armBackward(double armPower, int armMoveMilis) {

        //Set power to -0.4 so it goes in reverse and goes at a lower speed so it doesn't break
        armPower *= -0.4;

        //Set power of arm motor
        armMotor.setPower(armPower);

        //Wait time specified
        waitTime(armMoveMilis);

        armMotor.setPower(0);

        //Stop arm motors
        //Wait time so program doesn't bug
        waitTime(50);
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
        armMotor = hwMap.dcMotor.get("arm motor");

        //Set direction of motors
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setDirection(DcMotor.Direction.FORWARD);

        //Turn off motors
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        armMotor.setPower(0);

        //Disable encoders on motors (Turn back on when we get new motors -Reece)
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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