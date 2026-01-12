package nl.hu.inno.dashboard.dashboard.application.dto

import nl.hu.inno.dashboard.dashboard.domain.AppRole
import nl.hu.inno.dashboard.dashboard.domain.Course
import nl.hu.inno.dashboard.dashboard.domain.CourseRole
import nl.hu.inno.dashboard.dashboard.domain.UserInCourse
import nl.hu.inno.dashboard.dashboard.domain.Users
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UsersDTOTest {
    private lateinit var course: Course
    private lateinit var courseRole: CourseRole
    private lateinit var userInCourse: UserInCourse

    @BeforeEach
    fun setUp() {
        course = Course(
            canvasCourseId = 1,
            courseName = "Course 1",
            courseCode = "C1",
            instanceName = "2025",
            startDate = LocalDate.of(2025, 2, 1),
            endDate = LocalDate.of(2025, 7, 1)
        )
        courseRole = CourseRole.TEACHER
        userInCourse = UserInCourse(
            course = course,
            courseRole = courseRole
        )
    }

    @Test
    fun of_mapsUserToUsersDTO_withCourses() {
        val user = Users(
            email = "user@hu.nl",
            name = "User Name",
            appRole = AppRole.USER,
            userInCourse = mutableSetOf(userInCourse)
        )

        val dto = UsersDTO.of(user)

        assertEquals("user@hu.nl", dto.email)
        assertEquals("User Name", dto.name)
        assertEquals("USER", dto.appRole)
        assertEquals(1, dto.courses.size)
        val courseDTO = dto.courses[0]
        assertEquals(1, courseDTO.canvasCourseId)
        assertEquals("TEACHER", courseDTO.roleInCourse)
    }

    @Test
    fun of_mapsUserToUsersDTO_withNoCourses() {
        val user = Users(
            email = "user2@hu.nl",
            name = "User Two",
            appRole = AppRole.USER,
            userInCourse = mutableSetOf()
        )

        val dto = UsersDTO.of(user)

        assertEquals("user2@hu.nl", dto.email)
        assertEquals("User Two", dto.name)
        assertEquals("USER", dto.appRole)
        assertTrue(dto.courses.isEmpty())
    }

    @Test
    fun of_skipsCoursesWithNullCourseOrRole() {
        val userInCourseWithNullCourse = UserInCourse(
            course = null,
            courseRole = CourseRole.STUDENT
        )
        val userInCourseWithNullRole = UserInCourse(
            course = course,
            courseRole = null
        )
        val user = Users(
            email = "user3@hu.nl",
            name = "User Three",
            appRole = AppRole.USER,
            userInCourse = mutableSetOf(userInCourseWithNullCourse, userInCourseWithNullRole)
        )

        val dto = UsersDTO.of(user)

        assertTrue(dto.courses.isEmpty())
    }
}