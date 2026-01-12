const UNAUTHORIZED_STATUS = 401;
const FORBIDDEN_STATUS = 403;
const SERVER_ERROR_STATUS = 500;
const BAD_GATEWAY_STATUS = 502;

export async function renewCanvasData() {
    const response = await fetch('/api/v1/scripts/ENV_TWO/', {
        method: 'POST',
        credentials: 'include',
    });

    if (!response.ok) {
        let errorMessage;

        switch (response.status) {
            case UNAUTHORIZED_STATUS:
                errorMessage = 'Niet geautoriseerd: log opnieuw in om de Canvas-gegevens te verversen.';
                break;
            case FORBIDDEN_STATUS:
                errorMessage = 'Toegang geweigerd: je hebt geen rechten om de Canvas-gegevens te verversen.';
                break;
            case SERVER_ERROR_STATUS:
                errorMessage = 'Er is een serverfout opgetreden; de Canvas-gegevens zijn niet ververst.';
                break;
            case BAD_GATEWAY_STATUS:
                errorMessage = 'De backend kon geen verbinding maken met de Python-omgeving; de Canvas-gegevens zijn niet ververst.';
                break;
            default:
                errorMessage = 'Er is iets misgegaan; de Canvas-gegevens zijn niet ververst.';
        }

        throw new Error(errorMessage);
    }
}

export async function renewDashboardHtmls() {
    const response = await fetch('/api/v1/scripts/ENV_THREE/', {
        method: 'POST',
        credentials: 'include',
    });

    if (!response.ok) {
        let errorMessage;

        switch (response.status) {
            case UNAUTHORIZED_STATUS:
                errorMessage = 'Niet geautoriseerd: log opnieuw in om de dashboards te vernieuwen.';
                break;
            case FORBIDDEN_STATUS:
                errorMessage = 'Toegang geweigerd: je hebt geen rechten om de dashboards te vernieuwen.';
                break;
            case SERVER_ERROR_STATUS:
                errorMessage = 'Er is een serverfout opgetreden; de dashboards zijn niet vernieuwd.';
                break;
            case BAD_GATEWAY_STATUS:
                errorMessage = 'De backend kon geen verbinding maken met de Python-omgeving; de dashboards zijn niet vernieuwd.';
                break;
            default:
                errorMessage = 'Er is iets misgegaan; de dashboards zijn niet vernieuwd.';
        }

        throw new Error(errorMessage);
    }
}

export async function updateDatabase() {
    const response = await fetch('/api/v1/dashboard/users/refresh', {
        method: 'POST',
        credentials: 'include',
    });

    if (!response.ok) {
        let errorMessage;

        switch (response.status) {
            case UNAUTHORIZED_STATUS:
                errorMessage = 'Niet geautoriseerd: log opnieuw in om de database te updaten.';
                break;
            case FORBIDDEN_STATUS:
                errorMessage = 'Toegang geweigerd: je hebt geen rechten om de database te updaten.';
                break;
            case SERVER_ERROR_STATUS:
                errorMessage = 'Er is een serverfout opgetreden; de database is niet geüpdatet.';
                break;
            default:
                errorMessage = 'Er is iets misgegaan; de database is niet geüpdatet.';
        }

        throw new Error(errorMessage);
    }
}
