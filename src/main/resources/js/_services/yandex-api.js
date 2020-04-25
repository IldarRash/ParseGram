import config from 'config';

export const yandexService = {
    clientId,
};

function clientId() {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    };

    return fetch(`${config.apiUrl}/yandex`, requestOptions)
        .then(handleResponse)
        .then(yandexApi => {
            // login successful if there's a user in the response
            if (yandexApi) {
                // store user details and basic auth credentials in local storage
                // to keep user logged in between page refreshes;
                localStorage.setItem('api', JSON.stringify(yandexApi));
            }

            return yandexApi;
        });
}

function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                location.reload(true);
            }

            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        console.log("Data", data);
        return data;
    });
}