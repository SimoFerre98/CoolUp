package com.Simo_Elia.CoolUp

class dblist {
    //  Variaqbili
    var Id:Int = 0
    var Unit : String
    var Name : String

    //  Costruttore di default
    constructor() {
        Unit = ""
        Name = ""
    }

    //  Costruttorte per parametri
    constructor(Unit : String,Name: String)
    {
        this.Unit = Unit
        this.Name = Name
    }

    //  Selettori
    fun GetId() : Int
    {
        return this.Id
    }
    public fun GetUnit() : String
    {
        return this.Unit
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
    public fun SetUnit(Unit: String)
    {
        this.Unit = Unit
    }
    public fun SetName(Name: String)
    {
        this.Name = Name
    }

}