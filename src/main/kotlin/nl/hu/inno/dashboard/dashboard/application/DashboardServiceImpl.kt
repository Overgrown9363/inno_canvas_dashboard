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

    override fun updateExistingCourseData(file: MultipartFile) {
        val records = fileParserService.parseFile(file)

        val userCache = mutableMapOf<String, Users>()
        val updatedCourse = updateCourseUserData(records, userCache)

        courseDB.save(updatedCourse)
        usersDB.saveAll(userCache.values)
    }

    override fun replaceCourseData(file: MultipartFile) {
        TODO("Not yet implemented")
    }

    private fun updateCourseUserData(
        records: List<List<String>>,
        userCache: MutableMap<String, Users>
    ): Course {
        var updatedCourse = retrieveCourseFromRecords(records)

        for (record in records) {
            if (record.size != 7) {
                throw InvalidParseListException("Expected record to have 7 columns, got ${record.size}")
            }

            val userEmail = record[5]
            val user = userCache.getOrPut(userEmail) {
                usersDB.findByIdOrNull(userEmail) ?: convertToUser(record)
            }

            val updatedUser = user.copy(courses = user.courses + updatedCourse)
            updatedCourse = updatedCourse.copy(users = updatedCourse.users + updatedUser)
            userCache[userEmail] = updatedUser
        }

        return updatedCourse
    }

    private fun convertToUser(record: List<String>): Users {
        val name = record[4]
        val emailAddress = record[5]
        val role = when (record[6].uppercase()) {
            "STUDENT" -> Role.STUDENT
            "TEACHER" -> Role.TEACHER
            "ADMIN" -> Role.ADMIN
            else -> throw IllegalArgumentException("Invalid role: ${record[6]}")
        }

        return Users.of(emailAddress, name, role)
    }

    private fun convertToCourse(record: List<String>): Course {
        val canvasId = record[0].toInt()
        val title = record[1]
        val startDate = LocalDate.parse(record[2].substring(0, 10))
        val endDate = LocalDate.parse(record[3].substring(0, 10))

        return Course.of(canvasId, title, startDate, endDate)
    }

    private fun retrieveCourseFromRecords(records: List<List<String>>): Course {
        val firstRecord = records[0]
        val courseId = firstRecord[0].toInt()

        return courseDB.findByIdOrNull(courseId) ?: convertToCourse(firstRecord)
    }
}