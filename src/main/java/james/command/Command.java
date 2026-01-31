package james.command;

import james.Storage;
import james.TaskList;
import james.Ui;
import james.exception.JamesException;

public abstract class Command {

    public abstract boolean isExit();

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws JamesException;
}