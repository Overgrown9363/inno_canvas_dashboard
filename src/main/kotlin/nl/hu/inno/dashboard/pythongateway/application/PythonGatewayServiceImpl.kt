package nl.hu.inno.dashboard.pythongateway.application

import nl.hu.inno.dashboard.dashboard.application.DashboardService
import nl.hu.inno.dashboard.exception.exceptions.InvalidPythonEnvironmentException
import nl.hu.inno.dashboard.pythongateway.domain.PythonEnvironment
import org.springframework.stereotype.Service

@Service
class PythonGatewayServiceImpl(
    private val dashboardService: DashboardService
) : PythonGatewayService {

    override fun startPythonScript(email: String, environment: String) {
        dashboardService.verifyUserIsAdminOrSuperAdmin(email)

        val requestedEnvironment = parsePythonEnvironment(environment)

        TODO("Create rest template in domain layer that makes a POST to Python env 2 or 3 depending on the requested environment")
    }

    private fun parsePythonEnvironment(environment: String): PythonEnvironment =
        when (environment.trim().uppercase()) {
            "ENV_TWO" -> PythonEnvironment.ENV_TWO
            "ENV_THREE" -> PythonEnvironment.ENV_THREE
            else -> throw InvalidPythonEnvironmentException("Invalid Python environment: $environment")
    }
}