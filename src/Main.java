import interfaces.IConsumable;
import interfaces.IUsable;
import products.Product;
import products.ProductToDrink;
import products.ProductToEat;
import products.Souvenir;

public class Main {

    public static void main(String[] args) {
        Product drink = new ProductToDrink("Water", 1.2, "10.10.2020");
        Product food = new ProductToEat("Burger", 3, "10.10.2018");
        Product souvenir = new Souvenir("statuet", 5);

        ((IConsumable)drink).consume();
        ((IConsumable)food).consume();
        ((IUsable)souvenir).use();

        //---------------------------------------------------------------------------------------//
        // HOW FEATURES SHOULD WORK
        // park.getperson.buy/consume/use/listProducts
        // park.buyticket(someperson)
        //
        //---------------------------------------------------------------------------------------//

    }
}
