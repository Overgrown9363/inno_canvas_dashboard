package nl.hu.inno.dashboard.pythongateway.application

interface PythonGatewayService {
    fun startPythonScript(email: String, environment: String)
}