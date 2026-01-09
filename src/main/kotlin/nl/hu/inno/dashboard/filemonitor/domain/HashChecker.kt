package nl.hu.inno.dashboard.filemonitor.domain

import org.springframework.stereotype.Component
import java.io.File
import java.security.MessageDigest

@Component
class HashChecker {
    fun isContentChanged(file: File, lastHash: String?): Pair<Boolean, String?> {
        println("_____ comparing hash _____")
        val currentHash = calculateFileHash(file)
        val changed = currentHash != null && currentHash != lastHash

        println("_____ hash changed is: $changed _____")
        println("_____ hash previous: $lastHash || hash new: $currentHash")
        return Pair(changed, currentHash)
    }

    private fun calculateFileHash(file: File): String? {
        if (!file.exists()) return null
        return try {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            file.inputStream().buffered().use { input ->
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                var bytesRead = input.read(buffer)
                while (bytesRead != -1) {
                    messageDigest.update(buffer, 0, bytesRead)
                    bytesRead = input.read(buffer)
                }
            }
            val digest = messageDigest.digest()

            digest.joinToString("") { "%02x".format(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}