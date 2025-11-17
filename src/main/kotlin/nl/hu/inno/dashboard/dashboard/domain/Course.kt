package nl.hu.inno.dashboard.dashboard.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "COURSE")
class Course(
    @Id
    @Column(name = "CANVAS_COURSE_ID")
    val canvasCourseId: Int = 0,

    @Column(name = "COURSE_NAME")
    val courseName: String = "",

    @Column(name = "INSTANCE_NAME")
    val instanceName: String = "",

    @Column(name = "START_DATE")
    val startDate: LocalDate = LocalDate.MIN,

    @Column(name = "END_DATE")
    val endDate: LocalDate = LocalDate.MIN,

    @ManyToMany(mappedBy = "courses", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val users: MutableSet<Users> = mutableSetOf()
) {
    companion object {
        fun of(canvasCourseId: Int, courseName: String, instanceName: String, startDate: LocalDate, endDate: LocalDate, users: MutableSet<Users> = mutableSetOf()): Course {
            return Course(canvasCourseId, courseName, instanceName, startDate, endDate, users)
        }
    }

    override fun equals(other: Any?): Boolean =
        this === other || (other is Course && canvasCourseId == other.canvasCourseId)

    override fun hashCode(): Int = canvasCourseId.hashCode()
}