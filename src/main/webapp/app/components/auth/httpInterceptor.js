angular.module('lite')
    .factory('httpInterceptor', function ($q, $location, $window) {
        return {
            request(config) {
                // sends the locale over to the server based on language selection, to each request
                config.headers['Accept-Language'] = $window.localStorage.getItem("NG_TRANSLATE_LANG_KEY");
                return config || $q.when(config);
            },
            requestError(response) {
                return $q.reject(response);
            },
            response: function (response) {
                if (typeof response.data === 'string' && response.data.indexOf("18c971a3-d7da-40a6-bfad-2601aaac40ab") !== -1) {
                    $window.location.href = 'login?lang=' + $window.localStorage.getItem("NG_TRANSLATE_LANG_KEY");
                }
                return response || $q.when(response);
            },
            responseError(response) {
                return $q.reject(response);
            }
        };
    })
    .config(function ($httpProvider) {
        $httpProvider.interceptors.push('httpInterceptor');
    });