package com.Simo_Elia.CoolUp

class dblist {
    //  Variaqbili
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
    public fun GetEAN() : String
    {
        return this.EAN
    }
    public fun GetName() : String
    {
        return this.Name
    }

    //  Modificatori
    public fun SetEAN(EAN: String)
    {
        this.EAN = EAN
    }
    public fun SetName(Name: String)
    {
        this.Name = Name
    }

}