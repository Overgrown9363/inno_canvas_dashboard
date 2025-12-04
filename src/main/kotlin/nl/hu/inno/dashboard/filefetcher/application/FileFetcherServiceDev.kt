package nl.hu.inno.dashboard.filefetcher.application

import nl.hu.inno.dashboard.exception.exceptions.InvalidPathException
import nl.hu.inno.dashboard.filefetcher.domain.HtmlPathResolver
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import java.net.URI

@Service
@Profile("dev")
class FileFetcherServiceDev(
    @Value("\${filefetcher.base-url}")
    private val baseUrl: String
) : FileFetcherService {

    override fun fetchCsvFile(): Resource {
        val path = "${baseUrl}/user_data.csv"
        return UrlResource(URI.create(path))
    }

    override fun fetchDashboardHtml(email: String, role : String, instanceName: String, relativeRequestPath: String): Resource {
        val courseCode = instanceName.substringBefore("_")

        val baseUrlWithInstance = "$baseUrl/$courseCode/$instanceName/dashboard"
        val resolvedPath = HtmlPathResolver.resolvePath(email, role, instanceName, relativeRequestPath)
        val fullPath = "$baseUrlWithInstance/$resolvedPath"

        val resource = UrlResource(URI.create(fullPath))
        if (!resource.exists()) {
            throw InvalidPathException("Path $resolvedPath did not lead to an existing resource")
        }

//        TODO: remove these prints and comments below
        println("instance name: $instanceName, relative requestPath: $relativeRequestPath")
        println("final path: $fullPath")
        return resource
    }
}

/*
- vorige situatie:
- top level folder is gebaseerd op het instanceName, wat in het domein/database bekend is
- resultaat: we kunnen het direct vergelijken met de data uit de database en weten altijd dat het overeenkomt
-
- nieuwe situatie:
- top level folder is gebaseerd op de cursus code, wat niet bekend is in het domein/database
- resultaat: we moeten de cursus code ophalen met een h@ck d.m.v. een substring methode op de instanceName variabele
- dit neemt een risico met zich mee omdat de instanceName handmatig ingevoerd word, en niet door een script bepaald word
- hierdoor kan een cursus niet goed werken binnen de applicatie aangezien we de instanceName splitsen op de "_"
-
- bvb: als de instanceName als volgt is: TICT-V3SE6-25_sep25_test -- en we splitten op de "_" krijgen we: TICT-V3SE6-25
- in deze situatie is dat goed en werkt het, maar als er ooit een canvas cursus is met een underscore, of als
- de instanceName na de cursusCode niet meteen begint met een "_" dan zal het fout gaan
-
- voorstel: canvas cursus code vervangen door cursus code als primary key voor de cursus.
- nu hebben we zowel toegang tot de altijd correcte cursus code en instanceName
- (als pluspunt kunnen we de cursus naam weer terug zetten naar de echte cursus naam, en niet de code zoals in deze nieuwe situatie)
 */