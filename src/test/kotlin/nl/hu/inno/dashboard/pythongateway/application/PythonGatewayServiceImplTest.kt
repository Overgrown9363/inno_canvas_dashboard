package nl.hu.inno.dashboard.pythongateway.application

import nl.hu.inno.dashboard.dashboard.application.DashboardService
import nl.hu.inno.dashboard.exception.exceptions.InvalidPythonEnvironmentException
import nl.hu.inno.dashboard.pythongateway.domain.PythonEnvironment
import nl.hu.inno.dashboard.pythongateway.domain.PythonRestClient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoInteractions

class PythonGatewayServiceImplTest {
    private lateinit var dashboardService: DashboardService
    private lateinit var pythonRestClient: PythonRestClient
    private lateinit var service: PythonGatewayServiceImpl

    @BeforeEach
    fun setUp() {
        dashboardService = mock(DashboardService::class.java)
        pythonRestClient = mock(PythonRestClient::class.java)
        service = PythonGatewayServiceImpl(dashboardService, pythonRestClient)
    }

    @Test
    fun startPythonScript_callsVerifyUserAndPostsToEnvTwo_whenEnvTwoGiven() {
        val email = "admin@hu.nl"
        service.startPythonScript(email, "ENV_TWO")

        verify(dashboardService).verifyUserIsAdminOrSuperAdmin(email)
        verify(pythonRestClient).postToPythonEnvironment(PythonEnvironment.ENV_TWO)
    }

    @Test
    fun startPythonScript_callsVerifyUserAndPostsToEnvThree_whenEnvThreeGiven() {
        val email = "admin@hu.nl"
        service.startPythonScript(email, "env_three")

        verify(dashboardService).verifyUserIsAdminOrSuperAdmin(email)
        verify(pythonRestClient).postToPythonEnvironment(PythonEnvironment.ENV_THREE)
    }

    @Test
    fun startPythonScript_throwsInvalidPythonEnvironmentException_whenInvalidEnvGiven() {
        val email = "admin@hu.nl"
        val exception = assertThrows(InvalidPythonEnvironmentException::class.java) {
            service.startPythonScript(email, "invalid_env")
        }
        assertEquals("Invalid Python environment: invalid_env", exception.message)
        verify(dashboardService).verifyUserIsAdminOrSuperAdmin(email)
        verifyNoInteractions(pythonRestClient)
    }
}