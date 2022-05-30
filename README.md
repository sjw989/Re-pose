# Re-pose
당신의 안 좋은 자세습관을 고쳐주는 헬스케어 서비스 </p>
Android app &amp; node.js server PROJECT
<br></br>

# 개요
- 사용자는 일상샹활 속에서 볼 수 있는 자신의 잘못된 자세습관들을 선택할 수 있다. 
- 사용자가 선택한 자세습관이 야기할 수 있는 질병정보를 제공한다.
- 사용자가 선택한 자세습관을 고치는데에 도움이되는 운동 및 스트레칭 정보를 제공한다.
- 사용자가 선택한 요일, 시간에 푸쉬알림을 전송하여 운동시간을 알려준다.

<br></br>
# 사용 언어 및 기술
  <h><img src="https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=Android&logoColor=white"/>
   <img src="https://img.shields.io/badge/AndroidStudio-3DDC84?style=flat-square&logo=AndroidStudio&logoColor=white"/>
  <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=Kotlin&logoColor=white"/>
  <img src="https://img.shields.io/badge/Node.js-00599C?style=flat-square&logo=Node.js&logoColor=white"/> </h>
  
<br></br>
# 역할
- 전체적인 Front-end 개발
- 타이머 기능 개발
<br></br>

<br></br>
# 로그인 화면

<div style="width: 100%; height: 200px; overflow: hidden">
  사용자는 아이디/패스워드 형식으로 로그인 및 회원가입 할 수 있으며, Room DB를 활용하여 자동로그인 기능을 제공한다.
  <p></p>
  <table>
		<tr>
			<td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170947327-7404ef19-0ea4-48d3-a852-c24be350debb.png"
					width="230"
				/>
			</td>
			<td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170947493-42991d38-39e7-47a5-aa9c-8e6cb7d84e9e.png"
					width="230"
				/>
			</td>
		</tr>
	</table>

</div>
<br></br>


# 선택한 자세습관 출력 화면
<div style="width: 100%; height: 200px; overflow: hidden">
  1. 앱 실행시 나오는 메인화면 </p>
  2. 사용자는 자신이 선택한 자세습관들을 볼 수 있다. </p>
  3. 선택된 자세가 없는 경우, 아무것도 선택되지 않았음을 알려준다. </p>
  4. 각 버튼을 통해 다른 페이지로 이동할 수 있다. </p>
  5. 왼쪽 상단에 타이머가 표시된다.
  <p></p>
  <table>
		<tr>
			<td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170947893-bd3d16de-d8bf-4ede-910b-0ecc8c7ee495.jpg"
					width="230"
				/>
			</td>
			<td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170947988-13ba75fa-351e-4299-a3d6-109f69b23aa7.jpg"
					width="230"
				/>
			</td>
      <td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170948056-d5a39f3b-9352-4c51-b377-008b9875ab8b.jpg"
					width="230"
				/>
			</td>
		</tr>
	</table>

</div>

<br></br>
# 자세습관 선택 화면
  1. 사용자는 주어진 자세습관 리스트 중, 자신이 가지고있다고 생각되는 자세습관을 선택할 수 있다.</p>
  2. 자세습관 이미지를 터치함으로써 간편하게 넣고 뺄 수 있다.</p>
  3. 선택된 자세습관은 테두리로 표시한다. 
  <p></p>
	<table overflow: hidden">
		<tr>
      <td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170961521-2022305e-2057-42ae-a0ed-caec6128c559.png"
					width="230"
				/>
			</td>
			<td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170948966-5196d371-1cff-4459-9806-ede5b8d1d8f9.jpg"
					width="230"
				/>
			</td>
			<td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170950986-608f74fb-1de2-4282-be19-9d7e4ea3eba6.jpg"
					width="230"
				/>
			</td>
		</tr>
	</table>
<br></br>


# 자세습관 질병 화면
  1. 사용자가 "자세습관 선택화면"에서 선택되지않은 자세습관을 터치시, 관련된 질병정보를 보여준다.</p>
  2. "운동시작" 버튼을 누름으로써, 최종적으로 해당 자세습관을 선택하게된다.
  <p></p>
	<table overflow: hidden">
		<tr>
      <td>
				<img src="https://user-images.githubusercontent.com/82700895/170961929-8b8cbd25-f3fb-4ed9-a47e-59c51cf46250.png"
					width="230"
				/>
			</td>
			<td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170950489-bdecfb9b-20a6-4c80-9fce-9b1bb6981fdf.jpg"
					width="230"
				/>
			</td>
		</tr>
	</table>
<br></br>



# 운동 및 스트레칭 정보 출력화면 
  1. 사용자는 "선택한 자세습관 출력 화면" 에서 터치를 통해 "운동 및 스트레칭 정보 출력화면"으로 이동할 수 있다. </p>
  2. 마지막에 "운동완료" 버튼을 클릭 하면, 완료 다이얼로그를 출력해준다. </p>
  3. 만약, 타이머가 동작중이었다면 타이머를 멈춘다.
  <p></p>
	<table overflow: hidden">
		<tr>
			<td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170952899-22d90a92-71e1-4140-acd8-0668d1dee87d.png"
					width="230"
				/>
			</td>
			<td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170952966-307094c7-f1ad-44bf-80ee-6cdc0e7b0d33.jpg"
					width="230"
				/>
			</td>
      <td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170953032-b737284e-559b-4fe8-aaa4-30f30c18e314.jpg"
					width="230"
				/>
      </td>
		</tr>
	</table>

<br></br>
# 알림시간 설정화면
  사용자는 본인이 원하는 시간에 푸쉬알림을 받을 수 있도록 요일/시간을 설정하 수 있다.
  <p></p>
	<table overflow: hidden">
    <tr>
       <td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170962781-b285ff20-8e4a-482a-a781-a5459c8bdf56.png"
					width="230"
				/>
			</td>
      <td>
					<img src="https://user-images.githubusercontent.com/82700895/170957315-152e990d-9dda-4b12-9e0b-79989209c463.jpg" width="230"
				/>
			</td>
       <td>
						<img src="https://user-images.githubusercontent.com/82700895/170959326-554df4a7-cddd-4b6d-b182-fa086a729080.jpg" width="230"
				/>
			</td>
    </tr>
	</table>
		
<br></br>

# 푸쉬알림 및 타이머
  1. 사용자는 자신이 선택한 요일/시간에 푸쉬알림을 받을 수 있다. </p>
  2. 푸쉬알림을 수신한 시간으로부터 1시간의 타이머가 동작한다. </p>
  3. 만약 1시간 이내에, "운동 및 스트레칭 정보 화면"에서 "운동완료" 버튼을 누르게된다면 타이머가 멈추고, 보상을 받게된다. 
  <p></p>
	<table overflow: hidden">
    <tr>
      <td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170958856-5dfedb45-4534-4d16-8d23-49adae955151.jpg"
					width="230"
				/>
			</td>
    </tr>
	</table>
<br></br>

# 메달 화면
  1. 사용자가 보상으로 획득할 수 있는 메달의 리스트를 보여준다. </p>
  2. 획득한 메달은 컬러, 획득하지 못 한 메달은 흑백으로 보여준다. </p>
  3. 메달 이미지를 터치하면, 해당 메달의 획득조건을 다이얼로그로 보여준다. 
  <p></p>
	<table overflow: hidden">
    <tr>
      <td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170962648-51f553fc-d212-4017-858e-fcebef56bb18.png"
					width="230"
				/>
			</td>
      <td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170960965-63d4267f-44d9-4ec5-a163-5538aec3f0df.jpg"
					width="230"
				/>
			</td>
      <td>
				<img
					src="https://user-images.githubusercontent.com/82700895/170961041-c9dfe9e5-bf85-4833-aa72-4c8216c0dc5c.jpg"
					width="230"
				/>
			</td>
    </tr>
	</table>



