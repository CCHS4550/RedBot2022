package frc.robot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Autonomous extends SequentialCommandGroup{
    //auto is set up such that it will run when you want it to

    //inside the constructor you have to put an object of each subsystem you plan to use
    public Autonomous(DriveTrain chassis, Shooter shooter){
        //put all commands within this super.addcommands
        //make note that it uses commas instead of semicolons because you're technically adding them in a list
        super.addCommands(
            //whatever you put here must extend from command
            //https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/package-summary.html
            //check link for relevant subclasses 
            chassis.turnAngle(45),
            chassis.moveTo(7),
            chassis.turnAngle(-90),
            shooter.shoot(0.5)
        );
    }
}