package com.example.fruithub.screens.productdetails

data class ProductDetailsUiState(
    val name: String = "Quinoa Fruit Salad",
    val ingredients: String = "RedQuinoa,Lime,Honey,Blueberries,Strawberries\nMango, Fresh mint.",
    val description: String = "If you are looking for a new fruit salad to eat today, \nquinoa is the perfect brunch for you. make ",
    val unitPrice: Int = 2000,
    val quantity: Int = 1,
    val isFavorite: Boolean = false,
    val startAnimation: Boolean = false
) {
    val totalPrice: Int get() = unitPrice * quantity
}
