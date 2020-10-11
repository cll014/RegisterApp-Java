document.addEventListener<"DOMContentLoaded", () =>
{
    getTransactionIdElement().addEventListener("click", transActionClick); 
    getProductListIdElement().addEventListener("click", productActionClick);  
    getEmployeeCreateIdElement().addEventListener("click", empCreateActionClick); 
    getEmployeeDetailsIdElement().addEventListener("click", empDetailsActionClick);
    getReportIdElement().addEventListener("click", reportActionClick);
}

//Button Functionality
function transActionClick(event)
{
    displayError("Functionality has not been implemented yet.");
}

function productActionClick(event)
{
    window.location.assign.apply("productListing");
}

function empCreateActionClick(event)
{
    window.location.assign.apply("employeeDetail");
}

function empDetailsActionClick(event)
{
    displayError("Functionality has not been implemented yet.");
}

function reportActionClick(event)
{
    displayError("Functionality has not been implemented yet.");
}

//Getters
function getTransactionIdElement()
{
    return document.getElementById("startTransactionButton");
}

function getProductListIdElement()
{
    return document.getElementById("viewProductsButton");
}

function getCreateEmployeeIdElement()
{
    return document.getElementById("createEmployeeButton");
}

function getProductSalesReportIdElement()
{
    return document.getElementById("productSalesReportButton");
}

function getCashierSalesReportIdElement()
{
    return document.getElementById("cashierSalesReportButton");
}

