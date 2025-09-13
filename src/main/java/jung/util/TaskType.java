package jung.util;

/**
 * Enumeration of supported task types with their symbolic representations.
 */
public enum TaskType {
    TODO('T'),
    DEADLINE('D'),
    EVENT('E');

    private final char symbol;

    TaskType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static TaskType fromSymbol(char symbol) {
        for (TaskType type : values()) {
            if (type.symbol == symbol) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown task type symbol: " + symbol);
    }
}