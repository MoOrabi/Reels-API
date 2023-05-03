const initFacebookLogin = () => {
    window.fbAsyncInit = function () {
      FB.init({
        appId: "486206640184103",
        autoLogAppEvents: true,
        xfbml: true,
        version: "v7.0",
      });
    };
  };
  
FB.login(function(response) {
  if (response.status === 'connected') {
    // Logged into your webpage and Facebook.
    const facebookLoginRequest = {
      accessToken: response.authResponse.accessToken,
    };
    facebookLogin(facebookLoginRequest)
      .then((response) => {
        localStorage.setItem("accessToken", response.accessToken);
       });
  } else {
    // The person is not logged into your webpage or we are unable to tell. 
  }
}, {scope: 'email'});