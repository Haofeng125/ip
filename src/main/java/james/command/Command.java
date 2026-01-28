package james.command;

import james.exception.JamesException;
import james.*;

public abstract class Command {
    public abstract boolean isExit();
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws JamesException;
}