package ModelAndDTO;
import org.junit.jupiter.api.Test;
import ru.aston.dto.StudentDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StudentDTOTest {

    @Test
    void testGettersAndSetters() {
        // Создаем объект StudentDTO
        StudentDTO student = new StudentDTO();

        // Устанавливаем значения с помощью сеттеров
        student.setId(1L);
        student.setName("John Doe");
        student.setAge(20);
        student.setCourse(3);

        // Проверяем, что геттеры возвращают ожидаемые значения
        assertEquals(1L, student.getId());
        assertEquals("John Doe", student.getName());
        assertEquals(20, student.getAge());
        assertEquals(3, student.getCourse());
    }

    @Test
    void testToString() {
        // Создаем объект StudentDTO
        StudentDTO student = new StudentDTO(1L, "John Doe", 20, 3);

        // Проверяем метод toString()
        assertEquals("StudentDTO(id=1, name=John Doe, age=20, course=3)", student.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        // Создаем два объекта StudentDTO с одинаковыми полями
        StudentDTO student1 = new StudentDTO(1L, "John Doe", 20, 3);
        StudentDTO student2 = new StudentDTO(1L, "John Doe", 20, 3);

        // Проверяем методы equals() и hashCode()
        assertEquals(student1, student2);
        assertEquals(student1.hashCode(), student2.hashCode());

        // Создаем объект StudentDTO с различными полями
        StudentDTO student3 = new StudentDTO(2L, "Jane Smith", 25, 4);

        // Проверяем, что объекты с различными полями не равны
        assertNotEquals(student1, student3);
        assertNotEquals(student1.hashCode(), student3.hashCode());
    }

    @Test
    void testNullFields() {
        // Создаем объект StudentDTO с нулевыми значениями полей
        StudentDTO student = new StudentDTO();

        // Проверяем, что все поля равны null
        assertNull(student.getName());
    }


    @Test
    void testAllArgsConstructor() {
        // Создаем объект StudentDTO с помощью конструктора с параметрами
        StudentDTO student = new StudentDTO(1L, "John Doe", 20, 3);

        // Проверяем, что значения были установлены корректно
        assertEquals(1L, student.getId(), "ID should be 1");
        assertEquals("John Doe", student.getName(), "Name should be John Doe");
        assertEquals(20, student.getAge(), "Age should be 20");
        assertEquals(3, student.getCourse(), "Course should be 3");
    }

    @Test
    void testNoArgsConstructor() {
        // Создаем объект StudentDTO с помощью конструктора без параметров
        StudentDTO student = new StudentDTO();

        // Проверяем, что все поля равны значениям по умолчанию (0 для числовых типов, null для строк)
        assertEquals(0L, student.getId(), "ID should be 0");
        assertNull(student.getName(), "Name should be null");
        assertEquals(0, student.getAge(), "Age should be 0");
        assertEquals(0, student.getCourse(), "Course should be 0");
    }
}
