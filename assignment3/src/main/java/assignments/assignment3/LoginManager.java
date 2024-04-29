package assignments.assignment3;

import assignments.assignment3.systemCLI.*;

public class LoginManager {
    //Datafield
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;

    //Constructor
    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    //Getsystem method
    public UserSystemCLI getSystem(String role){
        if(role.equals("Customer")){
            return customerSystem;
        }else{
            return adminSystem;
        }
    }

}

