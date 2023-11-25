function handleLogin(event) {
    event.preventDefault();

    // Get user input
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

//    const user = {
//        email: email,
//        password: password
//    };

    fetch('https://localhost:8082/auth/login?' + new URLSearchParams({
		    email: email.toLowerCase(),
	        password: password
        }), {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    }).then(response => {
        if (!response.ok) {
            alert('Email(or User name) and / or password is incorrect');
            window.location.href = 'login.html'
        }
        
        return response.json();
    }).then((response) => {
		
        localStorage.setItem("token", 'Bearer '+response.token);
        getUserInfo(email);
        window.location.href = 'index.html'
		
        
    }).catch(error => {
        console.error('POST request error', error);
    });
    
}

function getUserInfo(email) {
	alert(email);
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

const loginForm = document.getElementById("loginForm");
loginForm.addEventListener("submit", handleLogin);

