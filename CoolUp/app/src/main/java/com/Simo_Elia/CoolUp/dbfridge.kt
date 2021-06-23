package com.Simo_Elia.CoolUp


class dbfrigo {
    //  Attributi
    var Id : Long = 0
    var EAN : String
    var Name : String
    var Category : String
    var Description : String
    var Allergens : String
    var Unit : String
    var Recyclable : String
    var Freezable : String
    var Date: String

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
        Date = ""
    }

    //  Costruttore poer parametri
    constructor(Id: Int,EAN : String,Name: String, Category : String,Description : String, Allergens : String,Unit : String,Recyclable : String,Freezable : String,Date : String)
    {
        //this.Id = Id.toLong()
        this.EAN = EAN
        this.Name = Name
        this.Category = Category
        this.Description = Description
        this.Allergens = Allergens
        this.Unit = Unit
        this.Recyclable = Recyclable
        this.Freezable = Freezable
        this.Date = Date
    }

    //  Costruttore per copia, serve per copiare i valori scaricati in download dal server azure.
    constructor(download: dbdownload)
    {
        //this.Id = Id.toLong()
        this.EAN = download.EAN
        this.Name = download.Name
        this.Category = download.Category
        this.Description = download.Description
        this.Allergens = download.Allergens
        this.Unit = download.Unit
        this.Recyclable = download.Recyclable
        this.Freezable = download.Freezable
        this.Date = ""
    }
    //  Variabili static, possono servire per variabili boolean
    companion object
    {
        const val TRUE = "1"
        const val FALSE = "0"
    }
}
