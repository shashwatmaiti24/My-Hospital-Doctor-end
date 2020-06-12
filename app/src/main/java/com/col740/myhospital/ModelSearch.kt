package com.col740.myhospital

data class SearchItem(var title:String)

object Supplier_Search{
    val searchitems = listOf<SearchItem>(
        SearchItem("Dr. Rohit Kumar Singh"),
        SearchItem("Dr. Anshshiv Garg"),
        SearchItem("Dr. Ayush Shrivastava"))
}