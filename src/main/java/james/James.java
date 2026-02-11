package james;

import james.command.Command;
import james.exception.JamesException;

/**
 * Represents the main entry point and logic controller for the James chatbot.
 * Coordinates between Storage, TaskList, and Ui to manage user tasks.
 */
public class James {

    private static final String DEFAULT_FILE_PATH = "./data/tasks.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes the James application with a specific file path for data storage.
     *
     * @param filePath The path to the file where tasks are saved and loaded.
     */
    public James(String filePath) {
        validateConstructorParam(filePath);
        initComponents(filePath);
        loadTasksSafely();
        verifyInitialization();
    }

    /**
     * Validates that the provided constructor parameters are acceptable.
     *
     * @param filePath The file path string to check.
     */
    private void validateConstructorParam(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "File path cannot be null or empty";
    }

    /**
     * Verifies that the internal task list state has been successfully initialized.
     */
    private void verifyInitialization() {
        assert this.tasks != null : "Task list should be initialized";
    }

    /**
     * Initializes the core system components.
     * Uses default Ui constructor to ensure Scanner is initialized for Terminal mode.
     *
     * @param filePath The file path used to initialize the storage component.
     */
    private void initComponents(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
    }

    /**
     * Attempts to load existing tasks from storage.
     * If an error occurs during loading, an error message is shown and a new list is created.
     */
    private void loadTasksSafely() {
        try {
            this.tasks = this.storage.loadTasks();
        } catch (JamesException e) {
            this.ui.showJamesError(e);
            this.tasks = new TaskList();
        }
    }

    /**
     * Processes a single input string and returns a response.
     * Primarily used as the interface for GUI-based interactions.
     *
     * @param input The raw command string from the user.
     * @return The formatted response string from the chatbot.
     */
    public String getResponse(String input) {
        verifyGetResponseState(input);

        try {
            Command command = Parser.parseCommand(input, this.tasks);
            command.execute(this.tasks, this.ui, this.storage);
            return this.ui.getAndClearResponse();
        } catch (JamesException e) {
            return e.getMessage();
        }
    }

    /**
     * Verifies that all necessary components are ready before processing a GUI response.
     *
     * @param input The user input to check for nullity.
     */
    private void verifyGetResponseState(String input) {
        assert input != null : "Input command cannot be null";
        assert this.tasks != null : "Task list is not initialized";
        assert this.ui != null : "UI is not initialized";
        assert this.storage != null : "Storage is not initialized";
    }

    /**
     * Starts the application in CLI/Terminal mode.
     * Greets the user and enters the command processing loop.
     */
    public void run() {
        verifyRunState();
        this.ui.greet();
        runCommandLoop();
    }

    /**
     * Verifies that the UI component is ready for Terminal mode interaction.
     */
    private void verifyRunState() {
        assert this.ui != null : "UI must be initialized before running the application";
    }

    /**
     * Orchestrates the high-level loop for reading and executing commands.
     * Continues until a command signals the program to terminate.
     */
    private void runCommandLoop() {
        while (!processOneCommandCycle()) {
            // High-level loop body remains empty to satisfy SLAP
        }
    }

    /**
     * Handles one full cycle of user interaction: reading and then executing.
     *
     * @return true if the program should exit, false otherwise.
     */
    private boolean processOneCommandCycle() {
        String fullCommand = readUserCommand();
        return executeUserCommand(fullCommand);
    }

    /**
     * Fetches a command from the UI and applies a visual divider.
     *
     * @return The command string entered by the user.
     */
    private String readUserCommand() {
        String fullCommand = this.ui.readCommand();
        this.ui.printDivider();
        return fullCommand;
    }

    /**
     * Coordinates parsing and execution logic for a command.
     * Includes error handling and visual formatting.
     *
     * @param fullCommand The raw user input string.
     * @return true if the command is an exit command, false otherwise.
     */
    private boolean executeUserCommand(String fullCommand) {
        try {
            Command command = Parser.parseCommand(fullCommand, this.tasks);
            command.execute(this.tasks, this.ui, this.storage);
            this.ui.getAndClearResponse();
            return command.isExit();
        } catch (JamesException e) {
            this.ui.showJamesError(e);
            return false;
        } finally {
            this.ui.printDivider();
        }
    }

    /**
     * Main entry point for the terminal application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new James(DEFAULT_FILE_PATH).run();
    }
}
