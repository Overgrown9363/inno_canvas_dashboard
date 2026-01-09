package nl.hu.inno.dashboard.filemonitor.application

interface FileMonitorService {
    fun startWatching()
    fun stopWatching()
}