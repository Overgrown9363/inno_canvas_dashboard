package nl.hu.inno.dashboard.filemonitor.domain

import org.springframework.stereotype.Component
import java.io.File
import java.security.MessageDigest

@Component
class HashChecker {
    private var lastHash: String? = null

    fun isContentChanged(file: File): Boolean {
        println("_____ comparing hash _____")
        val currentHash = calculateFileHash(file)
        val changed = currentHash != null && currentHash != lastHash
        if (changed) lastHash = currentHash
        println("_____ hash changed is: $changed _____")
        println("_____ hash previous: $lastHash || hash new: $currentHash")
        return changed
    }

    private fun calculateFileHash(file: File): String? {
        if (!file.exists()) return null
        return try {
            val bytes = file.readBytes()
            val digest = MessageDigest.getInstance("SHA-256").digest(bytes)
            digest.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}