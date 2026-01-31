package james;

import james.command.Command;
import james.exception.JamesException;

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