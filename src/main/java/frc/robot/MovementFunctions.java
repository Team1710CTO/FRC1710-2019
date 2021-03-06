/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * TalonSRX requires the use of .set(ControlMode.PercentOutput, output); for the speed
 * CANSparkMax requires the use of .set(output); for the speed.
 */
public class MovementFunctions {
    static double currentClawEncoderPosition;
    static int currentIntakeEncoderPosition;
    static double goalClawEncoderPosition;
    static int goalIntakeEncoderPosition;

    public static void resetCurrentEncoderPositions() {
      currentClawEncoderPosition = Robot.clawRotate.getEncoder().getPosition();
      currentIntakeEncoderPosition = Constants.pickup1.getSelectedSensorPosition();
    }

    //restingPosition should be called when the driver outputs 180 on the pov
    public static void restingPosition() {
        System.out.println("Running restingPosition code...");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 1;
        goalIntakeEncoderPosition = 1;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Robot.clawRotate.set(.25);
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            movingPosition();
            resetCurrentEncoderPositions();
            goalClawEncoderPosition = 1;
            goalIntakeEncoderPosition = 1;
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .5);
                Constants.pickup2.set(ControlMode.PercentOutput, .5);
                Robot.clawRotate.set(.5);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Robot.clawRotate.set(.5);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                restingPosition();
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, .5);
            Constants.pickup2.set(ControlMode.PercentOutput, .5);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Robot.clawRotate.set(.5);
            } else {
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Robot.clawRotate.set(.25);
            } else {
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .5);
                Constants.pickup2.set(ControlMode.PercentOutput, .5);
                Robot.clawRotate.set(.5);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Robot.clawRotate.set(.5);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                restingPosition();
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }

    public static void movingPosition() {
        System.out.println("Running movingPosition code...");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 2;
        goalIntakeEncoderPosition = 2;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
            Robot.clawRotate.set(0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }

    public static void BallIntake() {
        System.out.println("Running BallIntake Code...");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 2;
        goalIntakeEncoderPosition = 2;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
            Robot.clawRotate.set(0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }

    public static void HatchIntakeFront() {
        System.out.println("HatchIntakeFront");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 2;
        goalIntakeEncoderPosition = 2;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
            Robot.clawRotate.set(0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            } 
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }

    public static void HatchIntakeBack() {
        System.out.println("Running HatchIntakeBack Code...");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 2;
        goalIntakeEncoderPosition = 2;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
            Robot.clawRotate.set(0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }

    public static void HatchTransfer() {
        System.out.println("Running HatchTransfer Code...");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 2;
        goalIntakeEncoderPosition = 2;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
            Robot.clawRotate.set(0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
            
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }

    public static void BallOuttakeFront() {
        System.out.println("Running BallOuttakeFront code...");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 2;
        goalIntakeEncoderPosition = 2;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
            Robot.clawRotate.set(0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }

    public static void BallOuttakeBack() {
        System.out.println("BallOuttakeBack");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 2;
        goalIntakeEncoderPosition = 2;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
            Robot.clawRotate.set(0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }

    public static void HatchOuttakeFront() {
        System.out.println("HatchOuttakeFront");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 2;
        goalIntakeEncoderPosition = 2;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
            Robot.clawRotate.set(0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }

    public static void HatchOuttakeBack() {
        System.out.println("Running HatchOuttakeBack Code...");
        resetCurrentEncoderPositions();
        goalClawEncoderPosition = 2;
        goalIntakeEncoderPosition = 2;
        if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            Constants.pickup1.set(ControlMode.PercentOutput, 0);
            Constants.pickup2.set(ControlMode.PercentOutput, 0);
            Robot.clawRotate.set(0);
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition < goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition > goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
            if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition > goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, .25);
                Constants.pickup2.set(ControlMode.PercentOutput, .25);
                Robot.clawRotate.set(0);
            } else if(currentClawEncoderPosition < goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(.25);
            } else if(currentClawEncoderPosition == goalClawEncoderPosition && currentIntakeEncoderPosition == goalIntakeEncoderPosition) {
                Constants.pickup1.set(ControlMode.PercentOutput, 0);
                Constants.pickup2.set(ControlMode.PercentOutput, 0);
                Robot.clawRotate.set(0);
            }
        }
    }
}
