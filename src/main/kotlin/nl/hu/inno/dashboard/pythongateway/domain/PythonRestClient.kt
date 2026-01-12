package nl.hu.inno.dashboard.pythongateway.domain

import nl.hu.inno.dashboard.exception.exceptions.PythonGatewayException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForEntity

@Component
class PythonRestClient(
    @Value("\${python.gateway.env.two.url}")
    private val pythonEnvTwoUrl: String,
    @Value("\${python.gateway.env.three.url}")
    private val pythonEnvThreeUrl: String,
    private val restTemplate: RestTemplate
) {
    fun postToPythonEnvironment(environment: PythonEnvironment) {
        val url = when (environment) {
            PythonEnvironment.ENV_TWO -> pythonEnvTwoUrl
            PythonEnvironment.ENV_THREE -> pythonEnvThreeUrl
        }

        try {
            restTemplate.postForEntity<Void>(url)
        } catch (ex: Exception) {
            throw PythonGatewayException("Python environment unreachable: ${ex.message}")
        }
    }
}