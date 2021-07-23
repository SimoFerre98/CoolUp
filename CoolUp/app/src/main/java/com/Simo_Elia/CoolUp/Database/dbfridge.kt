package com.Simo_Elia.CoolUp.Database


class dbfridge {
    //  Attributi
    var Id : Int = 0
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
    constructor(EAN : String,Name: String, Category : String,Description : String, Allergens : String,Unit : String,Recyclable : String,Freezable : String,Date : String)
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


    //  Selettori
    fun GetId() : Int
    {
        return this.Id
    }

    public fun GetEAN() : String = this.EAN

    public fun GetName() : String
    {
        return this.Name
    }
    public fun GetCategory() : String
    {
        return this.Category
    }
    public fun GetDescription() : String
    {
        return this.Description
    }
    public fun GetAllergens() : String
    {
        return this.Allergens
    }
    public fun GetUnit() : String
    {
        return this.Unit
    }
    public fun GetRecyclable() : String
    {
        return this.Recyclable
    }
    public fun GetFreezable() : String
    {
        return this.Freezable
    }
    public fun GetDate() : String
    {
        return this.Date
    }


    //  Modificatori
    fun SetId(Id:Int)
    {
        this.Id = Id
    }
    public fun SetEAN(EAN: String)
    {
        this.EAN = EAN
    }
    public fun SetName(Name: String)
    {
        this.Name = Name
    }
    public fun SetCategory(Category: String)
    {
        this.Category = Category
    }
    public fun SetDescription(Description: String)
    {
        this.Description = Description
    }
    public fun SetAllergens(Allergens: String)
    {
        this.Allergens = Allergens
    }
    public fun SetUnit(Unit: String)
    {
        this.Unit = Unit
    }
    public fun SetRecyclable(Recyclable: String)
    {
        this.Recyclable = Recyclable
    }
    public fun SetFreezable(Freezable: String)
    {
        this.Freezable = Freezable
    }
    public fun SetDate(Date: String)
    {
        this.Date = Date
    }

    //  Variabili static, possono servire per variabili boolean
    companion object
    {
        const val TRUE = "1"
        const val FALSE = "0"
    }
}
