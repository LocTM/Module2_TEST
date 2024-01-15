package controller;

import model.Product;

import java.io.*;
import java.util.*;

public class ProductManagementApp {
    private static final List<Product> productList = new ArrayList<>();
    private static final String CSV_FILE_PATH = "data/products.csv";

    public static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Xem danh sách");
        System.out.println("2. Thêm mới");
        System.out.println("3. Cập nhật");
        System.out.println("4. Xóa");
        System.out.println("5. Sắp xếp");
        System.out.println("6. Tìm sản phẩm đắt nhất");
        System.out.println("7. Đọc File");
        System.out.println("8. Ghi File");
        System.out.println("9. Thoát");
    }

    public static int getChoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Chọn chức năng: ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.next();
            return -1;
        }
    }

    public static void displayProducts() {
        Scanner scanner = new Scanner(System.in);
        int startIndex = 0;
        while (startIndex < productList.size()) {
            int endIndex = Math.min(startIndex + 5, productList.size());
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(productList.get(i));
            }
            System.out.print("Nhấn Enter để tiếp tục");
            scanner.nextLine();
            startIndex += 5;
        }
    }
    public static void addProduct() {
        Scanner scanner = new Scanner(System.in);
        Product newProduct = new Product();
        System.out.println(" Lựa chọn “Thêm mới”.");
        System.out.print("Code: ");
        newProduct.setCode(scanner.nextLine());
        System.out.print("Tên: ");
        newProduct.setName(scanner.nextLine());
        System.out.print("Giá: ");
        newProduct.setPrice(scanner.nextDouble());
        System.out.print("Số lượng: ");
        newProduct.setQuantity(scanner.nextInt());
        Scanner scanner1 = new Scanner(System.in);
        System.out.print("Mô tả: ");
        newProduct.setDescription(scanner1.nextLine());

        productList.add(newProduct);
        displayProductList(productList);

        System.out.println("Sản phẩm đã được thêm thành công");
    }

    public static void updateProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập vào code cần sửa: ");
        String productCode = scanner.next();

        for (Product product : productList) {
            if (product.getCode().equals(productCode)) {
                System.out.println("Nhập vào các thông tin cần sửa");
                System.out.print("Code: ");
                Scanner scanner1 = new Scanner(System.in);
                product.setCode(scanner1.nextLine());
                Scanner scanner3 = new Scanner(System.in);
                System.out.print("Name: ");
                product.setName(scanner3.nextLine());
                System.out.print("Price: ");
                product.setPrice(scanner.nextDouble());
                System.out.print("Quantity: ");
                Scanner scanner2 = new Scanner(System.in);
                product.setQuantity(scanner2.nextInt());
                Scanner scanner4 = new Scanner(System.in);
                System.out.print("Description: ");
                product.setDescription(scanner4.nextLine());
                displayProductList(productList);
                System.out.println("Cập nhật thành công");
                return;
            }
        }
        System.out.println("Không tìm thấy Code " + productCode);
    }

    public static void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập code sản phẩm cần xóa: ");
        String productCode = scanner.next();

        for (Product product : productList) {
            if (product.getCode().equals(productCode)) {
                System.out.print("Bạn chắc chắn muốn xóa (Y/N): ");
                String confirmation = scanner.next().toUpperCase();
                if (confirmation.equals("Y")) {
                    displayProductList(productList);
                    productList.remove(product);
                    displayProductList(productList);
                    System.out.println("Đã xóa .");
                } else {
                    System.out.println("Đã cancel");
                }
                return;
            }
        }

        System.out.println("Không tìm thấy sản phẩm với code là: " + productCode);
    }
    public static void sortProducts() {
        System.out.println("Sắp xếp sản phẩm");
        System.out.println("1. Sắp xếp sản phẩm giá tăng dần");
        System.out.println("2. Sắp xếp sản phẩm giá giảm dần");
        System.out.println("3. Quay lại menu");
        int sortChoice = getChoice();

        switch (sortChoice) {
            case 1:
                productList.sort(Comparator.comparingDouble(Product::getPrice));
                displayProductList(productList);
                break;
            case 2:
                productList.sort(Comparator.comparingDouble(Product::getPrice).reversed());
                displayProductList(productList);
                break;
            case 3:
                return;
            default:
                System.out.println("Lựa chọn không đúng");
        }
    }
    public static void findMostExpensiveProduct() {
        if (productList.isEmpty()) {
            System.out.println("Không có sản phẩm nào trong kho");
            return;
        }

        Product mostExpensiveProduct = productList.stream()
                .max(Comparator.comparingDouble(Product::getPrice))
                .orElse(null);

        System.out.println("Sản phẩm đắt nhất là:");
        System.out.println(mostExpensiveProduct);
    }

    public static void readFromCSVFile() {
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
    private static void displayProductList(List<Product> productList) {
        System.out.println("Danh sách sản phẩm:");
        for (Product product : productList) {
            System.out.println(product);
        }
    }


}
