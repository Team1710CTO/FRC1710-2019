/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */

/* Open Source Software - may be modified and shared by FRC teams. The code   */

/* must be accompanied by the FIRST BSD license file in the root directory of */

/* the project.                                                               */

/*----------------------------------------------------------------------------*/



package frc.robot;



import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.CommandGroups.TestDrive;

public class Robot extends TimedRobot {

  Command autonomousCommand;  


  @Override

  public void robotInit() {
    Drive.initializeDrive();
  
    autonomousCommand = new TestDrive();
    

  }

  @Override

  public void autonomousInit(){

    autonomousCommand.start();

  }

  @Override

  public void autonomousPeriodic(){

    Scheduler.getInstance().run();

  }


  @Override

  public void teleopInit(){



  }

  @Override

  public void teleopPeriodic() {
    //This makes the robot drive | Turn power is multiplied by .3 to make it slower and drive is by .5 to make is slower as well
   Drive.arcadeDrive(-Drive.getTurnPower() * .2, Drive.getForwardPower() * .35);
   CurrentPool.currentPool();
   System.out.println("R1: " + (Drive.R1.getEncoder().getPosition() / 10.75));
   System.out.println("L1: " + (Drive.L1.getEncoder().getPosition() / 10.75));

  }

}