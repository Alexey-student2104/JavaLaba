package example.commands;

import java.util.Scanner;

import example.script.ScriptExecutor;

public class ExecuteScriptCommand implements Command {
    private ScriptExecutor scriptExecutor;

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
    public void execute(String argument, Scanner scanner, Object... dependencies) {
        if (argument == null) {
            System.err.println("Ошибка: укажите имя файла");
            return;
        }
        scriptExecutor.executeScript(argument, scanner);
    }
}