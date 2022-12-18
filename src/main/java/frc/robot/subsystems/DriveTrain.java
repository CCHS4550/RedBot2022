

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
import frc.helpers.OI;
import frc.helpers.PneumaticsSystem;
import frc.parent.RobotMap;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;

public class DriveTrain extends SubsystemBase {

    public static AHRS gyro = new AHRS(SPI.Port.kMXP);

    // Initializing motors
    private final CCSparkMax frontLeft = new CCSparkMax("Front Left", "fl", RobotMap.FRONT_LEFT, MotorType.kBrushless, IdleMode.kCoast, RobotMap.FRONT_LEFT_REVERSE, true);
    private final CCSparkMax frontRight = new CCSparkMax("Front Right", "fr", RobotMap.FRONT_RIGHT, MotorType.kBrushless, IdleMode.kCoast, RobotMap.FRONT_RIGHT_REVERSE, true);
    private final CCSparkMax backLeft = new CCSparkMax("Back Left", "bl", RobotMap.BACK_LEFT, MotorType.kBrushless, IdleMode.kCoast, RobotMap.BACK_LEFT_REVERSE, true);
    private final CCSparkMax backRight = new CCSparkMax("Back Right", "br", RobotMap.BACK_RIGHT, MotorType.kBrushless, IdleMode.kCoast, RobotMap.BACK_RIGHT_REVERSE, true);

    // Initializing solenoids
    PneumaticsSystem fastMode = new PneumaticsSystem(PneumaticsModuleType.CTREPCM, RobotMap.DRIVE_MODE_ONE, RobotMap.DRIVE_MODE_TWO);

    MotorControllerGroup left = new MotorControllerGroup(frontLeft, backLeft);
    MotorControllerGroup right = new MotorControllerGroup(frontRight, backRight);
    
    DifferentialDrive driveTrain = new DifferentialDrive(left, right);

    public double driveCap = .6;
    public void axisDrive(double speed, double turnSpeed) {
        driveTrain.arcadeDrive(OI.normalize(speed * speed * Math.signum(speed) * -1, -1, 1) * driveCap, OI.normalize(turnSpeed * turnSpeed * Math.signum(turnSpeed), -1, 1) * driveCap);
    }

    PIDController controller = new PIDController(.5, 0, .1);
    public Command moveTo(double position){
        RunCommand move = new RunCommand(() -> {
        left.set(OI.normalize(controller.calculate(frontLeft.getPosition(), position), -1, 1) * driveCap * .5);
        right.set(OI.normalize(controller.calculate(frontRight.getPosition(), position), -1, 1) * driveCap * .5);
        }, this){
            @Override
            public boolean isFinished() {
                // TODO Auto-generated method stub
                System.out.println("Left: " + frontLeft.getPosition());
                System.out.println("Right: " + frontRight.getPosition());
                return Math.abs(position + frontRight.getPosition()) < .5;
            }
        };
        InstantCommand reset = new InstantCommand(() -> {
            frontLeft.reset();
            frontRight.reset();
        });
        SequentialCommandGroup res = new SequentialCommandGroup(
            reset,
            move
        );
        return res;
    }

    PIDController angController = new PIDController(0.5, 0, 0);
    public Command turnAngle(double angle){
        // gyro.reset();
        RunCommand res = new RunCommand(() -> axisDrive(0, angController.calculate(gyro.getAngle(), angle)), this){
            @Override
            public boolean isFinished() {
                // TODO Auto-generated method stub
                return Math.abs(gyro.getAngle() - angle) < 5;
            }
        };
        return res;
    }

    public static void gyroReset(){
        gyro.reset();
    }

    public void toggleFastMode(){
        fastMode.toggle();
    }

    // public void print(double s){
    //     System.out.println(s);
    // }

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
