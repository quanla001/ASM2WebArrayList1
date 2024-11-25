package com.example.ASM2WebArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    private String currentSortOrderName = "asc"; // Default sorting order for names
    private String currentSortOrderMarks = "asc"; // Default sorting order for marks

    // Show the student list
    @GetMapping
    public String listStudents(Model model, @RequestParam(required = false) String search) {
        try {
            List<Student> students = studentService.getAllStudents();

            // Filter students if search query is present
            if (search != null && !search.isEmpty()) {
                students = students.stream()
                        .filter(student -> student.getName().toLowerCase().contains(search.toLowerCase()))
                        .collect(Collectors.toList());
            }
            model.addAttribute("students", students);
        } catch (Exception e) {
            model.addAttribute("error", "Error fetching students: " + e.getMessage());
        }

        model.addAttribute("currentSortOrderName", currentSortOrderName);
        model.addAttribute("currentSortOrderMarks", currentSortOrderMarks);
        model.addAttribute("search", search); // Add search term to model for the view
        return "index"; // Name of the main student list view
    }


    // Add a new student
    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student, Model model) {
        try {
            studentService.addStudent(student);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Validation Error: " + e.getMessage());
            return "addStudent"; // Return to the add form with an error message
        } catch (Exception e) {
            model.addAttribute("error", "Unexpected Error: " + e.getMessage());
            return "addStudent";
        }
        return "redirect:/students";
    }

    // Display edit form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        try {
            Student student = studentService.findStudentById(id);
            model.addAttribute("student", student);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Validation Error: " + e.getMessage());
            return "redirect:/students";
        } catch (Exception e) {
            model.addAttribute("error", "Unexpected Error: " + e.getMessage());
            return "redirect:/students";
        }
        return "editStudent"; // Name of the edit view
    }

    // Handle edit form submission
    @PostMapping("/edit")
    public String editStudent(@ModelAttribute Student student, Model model) {
        try {
            studentService.updateStudent(student);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Validation Error: " + e.getMessage());
            return "editStudent"; // Return to the edit form with an error message
        } catch (Exception e) {
            model.addAttribute("error", "Unexpected Error: " + e.getMessage());
            return "editStudent";
        }
        return "redirect:/students";
    }

    // Delete a student
    @PostMapping("/delete")
    public String deleteStudent(@RequestParam String id, Model model) {
        try {
            studentService.deleteStudent(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "Validation Error: " + e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "Unexpected Error: " + e.getMessage());
        }
        return "redirect:/students";
    }


    // Sort students by name, with search persistence
    @GetMapping("/sort/name")
    public String sortStudentsByName(Model model, @RequestParam(required = false) String search) {
        try {
            currentSortOrderName = currentSortOrderName.equals("asc") ? "desc" : "asc"; // Toggle order
            List<Student> students = studentService.sortStudentsByName(currentSortOrderName.equals("asc"));

            // Apply search filter if present
            if (search != null && !search.isEmpty()) {
                students = students.stream()
                        .filter(student -> student.getName().toLowerCase().contains(search.toLowerCase()))
                        .collect(Collectors.toList());
            }
            model.addAttribute("students", students);
        } catch (Exception e) {
            model.addAttribute("error", "Error sorting students by name: " + e.getMessage());
        }

        model.addAttribute("currentSortOrderName", currentSortOrderName);
        model.addAttribute("currentSortOrderMarks", currentSortOrderMarks);
        model.addAttribute("search", search);
        return "index";
    }

    // Sort students by marks, with search persistence
    @GetMapping("/sort/marks")
    public String sortStudentsByMarks(Model model, @RequestParam(required = false) String search) {
        try {
            currentSortOrderMarks = currentSortOrderMarks.equals("asc") ? "desc" : "asc"; // Toggle order
            List<Student> students = studentService.sortStudentsByMarks(currentSortOrderMarks.equals("asc"));

            // Apply search filter if present
            if (search != null && !search.isEmpty()) {
                students = students.stream()
                        .filter(student -> student.getName().toLowerCase().contains(search.toLowerCase()))
                        .collect(Collectors.toList());
            }
            model.addAttribute("students", students);
        } catch (Exception e) {
            model.addAttribute("error", "Error sorting students by marks: " + e.getMessage());
        }

        model.addAttribute("currentSortOrderName", currentSortOrderName);
        model.addAttribute("currentSortOrderMarks", currentSortOrderMarks);
        model.addAttribute("search", search);
        return "index";
    }

}
