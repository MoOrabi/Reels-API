function handleRegistration(event) {
    event.preventDefault();

    // Get user input
    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const status = "online"; // Assuming the status is online upon registration

    // Create an object with user information
    const user = {
        first_name:'Mohammed',
        last_name:'Orabi',
        user_name: username,
        email: email.toLowerCase(),
        password: password
    };
    fetch('https://localhost:8082/auth/register?' + new URLSearchParams({
	first_name:'Mohammed',
        last_name:'Orabi',
        user_name: username,
        email: email,
        password: password
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
        window.location.href = "index.html";
    }).catch(error => {
        console.error('POST request error:', error);
    });

}

// Attach the handleRegistration function to the form's submit event
const registrationForm = document.getElementById("registrationForm");
registrationForm.addEventListener("submit", handleRegistration);