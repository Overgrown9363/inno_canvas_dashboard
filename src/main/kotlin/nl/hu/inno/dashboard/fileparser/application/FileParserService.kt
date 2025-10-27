package nl.hu.inno.dashboard.fileparser.application

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileParserService() {
    fun parseFile(multipartFile: MultipartFile): List<Array<String>> {
        return TODO("Provide the return value")
    }
}