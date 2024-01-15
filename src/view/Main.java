package view;

import static controller.ProductManagementApp.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    addProduct();
                    break;
                case 3:
                    updateProduct();
                    break;
                case 4:
                    deleteProduct();
                    break;
                case 5:
                    sortProducts();
                    break;
                case 6:
                    findMostExpensiveProduct();
                    break;
                case 7:
                    readFromCSVFile();
                    break;
                case 8:
                    writeToCSVFile();
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Chọn từ 1 đến 9");
            }
        }
    }
}