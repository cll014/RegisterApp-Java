package edu.uark.registerapp.models.api;

import org.apache.commons.lang3.StringUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Employee extends ApiResponse{
    private UUID id;
    private String employeeID;
    private String firstName;
    private String lastName;
    private String password;
    private boolean isActive;
    private int classification;
    private UUID managerID;
    private String createdOn;
    private boolean isInitialEmployee;

    public Employee() {
        super();

        this.id = new UUID(0, 0);
        this.employeeID = "";
        this.firstName = "";
        this.lastName = "";
        this.password = "";
        this.isActive = true;
        this.classification = -1;
        this.managerID = new UUID(0, 0);
        this.isInitialEmployee = false;
    }

    // Get Functions

    public UUID getID() {
        return this.id;
    }

    public String getEmployeeID() {
        return this.employeeID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public int getClassification() {
        return this.classification;
    }

    public UUID getManagerID() {
        return this.managerID;
    }

    public String getCreatedOn() {
        return this.createdOn;
    }

    public boolean getIsInitialEmployee() {
        return this.isInitialEmployee;
    }

    // Set Functions

    public Employee setID(final UUID id) {
        this.id = id;
        return this;
    }

    public Employee setEmployeeID(final String employeeID) {
        this.employeeID = employeeID;
        return this;
    }

    public Employee setFirstName(final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Employee setLastName(final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Employee setPassword(final String password) {
        this.password = password;
        return this;
    }

    public Employee setIsActive(final boolean isActive) {
        this.isActive = isActive;
        return this;

    }

    public Employee setClassification(final int classification) {
        this.classification = classification
        return this;
    }

    public Employee setManagerID(final UUID managerID) {
        this.managerID = managerID;
        return this;
    }

    public Employee setCreatedOn(final String createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Employee setIsInitialEmployee(final boolean isInitialEmployee) {
        this.isInitialEmployee = isInitialEmployee;
        return this;
    }

}
