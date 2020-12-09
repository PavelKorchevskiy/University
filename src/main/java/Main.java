public class Main {
    private static final String HELLO = "Hello world";
    public static void main(String[] args) {
        System.out.println(getHelloMessage());
        System.out.println("Your number is " + getNumber());
    }
    public static String getHelloMessage() {
        return HELLO + "! This is the best app";
    }

    public static int getNumber() {
        return (int) (Math.random() * 100);
    }
}
