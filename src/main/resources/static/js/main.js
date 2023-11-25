const form = document.querySelector('#video-form');

const videoP = document.querySelector('#video-form #file');
const videoDiv = document.querySelector('#video-player');
const videoScreen = document.querySelector('#video-screen');

const queryParams = Object.fromEntries(new URLSearchParams(window.location.search));
fetch('http://localhost:8082/api/v1/reels')
    .then(result => result.json())
    .then(result => {

        const myVids = document.querySelector('#your-videos');
        
        if(result.length > 0){
            for(let vid of result){
				//let vid=result;
                const li = document.createElement('LI');
                const link = document.createElement('A');
                link.innerText = vid.id;
                link.href = window.location.origin + window.location.pathname + '?video=' + vid.id;
                li.appendChild(link);
                myVids.appendChild(li);
            }
        }else{
            myVids.innerHTML = 'No videos found';
        }

    });
    
if(queryParams.video){

    videoScreen.src = `http://localhost:8081/api/v1/reels/${queryParams.video}`;
    videoDiv.style.display = 'block';
    document.querySelector('#now-playing')
        .innerText = 'Now playing ' + queryParams.video;

}

let data = new FormData();
data.append('file', videoP.files[0]);

form.addEventListener('submit', ev => {
    ev.preventDefault();

    fetch('http://localhost:8081/api/v1/reels?description=VideoFromJS&country=Egypt&city=Cairo', {
        method: 'POST',
        body: data ,
        headers : {
			'Authorization' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNb29yYWJpMiIsImlhdCI6MTY3NTY3MDYyMSwiZXhwIjoxNjc1Njg4NjIxfQ.f5JPPusFv4zfvXk2HFCxLAL8O-8QRv1WhibeeqrC9Q0',
		//	'JWT' : 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNb29yYWJpMiIsImlhdCI6MTY3NTY3MDYyMSwiZXhwIjoxNjc1Njg4NjIxfQ.f5JPPusFv4zfvXk2HFCxLAL8O-8QRv1WhibeeqrC9Q0',
			'Content-Type': 'application/octet-stream'
		}
    }).then(result => result.text()).then(_ => {
        window.location.reload();
    });

});