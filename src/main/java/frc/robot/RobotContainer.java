package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

    public RobotContainer(){
        configureButtons();
    } 

    private void configureButtons() {

    }

    void test(){

    }

    public Command getAutoCommand(){
        return new Autonomous();
    }
}
