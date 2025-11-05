package nl.hu.inno.dashboard.dashboard.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.Table
import jakarta.persistence.ManyToMany

@Entity
@Table(name = "USERS")
class Users (
    @Id
    @Column(name = "EMAIL_ADDRESS")
    val emailAddress: String = "",

    @Column(name = "NAME")
    val name: String = "",

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    val role: Role? = null,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "USER_COURSE",
        joinColumns = [JoinColumn(name = "USER_EMAIL")],
        inverseJoinColumns = [JoinColumn(name = "COURSE_CANVAS_ID")]
    )
    val courses: MutableSet<Course> = mutableSetOf()
) {
    companion object {
        fun of(emailAddress: String, name: String, role: Role?, courses: MutableSet<Course> = mutableSetOf()): Users {
            return Users(emailAddress, name, role, courses)
        }
    }

    override fun equals(other: Any?): Boolean =
        this === other || (other is Users && emailAddress == other.emailAddress)

    override fun hashCode(): Int = emailAddress.hashCode()
}