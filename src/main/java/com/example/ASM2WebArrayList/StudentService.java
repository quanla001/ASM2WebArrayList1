package com.example.ASM2WebArrayList;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private List<Student> students = new ArrayList<>(); // In-memory list to store students

    // Method to add a student
    public void addStudent(Student student) {
        try {
            if (student.getMarks() < 0 || student.getMarks() > 10) {
                throw new IllegalArgumentException("Marks must be between 0 and 10.");
            }
            student.setRank(calculateRank(student.getMarks())); // Set rank based on marks
            students.add(student);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error adding student: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred. Please try again.");
        }
    }

    // Method to find a student by ID
    public Student findStudentById(String id) {
        try {
            Optional<Student> student = students.stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst();
            if (!student.isPresent()) {
                throw new IllegalArgumentException("Student not found with ID: " + id);
            }
            return student.get();
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error finding student: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while finding student.");
        }
    }

    // Method to update a student
    public void updateStudent(Student student) {
        try {
            Student existingStudent = findStudentById(student.getId());
            if (existingStudent == null) {
                throw new IllegalArgumentException("Student not found with ID: " + student.getId());
            }
            if (student.getMarks() < 0 || student.getMarks() > 10) {
                throw new IllegalArgumentException("Marks must be between 0 and 10.");
            }
            existingStudent.setName(student.getName());
            existingStudent.setMarks(student.getMarks());
            existingStudent.setRank(calculateRank(student.getMarks())); // Update rank after editing
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error updating student: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while updating student.");
        }
    }

    // Method to delete a student
    public void deleteStudent(String id) {
        try {
            Student student = findStudentById(id);
            if (student == null) {
                throw new IllegalArgumentException("Student not found with ID: " + id);
            }
            students.remove(student);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error deleting student: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while deleting student.");
        }
    }

    // Method to get all students
    public List<Student> getAllStudents() {
        try {
            return new ArrayList<>(students);
        } catch (Exception e) {
            System.err.println("Error retrieving all students: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while fetching students.");
        }
    }

    // Method to calculate rank based on marks
    public String calculateRank(double marks) {
        try {
            if (marks < 0 || marks > 10) {
                throw new IllegalArgumentException("Marks must be between 0 and 10.");
            }
            if (marks < 5.0) return "Fail";
            if (marks < 6.5) return "Medium";
            if (marks < 7.5) return "Good";
            if (marks < 9.0) return "Very Good";
            return "Excellent";
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
            throw e;
        }
    }

    // Sort students by name using Bubble Sort
    public List<Student> sortStudentsByName(boolean ascending) {
        try {
            List<Student> sortedStudents = new ArrayList<>(students);
            int n = sortedStudents.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    boolean condition = ascending
                            ? sortedStudents.get(j).getName().compareTo(sortedStudents.get(j + 1).getName()) > 0
                            : sortedStudents.get(j).getName().compareTo(sortedStudents.get(j + 1).getName()) < 0;
                    if (condition) {
                        Student temp = sortedStudents.get(j);
                        sortedStudents.set(j, sortedStudents.get(j + 1));
                        sortedStudents.set(j + 1, temp);
                    }
                }
            }
            return sortedStudents;
        } catch (Exception e) {
            System.err.println("Error sorting students by name: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while sorting students by name.");
        }
    }

    // Sort students by marks using Bubble Sort
    public List<Student> sortStudentsByMarks(boolean ascending) {
        try {
            List<Student> sortedStudents = new ArrayList<>(students);
            int n = sortedStudents.size();
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    boolean condition = ascending
                            ? sortedStudents.get(j).getMarks() > sortedStudents.get(j + 1).getMarks()
                            : sortedStudents.get(j).getMarks() < sortedStudents.get(j + 1).getMarks();
                    if (condition) {
                        Student temp = sortedStudents.get(j);
                        sortedStudents.set(j, sortedStudents.get(j + 1));
                        sortedStudents.set(j + 1, temp);
                    }
                }
            }
            return sortedStudents;
        } catch (Exception e) {
            System.err.println("Error sorting students by marks: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while sorting students by marks.");
        }
    }

    // Search students by name
    public List<Student> searchStudentsByName(String name) {
        try {
            if (name == null || name.isEmpty()) {
                throw new IllegalArgumentException("Search name cannot be null or empty.");
            }
            return students.stream()
                    .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                    .toList();
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("Error searching students: " + e.getMessage());
            throw new RuntimeException("Unexpected error occurred while searching students.");
        }
    }
}
