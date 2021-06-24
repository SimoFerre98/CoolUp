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
}