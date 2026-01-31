package james;

import james.exception.JamesException;
import james.command.Command;

/**
 * Represents the main entry point for the James chatbot application.
 * James is a task management tool that handles todos, deadlines, and events.
 * James can also handle jobs such as mark, unmark and delete.
 */
public class James {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes a new James chatbot instance.
     * Sets up the user interface and storage, and attempts to load existing tasks from the disk.
     *
     * @param filePath The file path where task data is stored and loaded from.
     */
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

    /**
     * Starts the main program loop.
     * Displays a greeting and continuously reads and executes user commands
     * until an exit command is issued.
     */
    public void run() {
        this.ui.greet();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = this.ui.readCommand();
                this.ui.printDivider();
                Command c = Parser.parseCommand(fullCommand, this.tasks);
                c.execute(this.tasks, this.ui, this.storage);
                isExit = c.isExit();
            } catch (JamesException e) {
                this.ui.showJamesError(e);
            } finally {
                this.ui.printDivider();
            }
        }
    }

    /**
     * The main method that serves as the entry point for the application.
     * Initializes the James application with a default data file path.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new James("./data/tasks.txt").run();
    }
}