package example.model;

/**
 * Перечисление уровней жизни.
 */
public enum StandardOfLiving {
    ULTRA_HIGH,
    MEDIUM,
    ULTRA_LOW;

    /**
     * Возвращает строку со всеми доступными значениями.
     */
    public static String getAvailableValues() {
        StringBuilder sb = new StringBuilder();
        for (StandardOfLiving sol : values()) {
            sb.append(sol.name()).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }
}