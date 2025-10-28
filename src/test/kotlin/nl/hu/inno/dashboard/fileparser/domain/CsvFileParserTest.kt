package nl.hu.inno.dashboard.fileparser.domain

import nl.hu.inno.dashboard.Fixture
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile

class CsvFileParserTest {

    private val parser = CsvFileParser()

    @Test
    fun supportsValidCSV() {
        val file = MockMultipartFile("file", "users-01.csv", "text/csv", byteArrayOf())

        assertTrue(parser.supports(file))
    }

    @Test
    fun doesNotSupportInvalidCSV() {
        val file = MockMultipartFile("file", "users-01.txt", "text/plain", byteArrayOf())

        assertFalse(parser.supports(file))
    }

    @Test
    fun parseValidCSV() {
        val csvContent = Fixture.fromFile("users-01.csv")
        val file = MockMultipartFile("file", "users-01.csv", "text/csv", csvContent.toByteArray())

        val actualResult = parser.parse(file)

        val expectedResultSize = 3
        val expectedFirstRow = arrayOf("email_address", "first_name", "last_name", "role", "courses")
        val firstResultRow = 0
        val expectedSecondRow = arrayOf("john.doe@student.hu.nl", "john", "doe", "STUDENT", "2600;2601")
        val secondResultRow = 1
        val expectedThirdRow = arrayOf("jane.doe@hu.nl", "jane", "doe", "TEACHER", "2600;2700")
        val thirdResultRow = 2
        assertEquals(expectedResultSize, actualResult.size)
        assertArrayEquals(expectedFirstRow, actualResult[firstResultRow])
        assertArrayEquals(expectedSecondRow, actualResult[secondResultRow])
        assertArrayEquals(expectedThirdRow, actualResult[thirdResultRow])
    }
}