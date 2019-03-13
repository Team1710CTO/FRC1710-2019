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
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
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
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
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
  public static Timer rumbleTime = new Timer();
  public static Timer clawHold = new Timer();
  public static Timer autoTime = new Timer();
  public static Timer time = new Timer();
  public static double[] changeAngle = new double[]{};
  public static double[] changeRotations = new double[]{};
  public static boolean Shift;
  public static DoubleSolenoid lShifter;
  public static DoubleSolenoid rShifter;
  public static DoubleSolenoid clawOpen1, clawOpen2;
  int i;
  public static TalonSRX pickup1, pickup2, intake, clawIntake1, clawIntake2, climber1, climber2, climber3, climber4;
  double encoderGoalClaw, encoderGoalPickup;
  double clawError, pickupOutput, pHold;
  public static CANSparkMax clawRotate;
  public static int visionPosMult = 1;
  public static int hatchPosition = 1;
  public static DigitalInput hatchSwitch1, hatchSwitch2, clawSwitch;
  public Compressor c = new Compressor(0);
  public static boolean clawOverride;

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

    hatchSwitch1 = new DigitalInput(2);
    hatchSwitch2 = new DigitalInput(3);

    clawRotate = new CANSparkMax(8, MotorType.kBrushless );
    Drive.initializeDrive();
    Vision.visionInit();
    pickup1.setNeutralMode(NeutralMode.Brake);
    pickup2.setNeutralMode(NeutralMode.Brake);
    pickup1.follow(pickup2);
    pickup1.setInverted(true);
  
    // Ballmech.initializeBallMech();
    // autonomousCommand = new TestDrive();
    Pixy.init();
    pickup1.setNeutralMode(NeutralMode.Brake);
    pickup2.setNeutralMode(NeutralMode.Brake);
    Drive.L1.setInverted(false);
    //Drive.L2.setInverted(false);
    //Drive.L2.follow(Drive.L1);
    Constants.constantInit();
    
    
  }

  @Override
  public void autonomousInit(){
     //lShifter.set(Value.kReverse);
    //  rShifter.set(Value.kForward);
    Vision.visionInit(); 
    Pixy.init();
    pickup1.setNeutralMode(NeutralMode.Brake);
    pickup2.setNeutralMode(NeutralMode.Brake);
    Drive.L1.setInverted(false);
    //clawOpen2.set(Value.kForward);
    //Drive.L2.setInverted(false);
    //Drive.L2.follow(Drive.L1);
    
    //clawOpen1.set(Value.kReverse);
    //clawOpen2.set(Value.kReverse);

    //Drive.ultraSonicInit();
    //Drive.limitSwitchInit();
    Vision.setFront();
  }

  @Override
  public void autonomousPeriodic(){
    Vision.setFront();
    double pressureCurrent = c.getCompressorCurrent();
    boolean compressorEnabled = c.enabled();
    boolean pressureSwitch = c.getPressureSwitchValue();
   // Vision.stream(); //print camera out to smart dash board
    SmartDashboard.putNumber("vision distance", Vision.visionDistance());
    Drive.driveStick.getRawButton(3);

    if(Drive.driveStick.getRawButtonPressed(3) == true){ //toggles camera position
      visionPosMult = visionPosMult * -1;
      System.out.println("button");
    }
    if(visionPosMult == 1){
      Vision.setBack();
    } else if(visionPosMult == -1){
      Vision.setFront();
    }

    if (Drive.mechStick.getPOV() == 90){
      ClawControl.HatchBack();
    } else if (Drive.mechStick.getPOV() == 270){
      ClawControl.HatchForward();
    } else if (Drive.mechStick.getPOV() == 180){
      ClawControl.BallBack();
    } else if (Drive.mechStick.getPOV() == 0){
      ClawControl.BallForward();
    }

    if (Drive.driveStick.getRawButton(4) == true){ //activaes vision tracking
      Vision.vision();
    } else {
      Drive.arcadeDrive((Drive.getTurnPower()), Drive.getForwardPower(), Drive.driveStick.getRawButton(9));
    }
    
    //SmartDashboard.putNumber("Hold Time", clawHold.get());
    //SmartDashboard.putNumber("claw encoder",clawRotate.getEncoder().getPosition() );
    //SmartDashboard.putNumber("Claw Temp", clawRotate.getMotorTemperature());
    //SmartDashboard.putNumber("R1", Drive.R1.getEncoder().getPosition() / 10.75);
    //SmartDashboard.putNumber("L1", Drive.L1.getEncoder().getPosition() / 10.75);
    //SmartDashboard.putNumber("Pressure CUrrent" , pressureCurrent);
    //SmartDashboard.putBoolean("COmpressure ENabled", compressorEnabled );
   // SmartDashboard.putBoolean("Switch valve",pressureSwitch );
    

    



    //intake.set(ControlMode.PercentOutput, -1 * Drive.driveStick.getRawAxis(3));
    
  // System.out.println(clawRotate.getEncoder().getPosition());
   pickup1.set(ControlMode.PercentOutput, Drive.mechStick.getRawAxis(1) * .7);
   pickup2.set(ControlMode.PercentOutput,-1 * Drive.mechStick.getRawAxis(1) * .7);
    
  if(clawRotate.getMotorTemperature() > 200){
     clawRotate.set(0);
  }
  else if(Drive.mechStick.getRawAxis(2) > 0){
     
      clawRotate.set( Drive.mechStick.getRawAxis(2) * .45);
    }else if(Drive.mechStick.getRawAxis(3) > 0){
      clawRotate.set(-1 * Drive.mechStick.getRawAxis(3) * .45);
    }else{
      clawRotate.set(0);
    } 

   SmartDashboard.putBoolean("hatch button1", hatchSwitch1.get());
   SmartDashboard.putBoolean("hatch button2", hatchSwitch2.get());
   if(Drive.driveStick.getRawButtonPressed(1) == true) {
      clawOpen1.set(Value.kReverse);
      clawOpen2.set(Value.kReverse);
    }else if(Drive.driveStick.getRawButtonPressed(2) == true){
      clawOpen1.set(Value.kForward);
      clawOpen2.set(Value.kForward);
    }/*else if(Drive.driveStick.getRawButtonPressed(1) == false && Drive.driveStick.getRawButtonPressed(2) == false && hatchSwitch1.get() == true && hatchSwitch2.get() == true){
      clawOpen1.set(Value.kReverse);
      clawOpen2.set(Value.kReverse);
    } */
    

    /*if(hatchSwitch1.get() == true && clawOverride == false|| hatchSwitch2.get() == true && clawOverride == false){
      clawOpen2.set(Value.kReverse);
      clawOverride = true;
  } else if(Drive.driveStick.getRawButtonPressed(1) == true){
    clawOverride = true;
    hatchPosition = hatchPosition * -1;
    if(hatchPosition == 1){
      clawOpen2.set(Value.kReverse);
   
    } else if(hatchPosition == -1){
      clawOpen2.set(Value.kForward);
      
    }else{
      clawOverride = false;
    }
  }*/

  /*
      int a_button;    // THIS IS THE AUTO HATCH GRAB
      Drive.driveStick.getRawButton(1);
      if(Drive.driveStick.getRawButtonReleased(1) == true){
        a_button = 0;
      } else {
        a_button = 1;
      }
      SmartDashboard.putNumber("a_button" , a_button);

      if(a_button == 0 && hatchSwitch1.get() == true && hatchSwitch2.get() == true){ //toggles hatch grabber position
       //clawOverride = true;
        hatchPosition = -1;
        System.out.println("button");
      } else if (a_button == 1 && (hatchSwitch1.get() == true || hatchSwitch2.get() == true)){
          hatchPosition = 1;       
      } else if(a_button == 0 && hatchSwitch1.get() == false && hatchSwitch2.get() == false){
          hatchPosition = hatchPosition * -1;
          
      }
      if(hatchPosition == 1){
        clawOpen2.set(Value.kReverse);
      } else if(hatchPosition == -1){
        clawOpen2.set(Value.kForward);
      }
        */
      
      
     
        //if(hatchSwitch1.get() == true|| hatchSwitch2.get() == true){
          //Drive.driveStick.setRumble(RumbleType.kLeftRumble, 1);
          //Drive.driveStick.setRumble(RumbleType.kRightRumble, 1);
          //rumbleTime.delay(.5);
        //}else{
          //Drive.driveStick.setRumble(RumbleType.kLeftRumble, 0);
          //Drive.driveStick.setRumble(RumbleType.kRightRumble, 0);
        //}


    //Manual Compressor    
   // if(Drive.mechStick.getRawButton(2) == true){
     // c.setClosedLoopControl(true);
    //}else{
     // c.setClosedLoopControl(false);
   // }
    
    

    
    
    
   if(Drive.driveStick.getRawButton(5) == true){
     
      Drive.clawIntake2.set(ControlMode.PercentOutput, 1);
     
      intake.set(ControlMode.PercentOutput, 0);
    }else if(Drive.driveStick.getRawButton(6) == true){
      Drive.clawIntake2.set(ControlMode.PercentOutput, -1);
    
      intake.set(ControlMode.PercentOutput, -1); 
        }else{
      Drive.clawIntake2.set(ControlMode.PercentOutput, 0);
    
      intake.set(ControlMode.PercentOutput, 0);
    }

    // Scheduler.getInstance().run();
    //THis should just be a copy of teleopperiodic
    //Reminder for Penn to just copy and paste it
  }

  @Override
  public void teleopInit(){
    //lShifter.set(Value.kReverse);
    //  rShifter.set(Value.kForward);
    Vision.visionInit(); 
    Pixy.init();
    pickup1.setNeutralMode(NeutralMode.Brake);
    pickup2.setNeutralMode(NeutralMode.Brake);
    Drive.L1.setInverted(false);
    //clawOpen2.set(Value.kForward);
    //Drive.L2.setInverted(false);
    //Drive.L2.follow(Drive.L1);
    
    //clawOpen1.set(Value.kReverse);
    //clawOpen2.set(Value.kReverse);

    //Drive.ultraSonicInit();
    //Drive.limitSwitchInit();
    Vision.setFront();
  }

  @Override
  public void teleopPeriodic() {
    Vision.setFront();
    double pressureCurrent = c.getCompressorCurrent();
    boolean compressorEnabled = c.enabled();
    boolean pressureSwitch = c.getPressureSwitchValue();
   // Vision.stream(); //print camera out to smart dash board
    SmartDashboard.putNumber("vision distance", Vision.visionDistance());
    Drive.driveStick.getRawButton(3);

    if(Drive.driveStick.getRawButtonPressed(3) == true){ //toggles camera position
      visionPosMult = visionPosMult * -1;
      System.out.println("button");
    }
    if(visionPosMult == 1){
      Vision.setBack();
    } else if(visionPosMult == -1){
      Vision.setFront();
    }

    if (Drive.mechStick.getPOV() == 90){
      ClawControl.HatchBack();
    } else if (Drive.mechStick.getPOV() == 270){
      ClawControl.HatchForward();
    } else if (Drive.mechStick.getPOV() == 180){
      ClawControl.BallBack();
    } else if (Drive.mechStick.getPOV() == 0){
      ClawControl.BallForward();
    }

    if (Drive.driveStick.getRawButton(4) == true){ //activaes vision tracking
      Vision.vision();
    } else {
      Drive.arcadeDrive((Drive.getTurnPower()), Drive.getForwardPower(), Drive.driveStick.getRawButton(9));
    }
    
    //SmartDashboard.putNumber("Hold Time", clawHold.get());
    //SmartDashboard.putNumber("claw encoder",clawRotate.getEncoder().getPosition() );
    //SmartDashboard.putNumber("Claw Temp", clawRotate.getMotorTemperature());
    //SmartDashboard.putNumber("R1", Drive.R1.getEncoder().getPosition() / 10.75);
    //SmartDashboard.putNumber("L1", Drive.L1.getEncoder().getPosition() / 10.75);
    //SmartDashboard.putNumber("Pressure CUrrent" , pressureCurrent);
    //SmartDashboard.putBoolean("COmpressure ENabled", compressorEnabled );
   // SmartDashboard.putBoolean("Switch valve",pressureSwitch );
    

    



    //intake.set(ControlMode.PercentOutput, -1 * Drive.driveStick.getRawAxis(3));
    
  // System.out.println(clawRotate.getEncoder().getPosition());
   pickup1.set(ControlMode.PercentOutput, Drive.mechStick.getRawAxis(1) * .7);
   pickup2.set(ControlMode.PercentOutput,-1 * Drive.mechStick.getRawAxis(1) * .7);
    
  if(clawRotate.getMotorTemperature() > 200){
     clawRotate.set(0);
  }
  else if(Drive.mechStick.getRawAxis(2) > 0){
     
      clawRotate.set( Drive.mechStick.getRawAxis(2) * .45);
    }else if(Drive.mechStick.getRawAxis(3) > 0){
      clawRotate.set(-1 * Drive.mechStick.getRawAxis(3) * .45);
    }else{
      clawRotate.set(0);
    } 

   SmartDashboard.putBoolean("hatch button1", hatchSwitch1.get());
   SmartDashboard.putBoolean("hatch button2", hatchSwitch2.get());
   if(Drive.driveStick.getRawButtonPressed(1) == true) {
      clawOpen1.set(Value.kReverse);
      clawOpen2.set(Value.kReverse);
    }else if(Drive.driveStick.getRawButtonPressed(2) == true){
      clawOpen1.set(Value.kForward);
      clawOpen2.set(Value.kForward);
    }/*else if(Drive.driveStick.getRawButtonPressed(1) == false && Drive.driveStick.getRawButtonPressed(2) == false && hatchSwitch1.get() == true && hatchSwitch2.get() == true){
      clawOpen1.set(Value.kReverse);
      clawOpen2.set(Value.kReverse);
    } */
    

    /*if(hatchSwitch1.get() == true && clawOverride == false|| hatchSwitch2.get() == true && clawOverride == false){
      clawOpen2.set(Value.kReverse);
      clawOverride = true;
  } else if(Drive.driveStick.getRawButtonPressed(1) == true){
    clawOverride = true;
    hatchPosition = hatchPosition * -1;
    if(hatchPosition == 1){
      clawOpen2.set(Value.kReverse);
   
    } else if(hatchPosition == -1){
      clawOpen2.set(Value.kForward);
      
    }else{
      clawOverride = false;
    }
  }*/

  /*
      int a_button;    // THIS IS THE AUTO HATCH GRAB
      Drive.driveStick.getRawButton(1);
      if(Drive.driveStick.getRawButtonReleased(1) == true){
        a_button = 0;
      } else {
        a_button = 1;
      }
      SmartDashboard.putNumber("a_button" , a_button);

      if(a_button == 0 && hatchSwitch1.get() == true && hatchSwitch2.get() == true){ //toggles hatch grabber position
       //clawOverride = true;
        hatchPosition = -1;
        System.out.println("button");
      } else if (a_button == 1 && (hatchSwitch1.get() == true || hatchSwitch2.get() == true)){
          hatchPosition = 1;       
      } else if(a_button == 0 && hatchSwitch1.get() == false && hatchSwitch2.get() == false){
          hatchPosition = hatchPosition * -1;
          
      }
      if(hatchPosition == 1){
        clawOpen2.set(Value.kReverse);
      } else if(hatchPosition == -1){
        clawOpen2.set(Value.kForward);
      }
        */
      
      
     
        //if(hatchSwitch1.get() == true|| hatchSwitch2.get() == true){
          //Drive.driveStick.setRumble(RumbleType.kLeftRumble, 1);
          //Drive.driveStick.setRumble(RumbleType.kRightRumble, 1);
          //rumbleTime.delay(.5);
        //}else{
          //Drive.driveStick.setRumble(RumbleType.kLeftRumble, 0);
          //Drive.driveStick.setRumble(RumbleType.kRightRumble, 0);
        //}


    //Manual Compressor    
   // if(Drive.mechStick.getRawButton(2) == true){
     // c.setClosedLoopControl(true);
    //}else{
     // c.setClosedLoopControl(false);
   // }
    
    

    
    
    
   if(Drive.driveStick.getRawButton(5) == true){
     
      Drive.clawIntake2.set(ControlMode.PercentOutput, 1);
     
      intake.set(ControlMode.PercentOutput, 0);
    }else if(Drive.driveStick.getRawButton(6) == true){
      Drive.clawIntake2.set(ControlMode.PercentOutput, -1);
    
      intake.set(ControlMode.PercentOutput, -1); 
        }else{
      Drive.clawIntake2.set(ControlMode.PercentOutput, 0);
    
      intake.set(ControlMode.PercentOutput, 0);
    }

    // are we giving claw power from axis 1, if we are, set goal = 
    //Holding the claw and intake into position
  /*  if(clawRotate.getMotorTemperature() > 200){
       clawRotate.set(0);
    }
   else if(Drive.mechStick.getRawAxis(2) > 0) {
      clawHold.stop();
      clawHold.reset();
      clawRotate.set(Drive.mechStick.getRawAxis(2) * .35);
      encoderGoalClaw = clawRotate.getEncoder().getPosition();
    } else if(Drive.mechStick.getRawAxis(3) > 0){
      clawHold.stop();
      clawHold.reset();
      clawRotate.set( -1 * Drive.mechStick.getRawAxis(3) * .35);
      encoderGoalClaw = clawRotate.getEncoder().getPosition();
    }else if(encoderGoalClaw > clawRotate.getEncoder().getPosition() + .25){ 
      //proportional control
      clawRotate.set((encoderGoalClaw - clawRotate.getEncoder().getPosition()) * .015);
    } else if(encoderGoalClaw < clawRotate.getEncoder().getPosition() - .25){
      clawRotate.set((encoderGoalClaw - clawRotate.getEncoder().getPosition()) * .015);
    }

   /*if(Drive.mechStick.getRawAxis(1) > 0) {
    pickup1.set(ControlMode.PercentOutput, Drive.mechStick.getRawAxis(1));
    pickup2.set(ControlMode.PercentOutput, Drive.mechStick.getRawAxis(1));
      encoderGoalPickup = pickup1.getSelectedSensorPosition();
    }else{
      pickupOutput = (encoderGoalPickup - pickup1.getSelectedSensorPosition()) * pHold;
      pickup1.set(ControlMode.PercentOutput, pickupOutput);
      pickup2.set(ControlMode.PercentOutput, pickupOutput);
    }*/
  } 
   
   
    //pickup1.setNeutralMode(NeutralMode.Brake);
    //pickup2.setNeutralMode(NeutralMode.Brake);
  //pickup1.set(ControlMode.PercentOutput, Drive.driveStick.getRawAxis(5));
   //pickup2.set(ControlMode.PercentOutput, Drive.driveStick.getRawAxis(5));
   
   
    //Climber.Climb();
    //}
      // if (Drive.driveStick.getRawButton(1)) {
      //   Drive.R1.set(.5);
      // } else if (Drive.driveStick.getRawButton(2)) {
      //   Drive.R2.set(.5);
      // } else if (Drive.driveStick.getRawButton(3)) {
      //   Drive.L1.set(-.5);
      // } else if (Drive.driveStick.getRawButton(4)) {
      //   Drive.L2.set(-.5);
      // } else {
      //   Drive.R1.set(0);
      //   Drive.R2.set(0);
      //   Drive.L1.set(0);
      //   Drive.L2.set(0);
      // }
  //  CurrentPool.currentPool();
   //System.out.println("R1: " + (Drive.R1.getEncoder().getPosition() / 10.75));
   //System.out.println("L1: " + (Drive.L1.getEncoder().getPosition() / 10.75));
   //Ballmech.ballMechTeleop();
    
    //recording mode
    // if (Drive.driveStick.getRawButton(4) == true){
    //   changesInAngle = Drive.getNavxAngle() - startingAngle;
    //   changesInRotations = (Drive.getRightPosition() + Drive.getLeftPosition() /2) - startingRotations;
    //   //find changes in angles and rotations
    //   //changeAngle = currentAngle - startingAngle
    //   //changeDistance = currentRotations - startingRotations
    // } else if(Drive.driveStick.getRawButtonReleased(4)){
    //   i++;
    //   changeAngle[i] = changesInAngle;
    //   changeRotations[i] = changesInRotations;
    //   System.out.println("Angle Changes: " + changeAngle);
    //   System.out.println("Rotation Chnages: " + changeRotations);
    //   //put changes into the array 
    // } else if(Drive.driveStick.getRawButton(4) == false){
    //   //keep finding starting positions and angles
    //   startingAngle = Drive.getNavxAngle();
    //   startingRotations = (Drive.getRightPosition() + Drive.getLeftPosition() /2);
    // }

  public static void Shifting(boolean isShifted){
    if (isShifted){
      lShifter.set(Value.kReverse);
      rShifter.set(Value.kForward);
      
    } else {
      lShifter.set(Value.kForward);
      rShifter.set(Value.kReverse);
      
    // Drive.arcadeDrive((-1 * Drive.getTurnPower()) * .2, Drive.getForwardPower() * .35, Shift);
    // CurrentPool.currentPool(); // Penn fix this - The watchdog loop tells me this is causing the robot code to run slow!
    //if(Drive.driveStick.getRawButton(4) == true){
      //Pixy.lineFollow();
    //}
    //System.out.println("R1: " + (Drive.R1.getEncoder().getPosition() / 10.75));
    //System.out.println("L1: " + (Drive.L1.getEncoder().getPosition() / 10.75));
    //Ballmech.ballMechTeleop();
    
    //recording mode
    /*if (Drive.driveStick.getRawButton(4) == true) {
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
    }

    //This makes the robot drive | Turn power is multiplied by .3 to make it slower and drive is by .5 to make is slower as well
    if (Drive.driveStick.getRawButton(1) == true){
      Vision.vision();
    } else {
      Drive.arcadeDrive((-1 * Drive.getTurnPower()) * .2, Drive.getForwardPower() * .35,false);
    }
    
    CurrentPool.currentPool();
    //System.out.println("R1: " + (Drive.R1.getEncoder().getPosition() / 10.75));
    //System.out.println("L1: " + (Drive.L1.getEncoder().getPosition() / 10.75));
    Ballmech.ballMechTeleop();
*/    
  }
  }
}


