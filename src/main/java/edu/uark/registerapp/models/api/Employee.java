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

    public UUID getID() {
        return this.id;
    }

    public Employee setID(final UUID id) {
        this.id = id;
        return this;
    }
}
