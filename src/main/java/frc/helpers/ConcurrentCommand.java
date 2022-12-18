package frc.helpers;

import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RunCommand;

public class ConcurrentCommand extends CommandBase{
    Command[] commands;
    public ConcurrentCommand(Command... commands){
        this.commands = commands;
        for(Command c : commands){
            for(Subsystem s : c.getRequirements()){
                addRequirements(s);
            }
        }
    }

    public void execute(){
        for(Command c : commands){
            c.execute();
        }
    }
    
    public boolean isFinished(){
        for(Command c : commands){
            if(!c.isFinished())
                return false;
        }
        return true;
    }
}
