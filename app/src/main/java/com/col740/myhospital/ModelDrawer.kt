package com.col740.myhospital

data class DrawerItem(var title:String)

object Supplier_Drawer{
    val draweritems = listOf<DrawerItem>(
        DrawerItem("Home"),
        DrawerItem("Search by Category"),
        DrawerItem("Your Bookings"),
        DrawerItem("Your Account"),
        DrawerItem("Settings"),
        DrawerItem("Customer Service"),
        DrawerItem("Logout"))
}