package nl.hu.inno.dashboard.dashboard.application.dto

import nl.hu.inno.dashboard.dashboard.domain.Users

data class StaffDTO (
val email: String,
val name: String,
val role: String,
) {
    companion object {
        fun of(user: Users): StaffDTO {
            return StaffDTO(
                email = user.email,
                name = user.name,
                role = user.privilege.name,
            )
        }
    }
}