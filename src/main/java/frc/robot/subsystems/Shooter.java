package frc.robot.subsystems;

import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.helpers.CCSparkMax;
import frc.parent.RobotMap;

public class Shooter extends SubsystemBase {
  // Initializing shooter motors
    final CCSparkMax shooter = new CCSparkMax("Shooter", "shoot", RobotMap.SHOOTER, MotorType.kBrushless, IdleMode.kBrake, RobotMap.SHOOTER_REVERSE, true);

    public void shoot(double speed) {
        shooter.set(speed);
    }

    public Command shootForTime(double power, double time){
        SequentialCommandGroup res = new SequentialCommandGroup(
            new InstantCommand(() -> shoot(power)),
            new WaitCommand(time),
            new InstantCommand(() -> shoot(0))
        );
        return res;
    }
}