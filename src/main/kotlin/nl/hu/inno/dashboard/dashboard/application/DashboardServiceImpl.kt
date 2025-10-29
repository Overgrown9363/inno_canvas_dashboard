package nl.hu.inno.dashboard.dashboard.application

import nl.hu.inno.dashboard.dashboard.data.CourseRepository
import nl.hu.inno.dashboard.dashboard.data.UsersRepository
import nl.hu.inno.dashboard.dashboard.domain.Course
import nl.hu.inno.dashboard.dashboard.domain.Users
import nl.hu.inno.dashboard.fileparser.application.FileParserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
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

        val courseList = rows.map { parseCourseList(it) }
        val userList = rows.map { parseUserList(it) }

        courseDB.saveAll(courseList)
        usersDB.saveAll(userList)
    }

    private fun parseUserList(columns: List<String>): Users {

    }

    private fun parseCourseList(columns: List<String>): Course {

    }
}