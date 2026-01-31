package james.task;

import java.time.LocalDate;

public class Deadline extends Task {
    private LocalDate deadlineTime;

    public Deadline(String description, LocalDate deadlineTime) {
        super(description);
        this.deadlineTime = deadlineTime;
    }

    @Override
    public String toString() {
        String monthName = this.deadlineTime.getMonth().name();
        monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();

        int year = this.deadlineTime.getYear();
        int day = this.deadlineTime.getDayOfMonth();

        return String.format("[D]%s (by: %s %s %d)", super.toString(), monthName, day, year);
    }

    @Override
    public String toFileFormat() {
        int year = this.deadlineTime.getYear();
        int month = this.deadlineTime.getMonthValue();
        int day = this.deadlineTime.getDayOfMonth();

        return String.format("D | %s | %04d-%02d-%02d", super.toFileFormat(), year, month, day);
    }
}