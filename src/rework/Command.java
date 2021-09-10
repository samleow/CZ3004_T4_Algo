package src.rework;

public class Command
{
	public enum CommandType
	{
		WAIT,
		MOVE,
		TURN
	}
	
	public CommandType command_type = CommandType.WAIT;
	public double arg1 = 0.0;
	public double arg2 = 0.0;
	
	public Command(CommandType cType, double arg1)
	{
		command_type = cType;
		this.arg1 = arg1;
	}
	
	public Command(CommandType cType, double arg1, double arg2)
	{
		command_type = cType;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	@Override
	public String toString()
	{
		return "[ " + command_type + ", " + arg1 + " ]";
	}
	
}
