package example.commands;

import java.util.Scanner;

import example.script.ScriptExecutor;

/**
 * Команда выполнения скрипта из файла.
 */
public class ExecuteScriptCommand extends BaseCommand {
    private final ScriptExecutor scriptExecutor;

    public ExecuteScriptCommand(ScriptExecutor scriptExecutor) {
        this.scriptExecutor = scriptExecutor;
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getDescription() {
        return "выполнить скрипт из файла";
    }

    @Override
    protected void doExecute(String argument, Scanner scanner) {
        if (argument == null) {
            System.err.println("Ошибка: укажите имя файла");
            return;
        }

        scriptExecutor.executeScript(argument, scanner);
    }
}