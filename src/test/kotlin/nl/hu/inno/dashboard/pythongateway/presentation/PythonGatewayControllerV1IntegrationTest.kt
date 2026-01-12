package nl.hu.inno.dashboard.pythongateway.presentation

import nl.hu.inno.dashboard.pythongateway.application.PythonGatewayService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PythonGatewayControllerV1::class)
class PythonGatewayControllerV1IntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var service: PythonGatewayService

    @Test
    fun startPythonScript_returnsOk_whenEmailPresent() {
        val environment = "ENV_TWO"
        val email = "john.doe@student.hu.nl"

        mockMvc.perform(
            post("/api/v1/scripts/$environment/")
                .with(oauth2Login().attributes { it["email"] = email })
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)

        verify(service).startPythonScript(email, environment)
    }

    @Test
    fun startPythonScript_returnsUnauthorized_whenEmailMissing() {
        val environment = "ENV_TWO"

        mockMvc.perform(
            post("/api/v1/scripts/$environment/")
                .with(oauth2Login().attributes { })
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isUnauthorized)

        verify(service, never()).startPythonScript(anyString(), anyString())
    }

    @Test
    fun startPythonScript_returnsUnauthorized_whenEmailIsBlank() {
        val environment = "ENV_TWO"

        mockMvc.perform(
            post("/api/v1/scripts/$environment/")
                .with(oauth2Login().attributes { it["email"] = "" })
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isUnauthorized)

        verify(service, never()).startPythonScript(anyString(), anyString())
    }
}