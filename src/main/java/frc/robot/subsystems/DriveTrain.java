

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.helpers.CCSparkMax;
import frc.parent.RobotMap;
import edu.wpi.first.wpilibj.SPI;

public class DriveTrain extends SubsystemBase {

    public static AHRS gyro = new AHRS(SPI.Port.kMXP);

    // Initializing motors
    private final CCSparkMax frontLeft = new CCSparkMax("Front Left", "fl", RobotMap.FRONT_LEFT, MotorType.kBrushless, IdleMode.kCoast, RobotMap.FRONT_LEFT_REVERSE, true);
    private final CCSparkMax frontRight = new CCSparkMax("Front Right", "fr", RobotMap.FRONT_RIGHT, MotorType.kBrushless, IdleMode.kCoast, RobotMap.FRONT_RIGHT_REVERSE, true);
    private final CCSparkMax backLeft = new CCSparkMax("Back Left", "bl", RobotMap.BACK_LEFT, MotorType.kBrushless, IdleMode.kCoast, RobotMap.BACK_LEFT_REVERSE, true);
    private final CCSparkMax backRight = new CCSparkMax("Back Right", "br", RobotMap.BACK_RIGHT, MotorType.kBrushless, IdleMode.kCoast, RobotMap.BACK_RIGHT_REVERSE, true);

    MotorControllerGroup left = new MotorControllerGroup(frontLeft, backLeft);
    MotorControllerGroup right = new MotorControllerGroup(frontRight, backRight);
    
    DifferentialDrive driveTrain = new DifferentialDrive(left, right);

    public void axisDrive(double speed, double turnSpeed) {
        driveTrain.arcadeDrive(speed * speed, turnSpeed * turnSpeed);
    }

    PIDController controller = new PIDController(.5, 0, 0);
    public Command moveTo(double position){
        RunCommand res = new RunCommand(() -> {
            left.set(controller.calculate(frontLeft.getPosition(), position));
            right.set(controller.calculate(frontRight.getPosition(), position));
        }, this){
            @Override
            public boolean isFinished() {
                // TODO Auto-generated method stub
                return position.;
            }
        };
        return res;
    }

    PIDController angController = new PIDController(0.5, 0, 0);
    public Command turnAngle(double angle){
        gyro.reset();
        RunCommand res = new RunCommand(() -> {
            gyro.reset();
            axisDrive(0, gyro.getAngle());
        }, this);
        return res;
    }

    // public Command moveDist(double pos, double speed){
    //         RunCommand res = new RunCommand(() -> axisDrive(speed, lp-), this){
    //         @Override
    //         public boolean isFinished(){
    //             return example.getPosition() - pos < 0.05;
    //         }
    //     };
    //     return res;
    // }
}
