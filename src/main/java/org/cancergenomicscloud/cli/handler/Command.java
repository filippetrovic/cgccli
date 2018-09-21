package org.cancergenomicscloud.cli.handler;

import java.util.Map;
import java.util.Objects;

/**
 * Created by Filip.
 */

public class Command {

	private final String commandCode;
	private final String authToken;
	private final Map<String, String> commandArguments;
	private final Map<String, String> commandKeyValues;

	public static Command of(String commandCode,
							 String authToken,
							 Map<String, String> commandArguments,
							 Map<String, String> commandKeyValues) {

		return new Command(commandCode, authToken, commandArguments, commandKeyValues);
	}

	private Command(String commandCode,
					String authToken,
					Map<String, String> commandArguments,
					Map<String, String> commandKeyValues) {

		this.commandCode = commandCode;
		this.authToken = authToken;
		this.commandArguments = commandArguments;
		this.commandKeyValues = commandKeyValues;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public String getAuthToken() {
		return authToken;
	}

	public Map<String, String> getCommandArguments() {
		return commandArguments;
	}

	public Map<String, String> getCommandKeyValues() {
		return commandKeyValues;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Command command = (Command) o;
		return Objects.equals(commandCode, command.commandCode) &&
				Objects.equals(authToken, command.authToken) &&
				Objects.equals(commandArguments, command.commandArguments) &&
				Objects.equals(commandKeyValues, command.commandKeyValues);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commandCode, authToken, commandArguments, commandKeyValues);
	}

	@Override
	public String toString() {
		return "Command{" +
				"commandCode='" + commandCode + '\'' +
				", authToken='" + authToken + '\'' +
				", commandArguments=" + commandArguments +
				", commandKeyValues=" + commandKeyValues +
				'}';
	}
}
