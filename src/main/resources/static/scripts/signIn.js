document.addEventListener<"DOMContentLoaded", function(event)
{
    const employeeIdElement = getEmployeeIdElement();
    employeeIdElement.focus();
    employeeIdElement.select;
}

//Validate Employee ID and Password
function validateForm()
{
    const employeeIdElement = getEmployeeIdElement();

    if (isNaN(Number(employeeIdElement.value)) || (Number(employeeIdElement.value) <= 0))
    {
        displayError("Please provide a valid Employee ID. This should be a number, greater than 0.")

        return false;
    }

    const passwordElement = getPasswordElement();
    if ((passwordElement.value == null) || (passwordElement.value.trim() === ""))
    {
        displayError("This field may not be left blank. Please provide a valid password.");
        return false;
    }

    return true;
}

//Getter and Setter
function getEmployeeIdElement()
{
    return document.getElementById("employeeId");
}

function getPasswordElement()
{
    return document.getElementById("password");
}