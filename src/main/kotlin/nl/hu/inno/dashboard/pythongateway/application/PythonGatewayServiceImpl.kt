package nl.hu.inno.dashboard.pythongateway.application

import nl.hu.inno.dashboard.dashboard.application.DashboardService
import nl.hu.inno.dashboard.exception.exceptions.InvalidPythonEnvironmentException
import nl.hu.inno.dashboard.pythongateway.domain.PythonEnvironment
import nl.hu.inno.dashboard.pythongateway.domain.PythonRestClient
import org.springframework.stereotype.Service

@Service
class PythonGatewayServiceImpl(
    private val dashboardService: DashboardService,
    private val pythonRestClient: PythonRestClient
) : PythonGatewayService {

    override fun startPythonScript(email: String, environment: String) {
        dashboardService.verifyUserIsAdminOrSuperAdmin(email)

        val requestedEnvironment = parsePythonEnvironment(environment)
        pythonRestClient.postToPythonEnvironment(requestedEnvironment)
    }

    private fun parsePythonEnvironment(environment: String): PythonEnvironment =
        when (environment.trim().uppercase()) {
            "ENV_TWO" -> PythonEnvironment.ENV_TWO
            "ENV_THREE" -> PythonEnvironment.ENV_THREE
            else -> throw InvalidPythonEnvironmentException("Invalid Python environment: $environment")
    }
}