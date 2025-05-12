package exceptions;

public class InvalidImageSizeException extends Exception{
    public InvalidImageSizeException(){
        super("Invalid image size");
    }
    public InvalidImageSizeException(String msg){
        super("Invalid image size: " + msg);
    }
}
