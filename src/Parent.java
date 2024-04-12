import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Parent {
        private String x;
        private String y;
    public Parent(String x, String y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = this.getClass();

        // Collect fields from all parent classes
        while (currentClass != null) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            fields.addAll(Arrays.asList(declaredFields));
            currentClass = currentClass.getSuperclass();
        }

        return fields.stream()
                .map(field -> {
                    field.setAccessible(true); // You need this if fields are private
                    try {
                        return field.getName() + "=" + field.get(this);
                    } catch (IllegalAccessException e) {
                        return field.getName() + "=access error";
                    }
                })
                .collect(Collectors.joining(", ", "{", "}"));
    }

}
