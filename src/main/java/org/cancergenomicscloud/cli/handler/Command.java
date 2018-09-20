package org.cancergenomicscloud.cli.handler;

import java.util.List;

/**
 * Created by Filip.
 */

public class Command {

	private final String commandCode;
	private final List<String> args;

	public static Command of(String commandCode, List<String> args) {
		return new Command(commandCode, args);
	}

	private Command(String commandCode, List<String> args) {
		this.commandCode = commandCode;
		this.args = args;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public List<String> getArgs() {
		return args;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Command command = (Command) o;

		if (commandCode != null ? !commandCode.equals(command.commandCode) : command.commandCode != null) return false;
		return args != null ? args.equals(command.args) : command.args == null;
	}

	@Override
	public int hashCode() {
		int result = commandCode != null ? commandCode.hashCode() : 0;
		result = 31 * result + (args != null ? args.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Command{" +
				"commandCode='" + commandCode + '\'' +
				", args=" + args +
				'}';
	}
}
