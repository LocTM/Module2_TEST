package storage;

import model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static controller.ProductManagementApp.CSV_FILE_PATH;
import static controller.ProductManagementApp.productList;

public class WriteReadFile {
    public static void readFromCSVFile() {
         List<Product> productList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Sẽ ghi đè danh sách sản phẩm hiện tại (Y/N): ");
        String confirmation = scanner.next().toUpperCase();

        if (confirmation.equals("Y")) {
            productList.clear();

            try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    Product product = new Product();
                    product.setCode(parts[0]);
                    product.setName(parts[1]);
                    product.setPrice(Double.parseDouble(parts[2]));
                    product.setQuantity(Integer.parseInt(parts[3]));
                    product.setDescription(parts[4]);
                    productList.add(product);
                }
                System.out.println("Thành công.");
            } catch (IOException e) {
                System.out.println("Lỗi" + e.getMessage());
            }
        }
    }

    public static void writeToCSVFile() {
        try {
            File file = new File("data/products.csv");

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Product product : productList) {
                    writer.write(String.format("%s,%s,%.2f,%d,%s%n",
                            product.getCode(), product.getName(), product.getPrice(),
                            product.getQuantity(), product.getDescription()));
                }
                System.out.println("Thành công");
            }
        } catch (IOException e) {
            System.out.println("Lỗi" + e.getMessage());
        }
    }
}

