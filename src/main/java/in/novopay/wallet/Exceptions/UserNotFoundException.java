package in.novopay.wallet.Exceptions;

import java.util.function.Supplier;

public class UserNotFoundException extends Exception{
    String message;
    public UserNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return "User not found for: "+message;
    }
}
