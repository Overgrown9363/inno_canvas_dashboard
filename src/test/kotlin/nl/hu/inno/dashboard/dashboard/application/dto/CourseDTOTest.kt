package nl.hu.inno.dashboard.dashboard.application.dto

import nl.hu.inno.dashboard.dashboard.domain.Course
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CourseDTOTest {
    @Test
    fun of_mapsCourseAndRoleToCourseDTO() {
        val course = Course(
            canvasCourseId = 123,
            courseName = "Test Course",
            courseCode = "TC101",
            instanceName = "2025-2026",
            startDate = LocalDate.of(2025, 1, 1),
            endDate = LocalDate.of(2025, 6, 30)
        )
        val dto = CourseDTO.of(course, "STUDENT")

        assertEquals(123, dto.canvasCourseId)
        assertEquals("Test Course", dto.courseName)
        assertEquals("TC101", dto.courseCode)
        assertEquals("2025-2026", dto.instanceName)
        assertEquals(LocalDate.of(2025, 1, 1), dto.startDate)
        assertEquals(LocalDate.of(2025, 6, 30), dto.endDate)
        assertEquals("STUDENT", dto.roleInCourse)
    }
}