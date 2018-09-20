package org.cancergenomicscloud.cli.handler;

import java.util.List;
import java.util.Objects;

/**
 * Created by Filip.
 */

public class Command {

	private final String commandCode;
	private final String authToken;
	private final List<String> args;

	public static Command of(String commandCode, String authToken, List<String> args) {
		return new Command(commandCode, authToken, args);
	}

	private Command(String commandCode, String authToken, List<String> args) {
		this.commandCode = commandCode;
		this.authToken = authToken;
		this.args = args;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public List<String> getArgs() {
		return args;
	}

	public String getAuthToken() {
		return authToken;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Command command = (Command) o;
		return Objects.equals(commandCode, command.commandCode) &&
				Objects.equals(authToken, command.authToken) &&
				Objects.equals(args, command.args);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commandCode, authToken, args);
	}

	@Override
	public String toString() {
		return "Command{" +
				"commandCode='" + commandCode + '\'' +
				", authToken='" + authToken + '\'' +
				", args=" + args +
				'}';
	}
}
