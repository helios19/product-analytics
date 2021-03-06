
var app = angular.module('productAnalytics',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8081/',
    TRANSACTION_SERVICE_API : 'http://localhost:8081/transaction-summary'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'templates/list.html',
                controller:'TransactionController',
                controllerAs:'ctrl'
            });
        $urlRouterProvider.otherwise('/');
    }]);