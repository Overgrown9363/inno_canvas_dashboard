package nl.hu.inno.dashboard.dashboard.application

import nl.hu.inno.dashboard.dashboard.data.CourseRepository
import nl.hu.inno.dashboard.dashboard.data.UsersRepository
import nl.hu.inno.dashboard.dashboard.domain.Course
import nl.hu.inno.dashboard.dashboard.domain.Users
import nl.hu.inno.dashboard.fileparser.application.FileParserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import nl.hu.inno.dashboard.dashboard.domain.Role
import nl.hu.inno.dashboard.dashboard.domain.exception.InvalidParseListException
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DashboardServiceImpl(
    private val courseDB: CourseRepository,
    private val usersDB: UsersRepository,
    private val fileParserService: FileParserService
) : DashboardService {
    override fun findCourseById(id: Int): Course? {
        return courseDB.findByIdOrNull(id)
    }

    override fun parseAndPersistCanvasData(file: MultipartFile) {
        val rows = fileParserService.parseFile(file)

        val courseList = rows.map { convertToCourse(it) }.distinct()
        val userList = rows.map { convertToUser(it) }.distinct()

        val newCourses = checkForDuplicateCourses(courseList)
        val newUsers = checkForDuplicateUsers(userList)

        courseDB.saveAll(newCourses)
        usersDB.saveAll(newUsers)
    }

    private fun convertToUser(columns: List<String>): Users {
        if (columns.size != 7) {
            throw InvalidParseListException("Expected record to have 7 columns, got ${columns.size}")
        }

        val name = columns[4]
        val emailAddress = columns[5]
        val role = when (columns[6].uppercase()) {
            "STUDENT" -> Role.STUDENT
            "TEACHER" -> Role.TEACHER
            "ADMIN" -> Role.ADMIN
            else -> throw IllegalArgumentException("Invalid role: ${columns[6]}")
        }

        return Users(
            name = name,
            emailAddress = emailAddress,
            role = role
        )
    }

    private fun convertToCourse(columns: List<String>): Course {
        if (columns.size != 7) {
            throw InvalidParseListException("Expected record to have 7 columns, got ${columns.size}")
        }

        val canvasId = columns[0].toInt()
        val title = columns[1]
        val startDate = LocalDate.parse(columns[2].substring(0, 10))
        val endDate = LocalDate.parse(columns[3].substring(0, 10))

        return Course(
            canvasId = canvasId,
            title = title,
            startDate = startDate,
            endDate = endDate
        )
    }

    private fun checkForDuplicateCourses(courseList: List<Course>): List<Course> {
        return courseList.filter { course ->
            courseDB.findByIdOrNull(course.canvasId) == null
        }
    }

    private fun checkForDuplicateUsers(userList: List<Users>): List<Users> {
        return userList.filter { user ->
            usersDB.findByIdOrNull(user.emailAddress) == null
        }
    }
}