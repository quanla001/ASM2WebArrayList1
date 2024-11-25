package com.example.ASM2WebArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddStudent_whenMarksGreaterThan10_shouldReturnError() throws Exception {
        mockMvc.perform(post("/students/add")
                        .param("name", "John Doe")
                        .param("marks", "11"))
                .andExpect(status().isBadRequest()) // Assuming error returns BadRequest status
                .andExpect(model().attributeExists("error"))
                .andExpect(model().attribute("error", "Marks cannot exceed 10."));
    }
}
