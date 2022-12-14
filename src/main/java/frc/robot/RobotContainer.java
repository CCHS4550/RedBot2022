package frc.robot;

import javax.print.attribute.standard.JobHoldUntil;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.helpers.OI;
import frc.parent.ControlMap;
import frc.robot.subsystems.*;
public class RobotContainer {
    //must instantiate an object of each subsystem you use
    private DriveTrain chassis = new DriveTrain();
    private Arms arms = new Arms();
    private Shooter shooter = new Shooter();

    Joystick[] controllers = OI.joystickArray;

    public RobotContainer(){
        configureButtons();
        chassis.setDefaultCommand(new RunCommand(() -> chassis.axisDrive(OI.axis(0, ControlMap.L_JOYSTICK_VERTICAL), OI.axis(0, ControlMap.R_JOYSTICK_HORIZONTAL)), chassis));
        // chassis.setDefaultCommand(new RunCommand(() -> chassis.print(OI.axis(0, ControlMap.L_JOYSTICK_VERTICAL)), chassis));
        shooter.setDefaultCommand(new RunCommand(() -> {
            shooter.shoot(OI.axis(1, ControlMap.RT) != 0 ? OI.axis(1, ControlMap.RT) : (OI.button(1, ControlMap.A_BUTTON) ? -1 : 0));
        }, shooter));
        // shooter.setDefaultCommand(new ConditionalCommand(shooter.shoot(OI.axis(1, ControlMap.RT)), shooter.shoot(OI.axis(1, ControlMap.RT) * -1), //a buttonr pressed  shooter));
        // arms.setDefaultCommand(new RunCommand(() -> arms.setSpeed(OI.dPadAng(1) > -1 ? Math.cos(Math.toRadians(OI.dPadAng(1))) : 0), arms));
        
        //arms.setDefualtCommand(new RunCommand(() -> arms.setSpeed(OI.dPad(1, )), arms);
    } 
        //This is Tyler's contribution to this code, you're welcome!!!
    private void configureButtons() {
        //this is how you do teleop stuff
        //when you create a trigger or trigger subclass, it will run whatever you want run when you specify
        
        //https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/Button.html 
        //list of modifiers to control what happens for trigger objects (joystickbuttons extend trigger)

        //https://first.wpi.edu/wpilib/allwpilib/docs/release/java/edu/wpi/first/wpilibj2/command/button/Trigger.html 
        //triggers are like buttons, but you can control when they go off
        //any logic amongst triggers must be done with .and, .negate, and others
        //see link for full list of logic operators

        // final double SHOOTER_INACTIVE_VALUE = 0.0;
        // final double SHOOTER_ACTIVE_VALUE = 0.5;
        // final double ACTIVE_ANALOG_CONSTANT = .15;


        new JoystickButton(controllers[0], ControlMap.A_BUTTON)
         .whenPressed(() -> chassis.toggleFastMode());

        new JoystickButton(controllers[1], ControlMap.A_BUTTON)
         .whenPressed(() -> arms.toggleArms());

    //     new Trigger(){
    //         @Override
    //         public boolean get(){
    //             return OI.axis(1, ControlMap.RT)  > ACTIVE_ANALOG_CONSTANT;
    //         }
    //     }.whenActive(() -> shooter.shoot(SHOOTER_ACTIVE_VALUE))
    //     .whenInactive(() -> shooter.shoot(SHOOTER_INACTIVE_VALUE));
    }
        


        

    void test(){

    }

    public Command getAutoCommand(){
        //see Autonomous class for more details
        return new Autonomous(chassis, shooter);
    }
}
