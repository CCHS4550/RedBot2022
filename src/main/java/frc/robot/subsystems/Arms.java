package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.helpers.CCSparkMax;
import frc.parent.RobotMap;

public class Arms extends SubsystemBase {
    Solenoid arms = new Solenoid(PneumaticsModuleType.CTREPCM, RobotMap.ARMS);

    public void toggleArms() {
        arms.toggle();
    }
}