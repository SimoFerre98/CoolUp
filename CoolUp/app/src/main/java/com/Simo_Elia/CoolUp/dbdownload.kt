package com.Simo_Elia.CoolUp


class dbdownload {
    //  Attributi
    var EAN : String
    var Name : String
    var Category : String
    var Description : String
    var Allergens : String
    var Unit : String
    var Recyclable : String
    var Freezable : String

    //  Costruttore di default
    constructor()
    {
        EAN = ""
        Name = ""
        Category = ""
        Description = ""
        Allergens = ""
        Unit = ""
        Recyclable = ""
        Freezable = FALSE   //  Variabile impostata di default a FALSE, cioè di base i prodotti non sono nel freezer
    }

    //  Costruttore poer parametri
    constructor(EAN : String,Name: String, Category : String,Description : String, Allergens : String,Unit : String,Recyclable : String,Freezable : String)
    {
        this.EAN = EAN
        this.Name = Name
        this.Category = Category
        this.Description = Description
        this.Allergens = Allergens
        this.Unit = Unit
        this.Recyclable = Recyclable
        this.Freezable = Freezable
    }


    //  Variabili static, possono servire per variabili boolean
    companion object
    {
        const val TRUE = "1"
        const val FALSE = "0"
    }
}