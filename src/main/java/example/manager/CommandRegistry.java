package example.manager;

import example.collection.CityCollection;
import example.commands.*;
import example.input.CityReader;
import example.script.ScriptExecutor;
import example.storage.StorageService;

public class CommandRegistry {
    private final CommandManager manager;
    private final CityCollection collection;
    private final CityReader reader;
    private final ScriptExecutor scriptExecutor;
    private final StorageService storage;

    public CommandRegistry(CommandManager manager, CityCollection collection,
                           CityReader reader, ScriptExecutor scriptExecutor,
                           StorageService storage) {
        this.manager = manager;
        this.collection = collection;
        this.reader = reader;
        this.scriptExecutor = scriptExecutor;
        this.storage = storage;
    }

    public void registerAll() {
        HelpCommand help = new HelpCommand();
        InfoCommand info = new InfoCommand(collection);
        ShowCommand show = new ShowCommand(collection);
        InsertCommand insert = new InsertCommand(collection, reader);
        UpdateCommand update = new UpdateCommand(collection, reader);
        RemoveKeyCommand removeKey = new RemoveKeyCommand(collection);
        ClearCommand clear = new ClearCommand(collection);
        SaveCommand save = new SaveCommand(collection, storage);
        ExecuteScriptCommand executeScript = new ExecuteScriptCommand(scriptExecutor);
        ExitCommand exit = new ExitCommand();
        ReplaceIfLowerCommand replaceIfLower = new ReplaceIfLowerCommand(collection, reader);
        RemoveGreaterKeyCommand removeGreaterKey = new RemoveGreaterKeyCommand(collection);
        RemoveLowerKeyCommand removeLowerKey = new RemoveLowerKeyCommand(collection);
        MinByGovernorCommand minByGovernor = new MinByGovernorCommand(collection);
        CountByTimezoneCommand countByTimezone = new CountByTimezoneCommand(collection);
        PrintFieldAscendingTimezoneCommand printFieldAscendingTimezone = new PrintFieldAscendingTimezoneCommand(collection);

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
}