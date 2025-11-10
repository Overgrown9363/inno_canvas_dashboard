package nl.hu.inno.dashboard.dashboard.domain

import jakarta.persistence.*

@Entity
@Table(name = "USERS")
class Users (
    @Id
    @Column(name = "EMAIL")
    val email: String = "",

    @Column(name = "NAME")
    val name: String = "",

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    val role: Role? = null,

    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "USER_COURSE",
        joinColumns = [JoinColumn(name = "USER_EMAIL")],
        inverseJoinColumns = [JoinColumn(name = "CANVAS_COURSE_ID")]
    )
    val courses: MutableSet<Course> = mutableSetOf()
) {
    companion object {
        fun of(emailAddress: String, name: String, role: Role?, courses: MutableSet<Course> = mutableSetOf()): Users {
            return Users(emailAddress, name, role, courses)
        }
    }

    override fun equals(other: Any?): Boolean =
        this === other || (other is Users && email == other.email)

    override fun hashCode(): Int = email.hashCode()
}