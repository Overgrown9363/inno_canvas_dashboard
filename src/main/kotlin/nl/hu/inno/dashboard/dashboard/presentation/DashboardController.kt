package nl.hu.inno.dashboard.dashboard.presentation

import nl.hu.inno.dashboard.dashboard.application.DashboardServiceImpl
import nl.hu.inno.dashboard.dashboard.application.dto.UsersDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/dashboard/")
class DashboardController(
    private val service: DashboardServiceImpl
    ) {

    @GetMapping("/users/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<UsersDTO> {
        val userDTO = service.findUserById(email)
        return if (userDTO != null) {
            ResponseEntity.ok(userDTO)
        } else
            ResponseEntity.notFound().build()
    }

    @PostMapping("/internal/users/new")
    fun addUsersAndCourses(): ResponseEntity<Void> {
        return try {
            service.addUsersToCourse()
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }

    @PostMapping("/internal/users/update")
    fun updateUsersAndCourses(): ResponseEntity<Void> {
        return try {
            service.updateUsersInCourse()
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }
}