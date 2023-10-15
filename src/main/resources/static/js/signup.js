// 휴대폰 번호 입력 부분
function changePhone() {
    const phone = document.getElementById("phone").value
    if (phone.length === 11) {
        document.getElementById("studentNum").focus()
    }
}

function signUpCheck() {

    // let pwdCheck;
    // pwdCheck = /^(?=.*\d)(?=.*[a-z])(?=\\S+$).{8,20}$/;
    let name = document.getElementById("name").value
    let nickname = document.getElementById("nickname").value
    let id = document.getElementById("id").value
    let pw = document.getElementById("pw").value
    let checkpw = document.getElementById("checkpw").value
    let phone = document.getElementById("phone").value
    let birthday = document.getElementById("birthday").value
    let gender_man = document.getElementById("gender_man").checked
    let gender_woman = document.getElementById("gender_woman").checked

    let pwd1 = document.querySelector('#pw');
    let pwd2 = document.querySelector('#checkpw');
    let form = document.getElementById('form');


    if (name != "" && id != "" && pw !== "" && checkpw !== "" && phone != "" && birthday != "" && (gender_woman || gender_man) && nickname != "") {

        form.submit();

    } else {

        // 이름 확인
        if (name == "") {
            document.getElementById("nameError").innerHTML = "이름이 올바르지 않습니다."
            document.getElementById("name").focus()

        } else {
            document.getElementById("nameError").innerHTML = ""

        }

        // 이름 확인
        if (nickname == "") {
            document.getElementById("nicknameError").innerHTML = "닉네임이 올바르지 않습니다."
            document.getElementById("nickname").focus()

        } else {
            document.getElementById("nicknameError").innerHTML = ""

        }

        // 아이디 확인
        if (id == "") {
            document.getElementById("idError").style.display = "block";
            document.getElementById("idAvailability").style.display = "none";
            document.getElementById("idError").innerHTML = "아이디가 올바르지 않습니다."
            document.getElementById("id").focus()

        } else {
            document.getElementById("idError").innerHTML = ""

        }

        if (checkpw !== pw || checkpw == "" || pw == "") {
            document.getElementById("checkpwError").style.color = "rgba(248,42,42,0.7)";
            document.getElementById("pwError").innerHTML = "비밀번호를 다시 입력해주세요."
            document.getElementById("checkpwError").innerHTML = ""
            document.getElementById("pw").innerHTML = ""
            document.getElementById("checkpw").innerText = ""
            document.getElementById("pw").focus()

        } else if (checkpw == pw && pw !== "" && checkpw !== "") {
            document.getElementById("checkpwError").style.color = "rgba(16,201,197,0.7)";
            document.getElementById("checkpwError").innerHTML = "비밀번호 일치함"
            document.getElementById("pwError").innerHTML = ""

        }


        // 연락처 확인
        if (phone == "" || phone.length !== 11) {
            document.getElementById("phoneError").innerHTML = "연락처 확인"
            document.getElementById("phone").focus()

        } else {
            document.getElementById("phoneError").innerHTML = ""

        }

        // 생년월일 확인
        if (birthday == "") {
            document.getElementById("birthdayError").innerHTML = "생년월일 확인"
            document.getElementById("birthday").focus()

        } else {
            document.getElementById("birthdayError").innerHTML = ""

        }

        // 성별체크확인
        if (!gender_man && !gender_woman) {
            document.getElementById("genderError").innerHTML = "성별 선택"

        } else {
            document.getElementById("genderError").innerHTML = ""

        }
    }
}