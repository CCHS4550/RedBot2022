package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.helpers.CCSparkMax;
import frc.parent.RobotMap;
import frc.helpers.PneumaticsSystem;

public class Arms extends SubsystemBase {
    //hey there, vicky here, your solenoid definitions are wrong
    //a solenoid works by letting pressure through and pushing a thing out
    //so there's no way to pull back in when only using one
    //so to have a functioning pneumatics system, you need to have 2 solenoids
    //to simplify it, there's a pneumaticssystem class that makes things easier to deal with,
    //basically just setting 2 solenoids opposite each other
    PneumaticsSystem arms = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.ARMS_ONE, RobotMap.ARMS_TWO);

    public void toggleArms() {
        arms.toggle();
    }
}