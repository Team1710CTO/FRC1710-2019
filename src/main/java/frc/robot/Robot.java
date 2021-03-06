/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */

/* Open Source Software - may be modified and shared by FRC teams. The code   */

/* must be accompanied by the FIRST BSD license file in the root directory of */

/* the project.                                                               */

/*----------------------------------------------------------------------------*/
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.CommandGroups.TestDrive;
import frc.Utility.PID;
import edu.wpi.first.wpilibj.Compressor;

public class Robot extends TimedRobot {
  Command autonomousCommand;
  double changesInAngle;
  double changesInRotations;
  double startingRotations;
  double startingAngle;
  double encoderGoalClaw, encoderGoalPickup;
  double clawError, pickupOutput, pHold;
  int i;
  public static Timer clawHold = new Timer();
  public static Timer autoTime = new Timer();
  public static Timer time = new Timer();
  public static double[] changeAngle = new double[]{};
  public static double[] changeRotations = new double[]{};
  public static boolean Shift;
  public static DoubleSolenoid lShifter;
  public static DoubleSolenoid rShifter;
  public static DoubleSolenoid clawOpen1, clawOpen2;
  public static TalonSRX pickup1, pickup2, intake, clawIntake1, clawIntake2, climber1, climber2, climber3, climber4;
  public static CANSparkMax clawRotate;

  @Override
  public void robotInit() {
    pHold = .25;
    pickup1 = new TalonSRX(5);
		pickup2 = new TalonSRX(6);
    lShifter = new DoubleSolenoid(0, 7);
    rShifter = new DoubleSolenoid(1, 6);
    clawOpen1 = new DoubleSolenoid(2,4);
    clawOpen2 = new DoubleSolenoid(3,5);
    autoTime = new Timer();
    clawHold = new Timer();
    intake = new TalonSRX(7);
    clawRotate = new CANSparkMax(8, MotorType.kBrushless );
    Drive.initializeDrive();
    Vision.visionInit();
    pickup1.setNeutralMode(NeutralMode.Brake);
    pickup2.setNeutralMode(NeutralMode.Brake);
    pickup1.follow(pickup2);
    pickup1.setInverted(true);
    Constants.constantInit();
    Vision.visionInit();
    Pixy.init();
    pickup1.setNeutralMode(NeutralMode.Brake);
    pickup2.setNeutralMode(NeutralMode.Brake);
    Drive.L1.setInverted(false);
    //Ballmech.initializeBallMech();
    //autonomousCommand = new TestDrive();
    //Drive.L2.setInverted(false);
    //Drive.L2.follow(Drive.L1);
    
  }

  @Override
  public void autonomousInit(){
    Drive.navx.reset();
    System.out.println("R1: " + (Drive.R1.getEncoder().getPosition() / 10.75));
    System.out.println("L1: " + (Drive.L1.getEncoder().getPosition() / 10.75));
    autoTime.start();
   //autonomousCommand.start();
    
  }

  @Override
  public void autonomousPeriodic(){
    //Scheduler.getInstance().run();
    //This should just be a copy of teleopperiodic- Reminder for Penn to just copy and paste it
  }

  @Override
  public void teleopInit(){
    Pixy.init();
    pickup1.setNeutralMode(NeutralMode.Brake);
    pickup2.setNeutralMode(NeutralMode.Brake);
    Drive.L1.setInverted(false);
    Climber.initalizeClimb();
    Sensors.ultraSonicInit();
    Sensors.limitSwitchInit();
    //Drive.L2.setInverted(false);
    //Drive.L2.follow(Drive.L1);
    //clawOpen1.set(Value.kReverse);
    //clawOpen2.set(Value.kReverse);
  }

  @Override
  public void teleopPeriodic() {
    Climber.Climb();
    SmartDashboard.putNumber("Hold Time", clawHold.get());
    SmartDashboard.putNumber("claw encoder",clawRotate.getEncoder().getPosition() );
    SmartDashboard.putNumber("Claw Temp", clawRotate.getMotorTemperature());
    pickup1.set(ControlMode.PercentOutput, Drive.mechStick.getRawAxis(1) * .7);
    pickup2.set(ControlMode.PercentOutput,-1 * Drive.mechStick.getRawAxis(1) * .7);
    //intake.set(ControlMode.PercentOutput, -1 * Drive.driveStick.getRawAxis(3));
    //pickup1.setNeutralMode(NeutralMode.Brake);
    //pickup2.setNeutralMode(NeutralMode.Brake);
    //pickup1.set(ControlMode.PercentOutput, Drive.driveStick.getRawAxis(5));
    //pickup2.set(ControlMode.PercentOutput, Drive.driveStick.getRawAxis(5));

    if (Drive.driveStick.getRawButton(4) == true){
      Vision.vision();
    } else {
      Drive.arcadeDrive((Drive.getTurnPower()) , Drive.getForwardPower(),false);
    }

    if(Drive.driveStick.getRawButtonPressed(1) == true) {
      clawOpen1.set(Value.kReverse);
      clawOpen2.set(Value.kReverse);
    } else if(Drive.driveStick.getRawButtonPressed(2) == true){
      clawOpen1.set(Value.kForward);
      clawOpen2.set(Value.kForward);
    }
    
    if (Drive.driveStick.getRawButton(5) == true){
      Drive.clawIntake1.set(ControlMode.PercentOutput, -1);
      Drive.clawIntake2.set(ControlMode.PercentOutput, 1);
      intake.set(ControlMode.PercentOutput, 1);
    } else if(Drive.driveStick.getRawButton(6) == true){
      Drive.clawIntake1.set(ControlMode.PercentOutput, 1);
      Drive.clawIntake2.set(ControlMode.PercentOutput, -1);
      intake.set(ControlMode.PercentOutput, -1);
    } else{
      Drive.clawIntake1.set(ControlMode.PercentOutput, 0);
      Drive.clawIntake2.set(ControlMode.PercentOutput, 0);
      intake.set(ControlMode.PercentOutput, 0);
    }

    //are we giving claw power from axis 1, if we are, set goal = 
    //Holding the claw and intake into position
    if (clawRotate.getMotorTemperature() > 200){
       clawRotate.set(0);
    } else if(Drive.driveStick.getRawAxis(2) > 0) {
      clawHold.stop();
      clawHold.reset();
      clawRotate.set(Drive.driveStick.getRawAxis(2) * .35);
      encoderGoalClaw = clawRotate.getEncoder().getPosition();
    } else if(Drive.driveStick.getRawAxis(3) > 0){
      clawHold.stop();
      clawHold.reset();
      clawRotate.set( -1 * Drive.driveStick.getRawAxis(3) * .35);
      encoderGoalClaw = clawRotate.getEncoder().getPosition();
    } else if(encoderGoalClaw > clawRotate.getEncoder().getPosition() + .25){ 
      //proportional control
      clawRotate.set((encoderGoalClaw - clawRotate.getEncoder().getPosition()) * .001);
    } else if(encoderGoalClaw < clawRotate.getEncoder().getPosition() - .25){
      clawRotate.set((encoderGoalClaw - clawRotate.getEncoder().getPosition()) * .001);
    }

   /*if(Drive.mechStick.getRawAxis(1) > 0) {
      pickup1.set(ControlMode.PercentOutput, Drive.mechStick.getRawAxis(1));
      pickup2.set(ControlMode.PercentOutput, Drive.mechStick.getRawAxis(1));
      encoderGoalPickup = pickup1.getSelectedSensorPosition();
    } else{
      pickupOutput = (encoderGoalPickup - pickup1.getSelectedSensorPosition()) * pHold;
      pickup1.set(ControlMode.PercentOutput, pickupOutput);
      pickup2.set(ControlMode.PercentOutput, pickupOutput);
    } */
  } 

  public static void Shifting(boolean isShifted){
    if (isShifted == true){
      lShifter.set(Value.kReverse);
      rShifter.set(Value.kForward);
    } else {
      lShifter.set(Value.kForward);
      rShifter.set(Value.kReverse);
    }
      
  /* CurrentPool.currentPool();
  System.out.println("R1: " + (Drive.R1.getEncoder().getPosition() / 10.75));
  System.out.println("L1: " + (Drive.L1.getEncoder().getPosition() / 10.75));
  Drive.arcadeDrive((-1 * Drive.getTurnPower()) * .2, Drive.getForwardPower() * .35, Shift);
    
    //recording mode
    if (Drive.driveStick.getRawButton(4) == true) {
      changesInAngle = Drive.getNavxAngle() - startingAngle;
      changesInRotations = (Drive.getRightPosition() + Drive.getLeftPosition() /2) - startingRotations;
      //find changes in angles and rotations
      //changeAngle = currentAngle - startingAngle
      //changeDistance = currentRotations - startingRotations
    } else if(Drive.driveStick.getRawButtonReleased(4)) {
      i++;
      changeAngle[i] = changesInAngle;
      changeRotations[i] = changesInRotations;
      System.out.println("Angle Changes: " + changeAngle);
      System.out.println("Rotation Chnages: " + changeRotations);
      //put changes into the array 
    } else if(Drive.driveStick.getRawButton(4) == false) {
      //keep finding starting positions and angles
      startingAngle = Drive.getNavxAngle();
      startingRotations = (Drive.getRightPosition() + Drive.getLeftPosition() /2);
    } */    
  }
}

