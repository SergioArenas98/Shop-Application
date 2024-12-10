# Shop Management System

This is a Java-based desktop application designed to manage inventory and sales for a retail shop. The application uses a graphical user interface (GUI) built with Java Swing and provides various functionalities to manage products, view inventory, track cash, and export data.

## Features

- **Export Inventory**: Export the current inventory to a specified format or file.
- **Manage Products**: Add new products, update stock, or remove products from the inventory.
- **View Inventory**: Display a list of all products currently in the inventory.
- **Manage Cash**: Track the current cash available in the shop’s cash register.
- **Keyboard Shortcuts**: Use hotkeys to quickly access various functionalities:
  - `0`: Export Inventory
  - `1`: Show Cash
  - `2`: Add Product
  - `3`: Add Stock
  - `5`: View Inventory
  - `9`: Remove Product

## Prerequisites

To run this project locally, you need to have the following installed:

- **Java 8 or higher**: The application is built using Java.
- **IDE**: It is recommended to use an Integrated Development Environment (IDE) like IntelliJ IDEA, Eclipse, or NetBeans to easily run and modify the project.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/SergioArenas98/Shop-Application.git
   ```
   
2. **Navigate to the project directory**:
   ```bash
   cd Shop-Application
   ```
   
3. **Open the project in your preferred IDE**.

4. **Run the project**:
   - Compile and run the main application from your IDE.
   - The GUI will appear, and you can begin managing products and inventory.

## Usage

- The **ShopView** is the main interface of the application. It provides buttons and keyboard shortcuts to interact with the shop’s functionalities.
- Upon running the application, the following actions can be performed:
  - **Export Inventory**: Save the inventory data to a file.
  - **Show Cash**: Display the current amount of money in the cash register.
  - **Add Product**: Add new products to the inventory.
  - **Add Stock**: Increase the stock of an existing product.
  - **View Inventory**: View all products in the inventory.
  - **Remove Product**: Delete a product from the inventory.

## Project Structure

- **`view/`**: Contains the Java Swing GUI components, such as buttons and panels.
- **`model/`**: Contains the business logic, including classes like `Shop` and `Product`.
- **`utils/`**: Contains helper classes, such as `Constants` for configuration values.

## Contributing

If you'd like to contribute to this project, feel free to fork the repository and submit a pull request. Please ensure your contributions are well-tested and documented.

### Steps for contributing:
1. Fork the repository.
2. Create a new branch for your feature or fix.
3. Implement the changes and test them thoroughly.
4. Submit a pull request with a clear description of the changes.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
