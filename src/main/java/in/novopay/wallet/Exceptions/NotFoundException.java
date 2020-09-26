package in.novopay.wallet.Exceptions;

import java.util.function.Supplier;

public class NotFoundException extends Exception{
    String message;
    public NotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String toString(){
        return "Not found for: "+message;
    }
}
