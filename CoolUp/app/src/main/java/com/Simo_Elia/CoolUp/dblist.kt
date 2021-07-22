package com.Simo_Elia.CoolUp

class dblist {
    //  Variaqbili
    var Id:Int = 0
    var EAN : String
    var Name : String

    //  Costruttore di default
    constructor() {
        EAN = ""
        Name = ""
    }

    //  Costruttorte per parametri
    constructor(EAN : String,Name: String)
    {
        this.EAN = EAN
        this.Name = Name
    }

    //  Selettori
    fun GetId() : Int
    {
        return this.Id
    }
    public fun GetEAN() : String
    {
        return this.EAN
    }
    public fun GetName() : String
    {
        return this.Name
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

}