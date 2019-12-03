package machine;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class CoffeeMachine {

    enum EdIzm{
        GRAMS("grams"),
        ML("ml"),
        CUP("disposable cups");

        private String str;
        EdIzm(String str){this.str=str;}
        public String getEdIzm(){return str;}
    }

    enum Ingreedients {
        WATER(EdIzm.ML,"water"),
        MILK(EdIzm.ML,"milk"),
        BEANS(EdIzm.GRAMS,"coffee beans"),
        CUPS(EdIzm.CUP,"coffee");

        private EdIzm edIzm;
        private String name;

        Ingreedients( EdIzm edIzm, String name){
            this.edIzm=edIzm;
            this.name=name;
        }

        public String getName(){return edIzm!=EdIzm.CUP?name:edIzm.getEdIzm();}

        public String getInputString(){
            return String.format("Write how many %s of %s do you want to add:",edIzm.getEdIzm(),name);
        }
    }

    enum Coffes{
        ESPRESSO(250,0,16,4,1),
        LATTE(350,75,20,7,1),
        CAPPUCCINO(200,100,12,6,1);
        private int water=0;
        private int milk=0;
        private int beans=0;
        private int cost=0;
        private int cup=0;
        Coffes(int water,int milk,int beans,int cost,int cup){
            this.water=water;
            this.milk=milk;
            this.beans=beans;
            this.cost=cost;
            this.cup=cup;
        }
        public int getWater(){return water;}
        public int getMilk(){return milk;}
        public int getBeans(){return beans;}
        public int getCost(){return cost;}
        public int getCup(){return cup;}
        public int getIngreedient(String ingreedientName){
            if (ingreedientName.equals(Ingreedients.WATER.name())) return water;
            else if (ingreedientName.equals(Ingreedients.MILK.name())) return milk;
            else if (ingreedientName.equals(Ingreedients.BEANS.name())) return beans;
            else if (ingreedientName.equals(Ingreedients.CUPS.name())) return cup;
            else throw  new IllegalArgumentException("Неверный ингридиент");
        }
    }

    Scanner scanner;



    CoffeeMachine(Scanner scanner){
        this.scanner=scanner;
    }

    private Map<Ingreedients,Integer> ingreedientMap=new TreeMap<>();
    public int getIngreedientCount(Ingreedients ingreedient){
        return this.ingreedientMap.getOrDefault(ingreedient,0);
    }
    public void addIngreedient(Ingreedients ingreedient, int count){
        this.ingreedientMap.put(ingreedient,this.ingreedientMap.getOrDefault(ingreedient,0)+count);
    }
    public void substrIngreedient(Ingreedients ingreedient, int count){
        ingreedientMap.put(ingreedient,ingreedientMap.getOrDefault(ingreedient,0)-count);
    }
    public void inputIngreedients(){
        for (Ingreedients ingreedient:Ingreedients.values())
        {
            System.out.println(ingreedient.getInputString());
            addIngreedient(ingreedient,Integer.parseInt(scanner.nextLine()));
        }
    }

    private int money=0;
    public int getMoney(){return money;}

    public boolean canBuy(Coffes coffes){
        for(Ingreedients ingreedient:ingreedientMap.keySet()){
            if (coffes.getIngreedient(ingreedient.name())>getIngreedientCount(ingreedient)){
                System.out.println(String.format("Sorry, not enough %s!", ingreedient.name));
                return false;
            }
        }
        return true;
    }
    public void buyCoffes(Coffes coffe){
        if (!canBuy(coffe)) return;
        System.out.println("I have enough resources, making you a coffee!");
        for(Ingreedients ingreedient:ingreedientMap.keySet()){
            substrIngreedient(ingreedient,coffe.getIngreedient(ingreedient.name()));
        }
        money+=coffe.cost;
    }
    public void buy(){
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String input=scanner.nextLine();
        if (input.equals("back")) {return;}
        buyCoffes(Coffes.values()[Integer.parseInt(input)-1]);
    }
    public void fill(){
        inputIngreedients();
    }
    public void take(){
        System.out.println(String.format("I gave you $%d",getMoney()));
        money=0;
    }

    public void action(){
        while(true) {
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
            String command = scanner.nextLine();
            System.out.println();
            if (command.equals("buy")) {
                buy();
            } else if (command.equals("fill")) {
                fill();
            } else if (command.equals(("take"))) {
                take();
            }
            else if (command.equals("remaining")) {
                System.out.println(this.toString());
            }
            else if (command.equals("exit")) {
                return;
            }
            System.out.println();
        }
    }
    @Override
    public String toString(){
        String str="The coffee machine has:"+"\n";
        for(Ingreedients ingreedient:ingreedientMap.keySet()){
            str+=String.format("%d of %s\n",getIngreedientCount(ingreedient),ingreedient.getName());
        }
        str+=String.format("$%d of money",getMoney());
        return  str;
    }

    {
        money=550;
        addIngreedient(Ingreedients.WATER,400);
        addIngreedient(Ingreedients.MILK,540);
        addIngreedient(Ingreedients.BEANS,120);
        addIngreedient(Ingreedients.CUPS,9);
    }


    public static void main(String[] args) {
        CoffeeMachine machine=new CoffeeMachine(new Scanner(System.in));
        machine.action();
    }
}