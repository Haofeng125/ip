package james.command;

import james.Storage;
import james.TaskList;
import james.Ui;
import james.exception.JamesException;

public class TerminateProgramCommand extends Command {

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws JamesException {
        storage.saveTasks(tasks);
        ui.bye();
    }
}