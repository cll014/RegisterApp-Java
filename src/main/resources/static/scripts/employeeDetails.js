let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("saveButton").addEventListener("click", saveActionClick);

    const employeeFirstNameEditElement = getEmployeeEditFirstName();
    employeeFirstNameEditElement.focus();
    employeeFirstNameEditElement.select();
});

// Save
function saveOnClick(event){
    if(!validateSave()){
        return;
    }

    const saveActionElement = event.target;
    saveActionElement.disabled = true;

    const employeeId = getEmployeeId();
    const employeeIdIsDefined = (employeeId.trim() !== "");
    const saveActionUrl = ("/api/employee/" + (employeeIdIsDefined ? employeeId : ""));
    const saveEmployeeRequest = {
        id : employeeId,
        managerId : getEmployeeManagerId(),
        firstName : getEmployeeEditFirstName().value,
        lastName : getEmployeeEditLastName().value,
        password : getEmployeeEditPassword().value,
        classification : getEmployeeEditType().value
    };

    if(employeeIdIsDefined){
        ajaxPatch(saveActionURL, saveEmployeeRequest, (callbackResponse) => {
            saveActionElement.disabled = false;
            if(isSuccessReponse(callbackResponse)){
                completeSaveAction(callbackResponse);
            }
        });
    }
    else{
        ajaxPost(saveActionUrl, saveEmployeeRequest, (callbackResponse) =>{
            saveActionElement.disabled = false;
            if(isSuccessReponse(callbackResponse)){
                completeSaveAction(callbackResponse);
            }
        });
    }
}

function validateSave(){
    const firstNameEditElement = getEmployeeEditFirstName();
    if(firstNameEditElement.value.trim() === ""){
        displayError("Please provide a valid first name.")
        firstNameEditElement.focus();
        firstNameEditElement.select();
        return false;
    }
    const lastNameEditElement = getEmployeeEditLastName();
    if(lastNameEditElement.value.trim() === ""){
        displayError("Please provide a valid last name.");
        lastNameEditElement.focus();
        lastNameEditElement.select();
        return false;
    }
    const passwordEditElement = getEmployeeEditPassword();
    if(passwordEditElement.value.trim() === ""){
        displayError("Please provide a valid password");
        passwordEditElement.focus();
        passwordEditElement.select();
        return false;
    }
    if(passwordEditElement.value != getEmployeeEditConfirmedPassword()){
        displayError("Passwords do not match.");
        passwordEditElement.focus();
        passwordEditElement.select();
        return false;
    }
    const employeeEditTypeElement = getEmployeeEditType();
    if(!employeeEditTypeElement.closest("tr").classList.contains("hidden")){
        if(employeeEditTypeElement.value <= 0){
            displayError("Please provide a valid emplyoee type.");
            employeeEditTypeElement.focus();
            return false;
        }
    }
    return true;
}

function completeSaveAction(callbackResponse){
    if(callbackResponse.data == null){
        return;
    }
    if((callbackResponse.data.redirectUrl != null) && (callbackResponse.data.redirectUrl !== "")){
        window.location.replace(callbackResponse.data.redirectUrl);
        return;
    }
    displayEmployeeSavedAlertModel();
    
    const employeeEmployeeIdElement = getEmployeeEmployeeIdElement();
    const employeeEmployeeIdRowElement = employeeEmployeeIdElement.closest("tr");
    if(employeeEmployeeIdRowElement.classList.contains("hidden")){
        setEmployeeId(callbackResponse.data.id);
        employeeEmployeeIdElement.value = callbackResponse.data.employeeId;
        employeeEmployeeIdRowElement.classList.remove("hidden");
    }
}
function displayEmployeeSavedAlertModel(){
    if(hideEmployeeSavedAlertTimer){
        clearTimeout(hideEmployeeSavedAlertTimer);
    }

    const savedAlertModelElement = getSavedAlertModelElement();
    savedAlertModelElement.style.display = "none";
    savedAlertModelElement.style.display = "block";

    hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModel, 1200);
}
function hideEmployeeSavedAlertModel(){
    if(hideEmployeeSavedAlertTimer){
        clearTimeout(hideEmployeeSavedAlertTimer);
    }
    getSavedAlertModelElement().style.display = "none";
}

// Getters and Setters
function getEmployeeId(){
    return document.getElementById("employeeId").value;
}
function setEmployeeId(employeeId){
    document.getElementById("employeeId").value = employeeId;
}
function getEmployeeManagerId(){
    return document.getElementById("employeeManagerId").value;
}
function setEmployeeEmployeeId(){
    return getEmployeeEmployeeIdElement().value;
}
function setEmployeeEmployeeId(){
    return document.getElementById("employeeEmployeeId");
}
function getSavedAlertModelElement(){
    return document.getElementById("employeeSavedAlertModel");
}
function getEmployeeEditFirstName(){
    return document.getElementById("employeeFirstName");
}
function getEmployeeEditLastName(){
    return document.getElementById("employeeLastName");
}
function getEmployeeEditPassword(){
    return document.getElementById("employeeLastname");
}
function getEmployeeEditConfirmedPassword(){
    return document.getElementById("employeeConfirmPassword").value;
}
function getEmployeeEditType(){
    return document.getElementById("employeeType");
}