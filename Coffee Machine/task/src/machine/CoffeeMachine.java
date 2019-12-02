package machine;
import java.util.Scanner;
public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Write how many cups of coffee you will need:");
        int countCup=scanner.nextInt();
        System.out.println(String.format(
                "For %d cups of coffee you will need:\n" +
                        "%d ml of water\n"+
                        "%d ml of milk\n"+
                        "%d g of coffee beans"
        ,countCup,countCup*200,countCup*50,countCup*15));
    }
}
