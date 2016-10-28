package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
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

//Main constructor, don't change this
public class HardwareSirvoBot {

    //Technical details of wheel for accurate movement with encoder
    public static final double COUNTS_PER_MOTOR_REV = 1440;
    public static final double DRIVE_GEAR_REDUCTION = 2;
    public static final double WHEEL_DIAMETER_INCHES = 4;
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    //Define motors and other important variables
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;
    public DcMotor armMotor = null;
    public DcMotor spinnerMotor = null;
    public ElapsedTime runtime = new ElapsedTime();

    //Define members for use of this hardware file and telemetry
    private LinearOpMode mode = null;
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
    public void waitTime(int miliseconds) {

        //Program to catch an error
        try {

            //Make java's program execution sleep
            Thread.sleep(miliseconds);

        //Catch error
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    //Define move robot method, use positive power to go forward, negative to go backward
    public void drive(double power, double leftInches, double rightInches, double timeoutSeconds) {

        //Make new integer to set left and right motor targets
        int leftTarget;
        int rightTarget;

        //Make sure op mode is active before running code
        if (mode.opModeIsActive()) {

            //Determine left and right target to move to
            leftTarget = leftMotor.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
            rightTarget = rightMotor.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);

            //Set target and move to position
            leftMotor.setTargetPosition(leftTarget);
            rightMotor.setTargetPosition(rightTarget);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            //Reset runtime and start motion
            leftMotor.setPower(Math.abs(power * 0.75));
            rightMotor.setPower(Math.abs(power * 0.75));

            //Test if motors are busy, runtime is less than timeout and motors are busy and then run code
            while (mode.opModeIsActive() && runtime.seconds() <= timeoutSeconds && leftMotor.isBusy() && rightMotor.isBusy()) {

                //Display path to driver
                mode.telemetry.addData("> Moving to pos ", leftTarget + rightTarget);
            }

            //Stop motors after moved to position
            leftMotor.setPower(0);
            rightMotor.setPower(0);

            //Set motors back to using run using encoder
            leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    //Define move arm method, use positive to go forward, negative to go backward
    public void moveArm(double power, int miliseconds) {

        //Set arm motor to low speed so it doesn't break
        power = power * 0.6;

        // Set power of arm motor
        armMotor.setPower(power);

        //Wait time specified
        waitTime(miliseconds);

        //Stop arm motors
        armMotor.setPower(0);

        //Idle if op mode is active
        mode.idle();
    }
    //End of robot methods. Put all methods above this comment

    //Find components on hardware map
    public void init(HardwareMap hwMap) {

        //Hardware map motors
        leftMotor = hwMap.dcMotor.get("left motor");
        rightMotor = hwMap.dcMotor.get("right motor");
        armMotor = hwMap.dcMotor.get("arm motor");
        spinnerMotor = hwMap.dcMotor.get("spinner motor");

        //Set direction of motors
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        spinnerMotor.setDirection(DcMotor.Direction.FORWARD);

        //Turn off motors
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        armMotor.setPower(0);
        spinnerMotor.setPower(0);

        //Enable encoders on wheel motors and disable on arm motor
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        spinnerMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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