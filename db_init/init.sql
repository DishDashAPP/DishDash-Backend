-- Create the user with password and grant privileges
DO
$$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_roles WHERE rolname = 'postgres') THEN
CREATE ROLE postgres LOGIN PASSWORD 'myPassword';
END IF;
END
$$;

-- Create the database if it does not exist
DO
$$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'postgres') THEN
      CREATE DATABASE postgres;
END IF;
END
$$;

-- Grant privileges to the user
GRANT ALL PRIVILEGES ON DATABASE postgres TO postgres;

-- Connect to the postgres database
\c postgres;

-- User Table (Interface)
CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    user_type VARCHAR(50) NOT NULL CHECK (user_type IN ('Customer', 'RestaurantOwner', 'DeliveryPerson'))
    );

-- Customer Table
CREATE TABLE IF NOT EXISTS customers (
                                         id INT PRIMARY KEY,
                                         address VARCHAR(255) NOT NULL,
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
    );

-- RestaurantOwner Table
CREATE TABLE IF NOT EXISTS restaurant_owners (
                                                 id INT PRIMARY KEY,
                                                 FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
    );

-- DeliveryPerson Table
CREATE TABLE IF NOT EXISTS delivery_persons (
                                                id INT PRIMARY KEY,
                                                location VARCHAR(255),
    current_order INT,
    status VARCHAR(50) NOT NULL CHECK (status IN ('ACTIVE', 'DEACTIVE', 'BUSY')),
    FOREIGN KEY (id) REFERENCES users(id) ON DELETE CASCADE
    );

-- Orders Table
CREATE TABLE IF NOT EXISTS orders (
                                      order_id SERIAL PRIMARY KEY,
                                      order_date DATE NOT NULL,
                                      status VARCHAR(50) NOT NULL CHECK (status IN ('PREPARING', 'DELIVERED', 'NOT_PAID', 'DELIVERING')),
    restaurant_owner_id INT,
    customer_id INT,
    delivery_person_id INT,
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (restaurant_owner_id) REFERENCES restaurant_owners(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (delivery_person_id) REFERENCES delivery_persons(id)
    );

-- OrderItems Table
CREATE TABLE IF NOT EXISTS order_items (
                                           order_item_id SERIAL PRIMARY KEY,
                                           order_id INT,
                                           food_id INT,
                                           price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
    );

-- Reviews Table
CREATE TABLE IF NOT EXISTS reviews (
                                       review_id SERIAL PRIMARY KEY,
                                       order_id INT,
                                       customer_id INT,
                                       comment TEXT,
                                       FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
    );

-- Rates Table
CREATE TABLE IF NOT EXISTS rates (
                                     rate_id SERIAL PRIMARY KEY,
                                     order_id INT,
                                     customer_id INT,
                                     point INT CHECK (point BETWEEN 1 AND 5),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
    );

-- Price Table
CREATE TABLE IF NOT EXISTS prices (
                                      price_id SERIAL PRIMARY KEY,
                                      amount DECIMAL(10, 2) NOT NULL,
    unit VARCHAR(50) NOT NULL CHECK (unit IN ('TOMAN'))
    );

-- Foods Table
CREATE TABLE IF NOT EXISTS foods (
                                     food_id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    menu_id INT
    );

-- Menus Table
CREATE TABLE IF NOT EXISTS menus (
                                     menu_id SERIAL PRIMARY KEY,
                                     restaurant_owner_id INT,
                                     FOREIGN KEY (restaurant_owner_id) REFERENCES restaurant_owners(id) ON DELETE CASCADE
    );

-- Categories Table
CREATE TABLE IF NOT EXISTS categories (
                                          category_id SERIAL PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL
    );

-- ShoppingCart Table
CREATE TABLE IF NOT EXISTS shopping_cart (
                                             cart_id SERIAL PRIMARY KEY,
                                             customer_id INT,
                                             restaurant_owner_id INT,
                                             FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_owner_id) REFERENCES restaurant_owners(id) ON DELETE CASCADE
    );

-- Order to ShoppingCart (Many-to-Many)
CREATE TABLE IF NOT EXISTS cart_items (
                                          cart_item_id SERIAL PRIMARY KEY,
                                          cart_id INT,
                                          order_item_id INT,
                                          FOREIGN KEY (cart_id) REFERENCES shopping_cart(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (order_item_id) REFERENCES order_items(order_item_id) ON DELETE CASCADE
    );

-- AuthenticationInfo Table
CREATE TABLE IF NOT EXISTS authentication_info (
                                                   user_id INT PRIMARY KEY,
                                                   username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- Token Table
CREATE TABLE IF NOT EXISTS tokens (
                                      token_id SERIAL PRIMARY KEY,
                                      value VARCHAR(255) NOT NULL,
    expiration_date TIMESTAMP NOT NULL,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    );

-- Transactions Table
CREATE TABLE IF NOT EXISTS transactions (
                                            transaction_id SERIAL PRIMARY KEY,
                                            order_id INT,
                                            price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL CHECK (status IN ('PAID', 'NOT_PAID')),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
    );

-- Payments Table
CREATE TABLE IF NOT EXISTS payments (
                                        payment_id SERIAL PRIMARY KEY,
                                        transaction_id INT,
                                        date_time TIMESTAMP NOT NULL,
                                        FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id) ON DELETE CASCADE
    );

-- Delivery Information
CREATE TABLE IF NOT EXISTS delivery_information (
                                                    delivery_id SERIAL PRIMARY KEY,
                                                    order_id INT,
                                                    delivery_person_id INT,
                                                    location VARCHAR(255),
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
    FOREIGN KEY (delivery_person_id) REFERENCES delivery_persons(id) ON DELETE CASCADE
    );

-- Initialize some sample data (optional)
INSERT INTO users (name, phone_number, email, user_type) VALUES ('John Doe', '1234567890', 'john.doe@example.com', 'Customer');
INSERT INTO customers (id, address) VALUES (1, '123 Main St, Springfield');
