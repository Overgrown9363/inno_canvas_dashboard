package nl.hu.inno.dashboard.fileparser.application

import org.springframework.web.multipart.MultipartFile

interface FileParserService {
    fun parseFile(multipartFile: MultipartFile): List<List<String>>
}