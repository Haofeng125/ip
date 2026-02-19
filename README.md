# James User Guide

_James_ is a chatbot that can help you handle job scheduling like school works and daily business.
Given below are instructions on how to use it.

## Setting up the project
Prerequisites:
+ For windows: JDK 17
+ For Mac: [Java 17 JDK+FX Azul distribution](https://www.azul.com/downloads/?version=java-17-lts&os=macos&package=jdk-fx#zulu)

1. Clone the Repository.
```
git clone https://github.com/yourusername/james.git
```
2. Make sure your terminal is in the project directory, and then create JAR file.
```
./gradlew clean shadowJar
```
3. Create data folder and tasks file inside the libs directory.
```
cd build/libs
mkdir data
touch data/tasks.txt
```
4. Execute the JAR file.
```
java -jar james.jar
```
if you didn't create the directory and the file in the last step, you might face some error message saying "cannot find the file". Don't worry, the first run of the program will automatically create the directory and the file for the user, and if you run it again, you will find out that the problem is solved.

## Operational Semantics
### 1. List
type `list` in the chatbox to view the current full task list.
### 2. Todo
type `todo [description]` in the chatbox to create a new "Todo" task with the given description.
### 3. Deadline
type `deadline [description] /by [yyyy-mm-dd]` in the chatbox to create a new "Deadline" task with the given description and due date.
### 4. Event
type `event [description] /from [start time] /to [end time]` in the chatbox to create a new "Event" task with the given description, start time, and end time.
### 5. Mark
type `mark [task number]` in the chatbox to mark the according task as "done".
### 6. Unmark
type `unmark [task number]` in the chatbox to mark the according task as "undone".
### 7. Delete
type `delete [task number` in the chatbox to delete the according task.
### 8. Tag
type `tag [task number] [tag content]` in the chatbox to add a tag with given _tag content_ to the according task.
>Please note that you can create multiple tags for one single task.
### 9. Bye
type `bye` in the chatbox to terminate the program and save the task list to the file _"tasks.txt"_.

## Development Workflow
### Project Structure
The source code is organized to facilitate scalability.
+ `src/main/java/james`:Application source code.
+ `src/test/java/james`: JUnit test suite.
### Testing Strategy
I adhere to a rigorous testing standard. All pull requests must pass the existing test suite.
+ To run all unit and integration tests: `./gradlew test`