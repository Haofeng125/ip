package james;

import james.command.Command;
import james.exception.JamesException;

/**
 * Represents the main logic of the James chatbot application.
 * James is a task management assistant that helps users track todos, deadlines, and events.
 * It coordinates the User Interface, Storage, and TaskList components.
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
        this.ui = new Ui(filePath);
        try {
            this.tasks = this.storage.loadTasks();
        } catch (JamesException e) {
            ui.showJamesError(e);
            this.tasks = new TaskList();
        }
    }

    /**
     * Generates a response for the GUI by processing user input.
     * This is the main entry point for the FXML-based interface.
     *
     * @param input The raw user input string from the GUI.
     * @return James's formatted response.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parseCommand(input, this.tasks);
            command.execute(this.tasks, this.ui, this.storage);
            return ui.getAndClearResponse(); // This grabs everything from our new buffer!
        } catch (JamesException e) {
            return e.getMessage();
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
                this.ui.getAndClearResponse();
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
