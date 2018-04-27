import products.Product;
import products.ProductToDrink;
import products.ProductToEat;
import products.Souvenir;

public class Main {

    public static void main(String[] args) {
        ProductToDrink drink = new ProductToDrink("Water", 1.2, "10.10.2020");
        ProductToEat food = new ProductToEat("Burger", 3, "10.10.2018");
        Souvenir souvenir = new Souvenir("statuet", 5);

        drink.consume();
        food.consume();
        souvenir.use();
    }
}
