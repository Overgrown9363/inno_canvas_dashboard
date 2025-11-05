package nl.hu.inno.dashboard.filefetcher.application

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class FileFetcherServiceImpl : FileFetcherService {
    override fun fetchCsvFile(): Resource {
        return ClassPathResource("a.csv")
    }
}