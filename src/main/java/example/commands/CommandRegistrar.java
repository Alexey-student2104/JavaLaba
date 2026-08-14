package example.commands;

import example.collection.CityCollection;
import example.script.ScriptExecutor;

/**
 * Класс для регистрации всех команд.
 * Собирает создание, настройку и регистрацию команд в одном месте.
 * Соблюдает принцип Single Responsibility.
 */
public class CommandRegistrar {

    private final CommandManager manager;
    private final CityCollection collection;
    private final ScriptExecutor scriptExecutor;

    public CommandRegistrar(CommandManager manager, CityCollection collection, ScriptExecutor scriptExecutor) {
        this.manager = manager;
        this.collection = collection;
        this.scriptExecutor = scriptExecutor;
    }

    /**
     * Создаёт, настраивает и регистрирует все команды.
     */
    public void registerAll() {
        // 1. Создаём все команды
        HelpCommand help = new HelpCommand();
        InfoCommand info = new InfoCommand();
        ShowCommand show = new ShowCommand();
        InsertCommand insert = new InsertCommand();
        UpdateCommand update = new UpdateCommand();
        RemoveKeyCommand removeKey = new RemoveKeyCommand();
        ClearCommand clear = new ClearCommand();
        SaveCommand save = new SaveCommand();
        ExecuteScriptCommand executeScript = new ExecuteScriptCommand(scriptExecutor);
        ExitCommand exit = new ExitCommand();
        ReplaceIfLowerCommand replaceIfLower = new ReplaceIfLowerCommand();
        RemoveGreaterKeyCommand removeGreaterKey = new RemoveGreaterKeyCommand();
        RemoveLowerKeyCommand removeLowerKey = new RemoveLowerKeyCommand();
        MinByGovernorCommand minByGovernor = new MinByGovernorCommand();
        CountByTimezoneCommand countByTimezone = new CountByTimezoneCommand();
        PrintFieldAscendingTimezoneCommand printFieldAscendingTimezone = new PrintFieldAscendingTimezoneCommand();

        // 2. Устанавливаем коллекцию для команд, которым она нужна
        BaseCommand[] withCollection = {
            info, show, insert, update, removeKey, clear, save,
            replaceIfLower, removeGreaterKey, removeLowerKey,
            minByGovernor, countByTimezone, printFieldAscendingTimezone
        };
        for (BaseCommand cmd : withCollection) {
            cmd.setCollection(collection);
        }

        // 3. Регистрируем все команды
        manager.register(help);
        manager.register(info);
        manager.register(show);
        manager.register(insert);
        manager.register(update);
        manager.register(removeKey);
        manager.register(clear);
        manager.register(save);
        manager.register(executeScript);
        manager.register(exit);
        manager.register(replaceIfLower);
        manager.register(removeGreaterKey);
        manager.register(removeLowerKey);
        manager.register(minByGovernor);
        manager.register(countByTimezone);
        manager.register(printFieldAscendingTimezone);
    }

    /**
     * Возвращает менеджер команд со всеми зарегистрированными командами.
     */
    public CommandManager getManager() {
        return manager;
    }
}