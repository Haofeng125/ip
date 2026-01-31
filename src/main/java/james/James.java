package james;

import james.command.Command;
import james.exception.JamesException;

public class James {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public James(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        try {
            this.tasks = this.storage.loadTasks();
        } catch (JamesException e) {
            ui.showJamesError(e);
            this.tasks = new TaskList();
        }
    }

    public void run() {
        this.ui.greet();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = this.ui.readCommand();
                this.ui.printDivider();
                Command command = Parser.parseCommand(fullCommand, this.tasks);
                command.execute(this.tasks, this.ui, this.storage);
                isExit = command.isExit();
            } catch (JamesException e) {
                this.ui.showJamesError(e);
            } finally {
                this.ui.printDivider();
            }
        }
    }

    public static void main(String[] args) {
        new James("./data/tasks.txt").run();
    }
}