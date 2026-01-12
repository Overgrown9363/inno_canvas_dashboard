package nl.hu.inno.dashboard.dashboard.application.dto

import nl.hu.inno.dashboard.dashboard.domain.AppRole
import nl.hu.inno.dashboard.dashboard.domain.Users
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AdminDTOTest {
    @Test
    fun of_mapsUserToAdminDTO() {
        val user = Users(
            email = "admin@hu.nl",
            name = "Admin User",
            appRole = AppRole.ADMIN
        )

        val dto = AdminDTO.of(user)

        assertEquals("admin@hu.nl", dto.email)
        assertEquals("Admin User", dto.name)
        assertEquals("ADMIN", dto.appRole)
    }
}