package nl.hu.inno.dashboard.fileparser.application

import nl.hu.inno.dashboard.Fixture
import nl.hu.inno.dashboard.fileparser.domain.CsvFileParser
import nl.hu.inno.dashboard.fileparser.domain.exception.FileTypeNotSupportedException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockMultipartFile

class FileParserServiceTest {

    private val csvParser = CsvFileParser()
    private val service = FileParserService(listOf(csvParser))

    @Test
    fun canParseValidCSV() {
        val csvContent = Fixture.fromFile("users-01.csv")
        val file = MockMultipartFile("file", "users-01.csv", "text/csv", csvContent.toByteArray())

        val actualResult = service.parseFile(file)

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

    @Test
    fun unsupportedFileThrowsException() {
        val file = MockMultipartFile("file", "users-01.txt", "text/plain", "test".toByteArray())

        val exception = assertThrows(FileTypeNotSupportedException::class.java) {
            service.parseFile(file)
        }
        val expectedMessage = "No available parser found"
        assertTrue(exception.message!!.contains(expectedMessage))
    }
}