//import { getUserInfo } from "./login";

function handleCredentialResponse(response) {
    decodeJwtResponse(response.credential);
}

function parseJwt (token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}

function decodeJwtResponse(data){
    signIn(parseJwt(data))
}

function signIn(ExtractedData) {
	console.log(ExtractedData);
	fetch('https://localhost:8082/auth/socialLogin?' + new URLSearchParams({
		    email: ExtractedData.email.toLowerCase()
        }), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(response => {
        if (!response.ok) {
			alert("Not Ok");
            register(ExtractedData);
            return;
        }
        
        return response.json();
    }).then((response) => {
		
        localStorage.setItem("token", 'Bearer '+response.token);
        getUserInfo(ExtractedData.email);
        window.location.href = 'index.html'
        
    }).catch(error => {
        console.error('POST request error', error);
    });
    
}

function register(data) {
	const user = {
        first_name: data.given_name,
        last_name: data.family_name,
        email: data.email.toLowerCase()
    };
	fetch('https://localhost:8082/auth/socialRegister?' + new URLSearchParams({
	    first_name: data.given_name,
        last_name: data.family_name,
        email: data.email.toLowerCase()
	}), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response;
    }).then(() => {
        localStorage.setItem("connectedUser", JSON.stringify(user));
        localStorage.setItem("token", 'Bearer '+response.token);
        getUserInfo(data.email);
        window.location.href = 'index.html'
    }).catch(error => {
        console.error('POST request error:', error);
    });
}

function getUserInfo(email) {
	fetch('https://localhost:8082/api/v1/users/un/' + email.toLowerCase(), {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem("token")
        }
    }).then(response => {
        return response.json();
    }).then((response) => {
		localStorage.setItem('connectedUser', JSON.stringify(response));
    }).catch(error => {
        console.error('GET request error', error);
    });
}


/* document.addEventListener("DOMContentLoaded", function() {
// <!-- FB SDK LOAD START -->
	window.fbAsyncInit = function() {
		FB.init({
			appId      : '486206640184103',
			cookie     : true,
			xfbml      : true,
			version    : 'v5.0'
		});
		FB.AppEvents.logPageView();
	};
	(function(d, s, id){
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id)) {return;}
		js = d.createElement(s); js.id = id;
		js.src = "https://connect.facebook.net/en_US/sdk.js";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
// <!-- FB SDK LOAD END -->
});
*/

function checkLoginState() {  
  FB.getLoginStatus(function(response) {  
  	statusChangeCallback(response);
  });
}

function statusChangeCallback(response) {
   console.log(response);
   if (response.status === 'connected') {
      console.log("logged in!")   
      testAPI();
   } else {
      console.log('please login')
   }
}

function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me',  {fields: 'name, email'}, function(response) {
      signIn(response)
    });
    FB.Event.subscribe('xfbml.render', finished_rendering);

}

