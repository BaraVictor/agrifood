-- Insert roles
INSERT INTO role (id, name) VALUES (1, 'VIEWER');
INSERT INTO role (id, name) VALUES (2, 'EDITOR');
INSERT INTO role (id, name) VALUES (3, 'ADMIN');

-- Insert businesses
INSERT INTO businesses (name, keyword, description, page_link) VALUES
('Bean Kingdom', 'legumi', 'Specialist in organic beans and legumes.', 'https://www.facebook.com/BeanKingdom'),
('The Olive House', 'olio', 'Olive oil directly from family-owned farms.', 'https://www.facebook.com/TheOliveHouse'),
('Cheese Delight', 'formaggio', 'Delightful artisan cheese selection.', 'https://www.facebook.com/CheeseDelight'),
('Wine Treasures', 'vino', 'Collector of rare Italian wines.', 'https://www.facebook.com/WineTreasures'),
('Artisan Wonders', 'prodotti artigianali', 'Handmade artisanal crafts.', 'https://www.facebook.com/ArtisanWonders'),
('Fresh & Fruity', 'frutta', 'Premium selection of organic fruits.', 'https://www.facebook.com/FreshAndFruity'),
('Jam Haven', 'conserve', 'Homegrown fruits turned into jams.', 'https://www.facebook.com/JamHaven'),
('The Spice Rack', 'spezie', 'Rare spices for the gourmet kitchen.', 'https://www.facebook.com/TheSpiceRack'),
('Pure Olive', 'olio', 'Olive oil crafted with care.', 'https://www.facebook.com/PureOlive'),
('Cheese Villa', 'formaggio', 'Traditional and organic cheese makers.', 'https://www.facebook.com/CheeseVilla'),
('Vinoteca', 'vino', 'Curator of classic and vintage wines.', 'https://www.facebook.com/Vinoteca'),
('Crafted Elegance', 'prodotti artigianali', 'Elegant handcrafted products.', 'https://www.facebook.com/CraftedElegance'),
('Fruit Orchard Co.', 'frutta', 'Provider of fresh, seasonal fruits.', 'https://www.facebook.com/FruitOrchardCo'),
('Berry Bliss', 'conserve', 'Berries turned into delicious preserves.', 'https://www.facebook.com/BerryBliss'),
('Nutri Beans', 'legumi', 'Nutrient-rich legumes distributor.', 'https://www.facebook.com/NutriBeans'),
('World of Spices', 'spezie', 'Spices from across the globe.', 'https://www.facebook.com/WorldOfSpices'),
('Gold Olive', 'olio', 'Golden quality in every drop of oil.', 'https://www.facebook.com/GoldOlive'),
('Cheese Corner', 'formaggio', 'Wide range of Italian cheeses.', 'https://www.facebook.com/CheeseCorner'),
('Red Grapes', 'vino', 'Red wine specialists from Italy.', 'https://www.facebook.com/RedGrapes'),
('Artisan Essence', 'prodotti artigianali', 'Crafting excellence in handmade goods.', 'https://www.facebook.com/ArtisanEssence'),
('Sunny Fruits', 'frutta', 'Fresh fruits for every season.', 'https://www.facebook.com/SunnyFruits'),
('Jam Jar', 'conserve', 'Homemade jams with natural ingredients.', 'https://www.facebook.com/JamJar'),
('Green Lentils', 'legumi', 'Organic green lentils and more.', 'https://www.facebook.com/GreenLentils'),
('Spice Masters', 'spezie', 'Masterful selection of global spices.', 'https://www.facebook.com/SpiceMasters'),
('Extra Virgin Oil', 'olio', 'Best quality extra virgin olive oil.', 'https://www.facebook.com/ExtraVirginOil'),
('Cheese Delight', 'formaggio', 'Gourmet cheese from local producers.', 'https://www.facebook.com/CheeseDelight'),
('Wine Cellars', 'vino', 'Fine Italian wine cellars.', 'https://www.facebook.com/WineCellars'),
('Handmade Heritage', 'prodotti artigianali', 'Traditional handmade artisan goods.', 'https://www.facebook.com/HandmadeHeritage'),
('Citrus Farm', 'frutta', 'Supplier of fresh citrus fruits.', 'https://www.facebook.com/CitrusFarm'),
('Preserve Love', 'conserve', 'Homemade preserves with passion.', 'https://www.facebook.com/PreserveLove'),
('Chickpea King', 'legumi', 'Supplier of organic chickpeas.', 'https://www.facebook.com/ChickpeaKing'),
('Herb Bliss', 'spezie', 'Blissful blends of herbs and spices.', 'https://www.facebook.com/HerbBliss'),
('Olive Oil Creations', 'olio', 'Innovative olive oil blends.', 'https://www.facebook.com/OliveOilCreations'),
('Fromaggio Fresco', 'formaggio', 'Fresh Italian cheese specialists.', 'https://www.facebook.com/FromaggioFresco'),
('Vino Classico', 'vino', 'Classic Italian wines for every taste.', 'https://www.facebook.com/VinoClassico'),
('Crafted with Care', 'prodotti artigianali', 'Handcrafted items with attention to detail.', 'https://www.facebook.com/CraftedWithCare'),
('Berry Fresh', 'frutta', 'Fresh berries from local farms.', 'https://www.facebook.com/BerryFresh'),
('Gourmet Preserves', 'conserve', 'Premium preserves and jams.', 'https://www.facebook.com/GourmetPreserves'),
('Harvest Beans', 'legumi', 'Supplier of fresh, high-quality beans.', 'https://www.facebook.com/HarvestBeans'),
('World Spices', 'spezie', 'World-class spices at your doorstep.', 'https://www.facebook.com/WorldSpices'),
('Olive Grove', 'olio', 'Olive oil straight from the grove.', 'https://www.facebook.com/OliveGrove'),
('Italian Cheese Hub', 'formaggio', 'Hub for Italian cheese enthusiasts.', 'https://www.facebook.com/ItalianCheeseHub'),
('Grape Garden', 'vino', 'High-quality Italian wines.', 'https://www.facebook.com/GrapeGarden'),
('Handcraft Heaven', 'prodotti artigianali', 'Artisan crafts from local creators.', 'https://www.facebook.com/HandcraftHeaven'),
('Apple Orchard', 'frutta', 'Supplier of organic apples.', 'https://www.facebook.com/AppleOrchard'),
('Homemade Jars', 'conserve', 'Preserves made from local fruits.', 'https://www.facebook.com/HomemadeJars'),
('Nutty Legumes', 'legumi', 'Supplier of organic nuts and legumes.', 'https://www.facebook.com/NuttyLegumes'),
('Spice Heritage', 'spezie', 'Heritage spices from around the globe.', 'https://www.facebook.com/SpiceHeritage'),
('Oliva Nuova', 'olio', 'New and innovative olive oil blends.', 'https://www.facebook.com/OlivaNuova'),
('Cheese Bliss', 'formaggio', 'Cheese for every taste.', 'https://www.facebook.com/CheeseBliss'),
('The Vineyard', 'vino', 'Exclusive wines from Italian vineyards.', 'https://www.facebook.com/TheVineyard'),
('Tradition Crafts', 'prodotti artigianali', 'Crafts steeped in tradition.', 'https://www.facebook.com/TraditionCrafts'),
('Freshly Picked', 'frutta', 'Hand-picked fresh fruits.', 'https://www.facebook.com/FreshlyPicked'),
('Jam & Jelly', 'conserve', 'Homemade jams and jellies.', 'https://www.facebook.com/JamAndJelly'),
('Bean Delight', 'legumi', 'Premium beans for every dish.', 'https://www.facebook.com/BeanDelight'),
('Spice Haven', 'spezie', 'A haven of aromatic spices.', 'https://www.facebook.com/SpiceHaven');

-- Insert customers
INSERT INTO customers (name, email, phone) VALUES
('John Doe', 'john.doe@example.com', '123-456-7890'),
('Jane Smith', 'jane.smith@example.com', '234-567-8901'),
('Alice Johnson', 'alice.johnson@example.com', '345-678-9012'),
('Robert Brown', 'robert.brown@example.com', '456-789-0123'),
('Emily Davis', 'emily.davis@example.com', '567-890-1234'),
('Michael Wilson', 'michael.wilson@example.com', '678-901-2345'),
('Jessica Taylor', 'jessica.taylor@example.com', '789-012-3456'),
('Daniel Thomas', 'daniel.thomas@example.com', '890-123-4567'),
('Sophia Lee', 'sophia.lee@example.com', '901-234-5678'),
('James Martin', 'james.martin@example.com', '123-890-4567'),
('Olivia Clark', 'olivia.clark@example.com', '234-901-5678'),
('William Rodriguez', 'william.rodriguez@example.com', '345-012-6789'),
('Ava Lewis', 'ava.lewis@example.com', '456-123-7890'),
('Lucas Walker', 'lucas.walker@example.com', '567-234-8901'),
('Mia Young', 'mia.young@example.com', '678-345-9012'),
('Elijah Hall', 'elijah.hall@example.com', '789-456-0123'),
('Charlotte Allen', 'charlotte.allen@example.com', '890-567-1234'),
('Benjamin Scott', 'benjamin.scott@example.com', '901-678-2345'),
('Amelia Wright', 'amelia.wright@example.com', '123-789-3456'),
('Mason King', 'mason.king@example.com', '234-890-4567');
-- Insert users
INSERT INTO user (username, password, email, role_id) VALUES
('viewerUser', '$2a$12$70Eu.EKWIN5Ko/riuA24EOQBz8ccfi7IzgGSXY4XFSGWSbyjP9A5S', 'viewer@example.com', 1), -- Password: viewer123
('workerUser', '$2a$12$NEVmAc7TiUSiiqaI9OBiZeKjSZQr27FnZZQVtC0FS/7NgwdjjOXMq', 'worker@example.com', 2), -- Password: worker123
('managerUser', '$2a$12$DLPFJkQLTipXtSi.RvpYxe50leTjXTWm/rVx2MkPC5rdTI1ybe9AS', 'manager@example.com', 3); -- Password: admin123


