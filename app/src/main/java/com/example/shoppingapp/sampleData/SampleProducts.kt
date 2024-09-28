package com.example.shoppingapp.sampleData

data class Product(
    val productId: String,
    val name: String,
    val category: Category,
    val vendorId: String,
    val isActive: Boolean,
    val price: Double,
    val description: String,
    val stock: Int
)



val sampleProducts = listOf(
    // Office Furniture
    Product(
        productId = "1",
        name = "Office Chair",
        category = officeFurnitureCategory,
        vendorId = "vendor1",
        isActive = true,
        price = 145.69,
        description = "Ergonomic office chair with adjustable height and lumbar support." +
                "Ergonomic office chair with adjustable height and lumbar support." +
                "Ergonomic office chair with adjustable height and lumbar support." +
                "Ergonomic office chair with adjustable height and lumbar support.",
        stock = 10
    ),
    Product(
        productId = "6",
        name = "Office Desk",
        category = officeFurnitureCategory,
        vendorId = "vendor1",
        isActive = true,
        price = 199.99,
        description = "Spacious office desk with cable management and storage drawers.",
        stock = 7
    ),
    Product(
        productId = "11",
        name = "Filing Cabinet",
        category = officeFurnitureCategory,
        vendorId = "vendor1",
        isActive = true,
        price = 89.99,
        description = "Steel filing cabinet with three drawers and a lock.",
        stock = 15
    ),
    Product(
        productId = "12",
        name = "Conference Table",
        category = officeFurnitureCategory,
        vendorId = "vendor1",
        isActive = true,
        price = 499.99,
        description = "Large conference table with cable management and power outlets.",
        stock = 2
    ),
    Product(
        productId = "13",
        name = "Bookcase",
        category = officeFurnitureCategory,
        vendorId = "vendor1",
        isActive = true,
        price = 150.00,
        description = "Tall bookcase with adjustable shelves and modern design.",
        stock = 5
    ),

    // Living Room Furniture
    Product(
        productId = "4",
        name = "Leather Sofa",
        category = livingRoomFurnitureCategory,
        vendorId = "vendor4",
        isActive = true,
        price = 899.99,
        description = "Luxurious leather sofa with reclining seats and cup holders.",
        stock = 2
    ),
    Product(
        productId = "9",
        name = "Coffee Table",
        category = livingRoomFurnitureCategory,
        vendorId = "vendor4",
        isActive = true,
        price = 129.99,
        description = "Modern coffee table with glass top and wooden legs.",
        stock = 9
    ),
    Product(
        productId = "14",
        name = "TV Stand",
        category = livingRoomFurnitureCategory,
        vendorId = "vendor4",
        isActive = true,
        price = 229.99,
        description = "Stylish TV stand with storage compartments and cable management.",
        stock = 4
    ),
    Product(
        productId = "15",
        name = "Armchair",
        category = livingRoomFurnitureCategory,
        vendorId = "vendor4",
        isActive = true,
        price = 199.99,
        description = "Comfortable armchair with soft fabric and wooden legs.",
        stock = 6
    ),
    Product(
        productId = "16",
        name = "Bookshelf",
        category = livingRoomFurnitureCategory,
        vendorId = "vendor4",
        isActive = true,
        price = 89.99,
        description = "Stylish wooden bookshelf with 5 adjustable shelves.",
        stock = 15
    ),

    // Dining Furniture
    Product(
        productId = "5",
        name = "Dining Table Set",
        category = diningFurnitureCategory,
        vendorId = "vendor5",
        isActive = true,
        price = 599.99,
        description = "Modern dining table set with 6 cushioned chairs.",
        stock = 8
    ),
    Product(
        productId = "10",
        name = "Bar Stool Set",
        category = diningFurnitureCategory,
        vendorId = "vendor5",
        isActive = true,
        price = 199.99,
        description = "Set of 2 bar stools with cushioned seats and adjustable height.",
        stock = 6
    ),
    Product(
        productId = "17",
        name = "Dining Chair",
        category = diningFurnitureCategory,
        vendorId = "vendor5",
        isActive = true,
        price = 99.99,
        description = "Elegant dining chair with padded seat and backrest.",
        stock = 20
    ),
    Product(
        productId = "18",
        name = "Buffet Table",
        category = diningFurnitureCategory,
        vendorId = "vendor5",
        isActive = true,
        price = 499.99,
        description = "Spacious buffet table with storage cabinets and wine rack.",
        stock = 3
    ),
    Product(
        productId = "19",
        name = "Dining Bench",
        category = diningFurnitureCategory,
        vendorId = "vendor5",
        isActive = true,
        price = 149.99,
        description = "Comfortable dining bench with wooden frame and cushioned seat.",
        stock = 12
    ),

    // Bedroom Furniture
    Product(
        productId = "20",
        name = "King Size Bed",
        category = bedroomFurnitureCategory,
        vendorId = "vendor3",
        isActive = true,
        price = 999.99,
        description = "King size bed with upholstered headboard and wooden frame.",
        stock = 4
    ),
    Product(
        productId = "21",
        name = "Nightstand",
        category = bedroomFurnitureCategory,
        vendorId = "vendor3",
        isActive = true,
        price = 129.99,
        description = "Modern nightstand with drawer and open shelf.",
        stock = 10
    ),
    Product(
        productId = "22",
        name = "Dresser",
        category = bedroomFurnitureCategory,
        vendorId = "vendor3",
        isActive = true,
        price = 599.99,
        description = "Spacious dresser with 6 drawers and mirror.",
        stock = 5
    ),
    Product(
        productId = "23",
        name = "Wardrobe",
        category = bedroomFurnitureCategory,
        vendorId = "vendor3",
        isActive = true,
        price = 799.99,
        description = "Large wardrobe with sliding doors and built-in mirror.",
        stock = 3
    ),
    Product(
        productId = "24",
        name = "Bedroom Bench",
        category = bedroomFurnitureCategory,
        vendorId = "vendor3",
        isActive = true,
        price = 249.99,
        description = "Elegant bedroom bench with cushioned seat and storage.",
        stock = 7
    ),

    // Gaming Furniture
    Product(
        productId = "2",
        name = "Gaming Desk",
        category = gamingFurnitureCategory,
        vendorId = "vendor2",
        isActive = true,
        price = 249.99,
        description = "Spacious gaming desk with RGB lighting and cable management.",
        stock = 5
    ),
    Product(
        productId = "7",
        name = "Gaming Chair",
        category = gamingFurnitureCategory,
        vendorId = "vendor2",
        isActive = true,
        price = 179.99,
        description = "Comfortable gaming chair with adjustable armrests and lumbar support.",
        stock = 12
    ),
    Product(
        productId = "25",
        name = "Gaming Monitor Stand",
        category = gamingFurnitureCategory,
        vendorId = "vendor2",
        isActive = true,
        price = 79.99,
        description = "Adjustable monitor stand with RGB lighting and USB ports.",
        stock = 10
    ),
    Product(
        productId = "26",
        name = "Gaming Sofa",
        category = gamingFurnitureCategory,
        vendorId = "vendor2",
        isActive = true,
        price = 499.99,
        description = "Reclining gaming sofa with cup holders and storage compartments.",
        stock = 3
    ),
    Product(
        productId = "27",
        name = "Gaming Storage Cabinet",
        category = gamingFurnitureCategory,
        vendorId = "vendor2",
        isActive = true,
        price = 199.99,
        description = "Gaming Storage Cabinet with 4 shelves and lockable doors.",
        stock = 8
    )
)