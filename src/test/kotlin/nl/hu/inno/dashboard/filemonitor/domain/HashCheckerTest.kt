package nl.hu.inno.dashboard.filemonitor.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class HashCheckerTest {
    private lateinit var hashChecker: HashChecker

    @BeforeEach
    fun setUp() {
        hashChecker = HashChecker()
    }

    @Test
    fun isContentChanged_returnsTrue_whenFileChanged() {
        val file = File.createTempFile("test", ".txt")
        file.writeText("original")
        val (_, hash1) = hashChecker.isContentChanged(file, null)
        file.writeText("changed")
        val (changed, hash2) = hashChecker.isContentChanged(file, hash1)
        assertTrue(changed)
        assertNotEquals(hash1, hash2)
        file.delete()
    }

    @Test
    fun isContentChanged_returnsFalse_whenFileNotChanged() {
        val file = File.createTempFile("test", ".txt")
        file.writeText("same content")
        val (_, hash1) = hashChecker.isContentChanged(file, null)
        val (changed, hash2) = hashChecker.isContentChanged(file, hash1)
        assertFalse(changed)
        assertEquals(hash1, hash2)
        file.delete()
    }

    @Test
    fun isContentChanged_returnsFalseAndNull_whenFileDoesNotExist() {
        val file = File("nonexistent.txt")
        val (changed, hash) = hashChecker.isContentChanged(file, null)
        assertFalse(changed)
        assertNull(hash)
    }

    @Test
    fun isContentChanged_returnsTrue_whenLastHashIsNullAndFileExists() {
        val file = File.createTempFile("test", ".txt")
        file.writeText("something")
        val (changed, hash) = hashChecker.isContentChanged(file, null)
        assertTrue(changed)
        assertNotNull(hash)
        file.delete()
    }
}