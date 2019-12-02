package machine;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class CoffeeMachine {

    enum EdIzm{
        gram("grams"),
        ml("ml");
        private String str;
        EdIzm(String str){this.str=str;}
        public String getEdIzm(){return str;}
    }

    enum Ingreedients {
        WATER(200,EdIzm.ml,"water"),
        MILK(50,EdIzm.ml,"milk"),
        BEANS(15,EdIzm.gram,"coffee beans");

        private int countOfOne;
        private EdIzm edIzm;
        private String name;

        Ingreedients(int countOfOne, EdIzm edIzm, String name){
            this.countOfOne = countOfOne;
            this.edIzm=edIzm;
            this.name=name;
        }

        public int getCountOfOne(){return countOfOne;}
        public String getInputString(){
            return String.format("Write how many %s of %s the coffee machine has: ",edIzm.getEdIzm(),name);
        }
        public int getCountCup(int countAll){return countAll/countOfOne;}
    }

    Scanner scanner;

    CoffeeMachine(Scanner scanner){
        this.scanner=scanner;
    }

    private Map<Ingreedients,Integer> ingreedientMap=new TreeMap<>();
    public void AddIngreedient(Ingreedients ingreedient, int count){
        ingreedientMap.put(ingreedient,count);
    }
    public int getIngreedientCount(Ingreedients ingreedient){
        return ingreedientMap.get(ingreedient);
    }
    public void inputIngreedients(){
        for (Ingreedients ingreedient:Ingreedients.values())
        {
            System.out.println(ingreedient.getInputString());
            AddIngreedient(ingreedient,Integer.parseInt(scanner.nextLine()));
        }
    }

    private int needCupCount=0;
    public int getNeedCountCup(){return needCupCount;}
    public void setNeedCupCount(int needCupCount){this.needCupCount=needCupCount;}
    public void inputNeedCupCount(){
        System.out.println("Write how many cups of coffee you will need: ");
        setNeedCupCount(Integer.parseInt(scanner.nextLine()));
    }

    public int getAvailableCountCup(){
        int min=Integer.MAX_VALUE;
        for (Ingreedients ingreedient:ingreedientMap.keySet())
        {
            int count=getIngreedientCount(ingreedient)/ingreedient.getCountOfOne();
            if (count<min){
                min=count;
            }
        }
        return min;
    }
    public String getResult(){
        int availableCountCup= getAvailableCountCup();
        if (availableCountCup== getNeedCountCup()){
            return "Yes, I can make that amount of coffee";
        }
        else if (availableCountCup>getNeedCountCup()){
            return String.format("Yes, I can make that amount of coffee (and even %d more than that)",availableCountCup-getNeedCountCup());
        }
        else{
            return String.format("No, I can make only %d cup(s) of coffee",availableCountCup);
        }
    }

    public static void main(String[] args) {
        CoffeeMachine machine=new CoffeeMachine(new Scanner(System.in));
        machine.inputIngreedients();
        machine.inputNeedCupCount();
        System.out.println(machine.getResult());
    }
}
