const showPasswordBtns = document.getElementsByClassName("show-password-btn");
const copyPasswordBtns = document.getElementsByClassName("copy-password-btn");

const originalClassesShowBtn = showPasswordBtns[0].getAttribute("class");
const originalClassesCopyBtn = copyPasswordBtns[0].getAttribute("class");

Array.prototype.map.call(showPasswordBtns, element => 
    element.addEventListener("click", showPassword)
);

Array.prototype.map.call(copyPasswordBtns, element => 
    element.addEventListener("click", copyPassword)
);

// For opening the note modal
function showNoteModal(noteId, noteTitle, noteDescription) {
        console.log(noteTitle)

    if(noteId) {
        const noteForm = document.getElementById("note-form");
        noteForm.setAttribute("action", `/note/${noteId}`);
    }

    $('#note-id').val(noteId ? noteId : '');
    $('#note-title-input').val(noteTitle ? noteTitle : '');
    $('#note-description').val(noteDescription ? noteDescription : '');
    $('#noteModal').modal('show');
}

// For opening the credentials modal
function showCredentialModal(credentialId, url, username, password) {

    if(credentialId) {
        const credentialForm = document.getElementById("credential-form");
        credentialForm.setAttribute("action", `/credential/${credentialId}`);
    }

    $('#credential-id').val(credentialId ? credentialId : '');
    $('#credential-url').val(url ? url : '');
    $('#credential-username').val(username ? username : '');
    $('#credential-password').val(password ? password : '');
    $('#credentialModal').modal('show');
}

/*
    gets id from event and finds the password input
    alternates password input's type between text and password
*/
function showPassword(e) {
    const id = e.target.id.split("-")[3];
    const passwordInput = getPasswordInput(id); 
    const clickedShowPasswordBtn = showPasswordBtns[id - 1];
    if(passwordInput.getAttribute("type") === "password") {
        passwordInput.setAttribute("type", "text");
        // document.getElementById(`show-password-btn-${e.target.id}`).setAttribute("btn fa fa-eye-slash show-password-btn")
        clickedShowPasswordBtn.setAttribute("class", "btn fa fa-eye-slash show-password-btn");
    } else {
        passwordInput.setAttribute("type", "password");
        clickedShowPasswordBtn.setAttribute("class", originalClassesShowBtn);
    }
}
/*        
    gets value from password input and copies it to user's clipboard
*/
function copyPassword(e) {
    const passwordInput = getPasswordInput(e.target.id.split("-")[3]); 
    const value = passwordInput.value;
    const placeholder = document.createElement("input");
    document.body.append(placeholder);
    placeholder.value = value;
    placeholder.select();
    placeholder.setSelectionRange(0, 99999);
    document.execCommand("copy");
    document.body.removeChild(placeholder);
    // TODO confirm that it has been copied
    e.target.textContent = "Copied!";
    e.target.setAttribute("class", "btn show-password-btn");
    setTimeout(() => {
        e.target.textContent = "";
        e.target.setAttribute("class", originalClassesCopyBtn);
    }, 800)
}

/*
    finds and returns corresponding passwordInput
*/
function getPasswordInput(id) {
    return document.getElementById(`user-password-${id}`);
}