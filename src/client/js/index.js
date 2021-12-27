var loginApp = angular.module("loginApp", []);
loginApp.controller("loginController", function($scope){
    $scope.userId = "";
    $scope.password = "";
    $scope.handleLogin = function(){
        globalThis.fetch("https://127.0.0.1/login",
            {
                method:"PUT",
                body:{
                    data:3
                },
                headers:{

                }
            }
        )
        .then(data => data.json())
        .then(json => console.log(json));
    };
})