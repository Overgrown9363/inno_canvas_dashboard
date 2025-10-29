package nl.hu.inno.dashboard.dashboard.presentation

import nl.hu.inno.dashboard.dashboard.application.DashboardServiceImpl
import nl.hu.inno.dashboard.dashboard.domain.Course
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import nl.hu.inno.dashboard.fileparser.application.FileParserService 

@RestController
@RequestMapping("/dashboard/")
class DashboardController(
    private val service: DashboardServiceImpl, 
    private val fileParserService: FileParserService
    ) {

    @GetMapping("/{id}")
    fun getMessage(@PathVariable id: Int): ResponseEntity<Course> {
        val course = service.findCourseById(id)
        return if (course != null) {
            ResponseEntity.ok(course)
        } else
            ResponseEntity.notFound().build()
    }

    @PostMapping("/parse")
    fun parseFile(@RequestParam("file") file: MultipartFile) : ResponseEntity<List<List<String>>> {
        return try {
            val parsedData = fileParserService.parseFile(file)
            ResponseEntity.ok(parsedData)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }
}