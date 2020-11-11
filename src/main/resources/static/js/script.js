const showPasswordBtns = document.getElementsByClassName("show-password-btn");

Array.prototype.map.call(showPasswordBtns, element => 
    element.addEventListener("click", showPassword)
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

function showPassword(e) {
    const id = e.target.id.split("-")[3];
    const passwordInput = document.getElementById(`user-password-${id}`);
    passwordInput.getAttribute("type") === "password" ? passwordInput.setAttribute("type", "text") : passwordInput.setAttribute("type", "password")
}