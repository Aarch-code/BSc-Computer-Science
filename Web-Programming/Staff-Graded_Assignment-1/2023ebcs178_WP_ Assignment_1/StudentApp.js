//<--Tandel Prachi Raghunath 2023EBCS178-->

class Student {
    constructor(name, dob, age, mobile, email) {
        this.name = name;
        this.dob = dob;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
    }

    displayInfo() {
        console.log("----- Student Information -----");
        console.log(`Name: ${this.name}`);
        console.log(`Date of Birth: ${this.dob}`);
        console.log(`Age: ${this.age}`);
        console.log(`Mobile: ${this.mobile}`);
        console.log(`Email: ${this.email}`);
        console.log("--------------------------------\n");
        alert(`Name: ${this.name}\nDOB: ${this.dob}\nAge: ${this.age}\nMobile: ${this.mobile}\nEmail: ${this.email}`);
    }
}

document.getElementById('dob').addEventListener('input', function() {
    var dob = this.value;
    var regex = /^\d{2}\/\d{2}\/\d{4}$/;
    if (regex.test(dob)) {
        calculateAge(dob);
    }
});

function clearInputs() {
    document.getElementById('name').value = "";
    document.getElementById('dob').value = "";
    document.getElementById('age').value = "";
    document.getElementById('mobile').value = "";
    document.getElementById('email').value = "";
    document.getElementById('retrieveMobile').value = "";
}

function validateName() {
    var name = document.getElementById('name').value;
    var regex = /^[a-zA-Z\s]+$/;
    if (!regex.test(name) || name.trim() === "") {
        alert("Invalid Name: Only alphabets and spaces are allowed.");
        document.getElementById('name').value = "";
        return false;
    }
    return true;
}

function validateDOB() {
    var dob = document.getElementById('dob').value;
    var regex = /^\d{2}\/\d{2}\/\d{4}$/;
    if (!regex.test(dob) || dob.trim() === "") {
        alert("Invalid Date of Birth: Use the format dd/mm/yyyy.");
        document.getElementById('dob').value = "";
        return false;
    } else {
        var parts = dob.split('/');
        var day = parseInt(parts[0], 10);
        var month = parseInt(parts[1], 10);
        var year = parseInt(parts[2], 10);

        if (month < 1 || month > 12 || day < 1 || day > 31) {
            alert("Invalid Date of Birth: Day must be between 01 and 31 and Month between 01 and 12.");
            document.getElementById('dob').value = "";
            return false;
        }

        if ((month === 4 || month === 6 || month === 9 || month === 11) && day > 30) {
            alert("Invalid Date of Birth: The selected month has only 30 days.");
            document.getElementById('dob').value = "";
            return false;
        }

        if (month === 2) {
            var isLeap = (year % 4 === 0 && year % 100 !== 0) || (year % 400 === 0);
            if ((isLeap && day > 29) || (!isLeap && day > 28)) {
                alert("Invalid Date of Birth: February in the selected year has only " + (isLeap ? "29" : "28") + " days.");
                document.getElementById('dob').value = "";
                return false;
            }
        }

        calculateAge(dob);
    }
    return true;
}

function calculateAge(dob) {
    var parts = dob.split('/');
    var birthDate = new Date(parts[2], parts[1] - 1, parts[0]);
    var today = new Date();
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    document.getElementById('age').value = age;
}

function validateMobile() {
    var mobile = document.getElementById('mobile').value;
    var regex = /^[1-9]\d{9}$/;
    if (!regex.test(mobile) || mobile.trim() === "") {
        alert("Invalid Mobile Number: Only numbers are allowed, and it must be a 10-digit number starting with 1-9.");
        document.getElementById('mobile').value = "";
        return false;
    }
    return true;
}

function validateEmail() {
    var email = document.getElementById('email').value;
    var regex = /^[^\s@]+@[^\s@]+\.(com|in)$/;
    if (!regex.test(email) || email.trim() === "" || email.startsWith('@')) {
        alert("Invalid Email ID: Should contain '@', should not begin with '@', and should end with '.com' or '.in'.");
        document.getElementById('email').value = "";
        return false;
    }
    return true;
}

document.getElementById('studentForm').onsubmit = function(event) {
    event.preventDefault();
    validateForm();
};

function validateForm() {
    var isNameValid = validateName();
    var isDOBValid = validateDOB();
    var isMobileValid = validateMobile();
    var isEmailValid = validateEmail();

    if (isNameValid && isDOBValid && isMobileValid && isEmailValid) {
        var name = document.getElementById('name').value;
        var dob = document.getElementById('dob').value;
        var age = document.getElementById('age').value;
        var mobile = document.getElementById('mobile').value;
        var email = document.getElementById('email').value;

        var student = new Student(name, dob, age, mobile, email);
        document.cookie = "student=" + JSON.stringify(student);
        document.getElementById('studentForm').style.display = 'none';
        document.getElementById('nextPage').style.display = 'block';
    } else {
        alert("Please fill out all fields correctly.");
        return false;
    }
}

function retrieveCookie() {
    var mobile = document.getElementById('retrieveMobile').value;
    var cookies = document.cookie.split("; ");
    var studentCookie = cookies.find(cookie => cookie.startsWith("student="));
    if (studentCookie) {
        var student = JSON.parse(studentCookie.split("=")[1]);
        
        if (student.mobile === mobile) {
            (new Student(student.name, student.dob, student.age, student.mobile, student.email)).displayInfo();
        } else {
            alert("No student found with this mobile number.");
        }
    } else {
        alert("No student information available.");
    }
}
