package com.example.ASM2WebArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Asm2WebArrayList1ApplicationTests {

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentService();
    }
    @Test
    void contextLoads() {
    }

    @Test
    void testAddStudent() {
        Student student = new Student("1", "Alice", 8.5);
        studentService.addStudent(student);

        assertEquals(1, studentService.getAllStudents().size());
        assertEquals("Alice", studentService.getAllStudents().get(0).getName());
        assertEquals("Very Good", studentService.getAllStudents().get(0).getRank());
    }
    @Test
    void testFindStudentById() {
        Student student = new Student("1", "Alice", 8.5);
        studentService.addStudent(student);

        Student foundStudent = studentService.findStudentById("1");
        assertNotNull(foundStudent);
        assertEquals("Alice", foundStudent.getName());
    }

    @Test
    void testFindStudentById_NotFound() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.findStudentById("999"); // Non-existent ID
        });
        assertEquals("Error retrieving student details. Please try again.", exception.getMessage());
    }
    @Test
    void testUpdateStudent() {
        Student student = new Student("1", "Alice", 8.5);
        studentService.addStudent(student);

        student.setName("Alice Updated");
        student.setMarks(9.2);
        studentService.updateStudent(student);

        Student updatedStudent = studentService.findStudentById("1");
        assertEquals("Alice Updated", updatedStudent.getName());
        assertEquals("Excellent", updatedStudent.getRank());
    }

    @Test
    void testDeleteStudent() {
        Student student = new Student("1", "Alice", 8.5);
        studentService.addStudent(student);

        studentService.deleteStudent("1");

        assertEquals(0, studentService.getAllStudents().size());
    }

    @Test
    void testDeleteStudent_NotFound() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudent("999"); // Non-existent ID
        });
        assertEquals("Error deleting student. Please try again.", exception.getMessage());
    }

    @Test
    void testCalculateRank() {
        assertEquals("Fail", studentService.calculateRank(4.0));
        assertEquals("Medium", studentService.calculateRank(6.0));
        assertEquals("Good", studentService.calculateRank(7.0));
        assertEquals("Very Good", studentService.calculateRank(8.0));
        assertEquals("Excellent", studentService.calculateRank(9.5));
    }

    @Test
    void testSortStudentsByName() {
        Student student1 = new Student("1", "Charlie", 7.0);
        Student student2 = new Student("2", "Alice", 8.5);
        studentService.addStudent(student1);
        studentService.addStudent(student2);

        List<Student> sortedStudents = studentService.sortStudentsByName(true);
        assertEquals("Alice", sortedStudents.get(0).getName());
        assertEquals("Charlie", sortedStudents.get(1).getName());
    }
}
