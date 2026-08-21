package example.commands;

import java.util.Scanner;

public interface Command {
    String getName();
    String getDescription();
    void execute(String argument, Scanner scanner, Object... dependencies);
}