
const form = document.querySelector("#product-form");

form.addEventListener("submit",async(e)=>{
	e.preventDefault();
	
	const fileInput = document.querySelector("#img-file");
	
	const fileName = fileInput.files[0].name;
	
	
	
	//주소에는 공백같이 사용할 수 없는 문자들이 좀재하 ㅁ
	// 그래서 대체하는 문자로 변환시커야함 이런 문제를 encodeUrlcomponent로 퍼리해줌
	const name = encodeURIComponent(fileName);
	//클라이언트가 이미지를 업로드할거다 요청을 서버에게 함
	const presignedResponse = await fetch(`/presigned-url?filename=${name}`);
	//서버에서 클라이언트한테 업로드할 url을 넘겨받음
	const presignedUrl = await presignedResponse.text();
	//받은 URL을 이용해서 버킷에 업로드 요청을 함
	const s3uploadResponse = await fetch(presignedUrl,{
		method: "PUT",
		body: fileInput.files[0]
		});
	const fileUrl = s3uploadResponse.url.split("?")[0];
	console.log(fileUrl);

})