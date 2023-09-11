let token = localStorage.getItem('token');
let connectedUser = localStorage.getItem('connectedUser');
let connectedUserJson = JSON.parse(connectedUser);
function loadAndDisplayUsers() {

    // check if the user is connected
    
    if (!connectedUser) {
        window.location = 'login.html';
        return;
    }
    
    


    const userListElement = document.getElementById("userList");
    // Clear any existing content in the userListElement
    userListElement.innerHTML = "Loading...";
    // Retrieve the userList from Local Storage
    fetch('https://localhost:8082/api/v1/onlineusers',{
		method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': token
        }
	})
    .then((response) => {
        return response.json();
    })
    .then((data) => {
        console.log(data);
        displayUsers(data, userListElement);
    });
}

function displayUsers(userList, userListElement) {
    userListElement.innerHTML = "";

    // Loop through the userList and create list items to display each user
    userList.forEach(user => {
        const listItem = document.createElement("li");
        if(user.userName===connectedUserJson.userName){
			listItem.innerHTML = `
                <div style="color:blue;">
                    <i class="fa fa-user-circle"></i>
                    ${user.userName} <i class="user-email">(${user.email})</i>
                </div>
                <i class="fa fa-lightbulb-o ${user.status === "online" ? "online" : "offline"}"></i>
            `;
        
        	userListElement.appendChild(listItem);
        	
		}else {
			listItem.innerHTML = `
                <div class="userDiv">
                    <i class="fa fa-user-circle"></i>
                    ${user.userName} <i class="user-email">(${user.email})</i>
                </div>
                <i class="fa fa-lightbulb-o ${user.status === "online" ? "online" : "offline"}"></i>
            `;
        
        	userListElement.appendChild(listItem);
		}

        
    });
}

// Call the loadAndDisplayUsers function when the page loads
window.addEventListener("load", loadAndDisplayUsers);



function handleLogout() {
    fetch('https://localhost:8082/auth/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization':token
        },
    })
        .then((response) => {
			localStorage.removeItem('connectedUser');
			localStorage.removeItem('token');
            return response;
        })
        .then((data) => {
            window.location.href = "login.html";
        });
}

const logoutBtn = document.getElementById("logoutBtn");
logoutBtn.addEventListener("click", handleLogout);

const chatBtn = document.getElementById("chatBtn");
chatBtn.addEventListener("click", () => {
	console.log("Hi");
	window.location.href="chat.html";
});


function handleNewMeeting() {
    const connectedUser = JSON.parse(localStorage.getItem('connectedUser'));
    console.log(connectedUser);
    window.open(`videocall.html?username=${connectedUser.userName}`, "_blank");
}

// Attach the handleNewMeeting function to the "Create a New Meeting" button
const newMeetingBtn = document.getElementById("newMeetingBtn");
newMeetingBtn.addEventListener("click", handleNewMeeting);


function handleJoinMeeting() {
    const roomId = document.getElementById("meetingName").value;
    const connectedUser = JSON.parse(localStorage.getItem('connectedUser'));
	
    const url = `videocall.html?roomID=${roomId}&username=${connectedUser.userName}`;

    window.open(url, "_blank");
}

const joinMeetingBtn = document.getElementById("joinMeetingBtn");
joinMeetingBtn.addEventListener("click", handleJoinMeeting);