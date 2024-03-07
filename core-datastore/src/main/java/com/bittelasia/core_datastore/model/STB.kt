package com.bittelasia.core_datastore.model

object STB {
    var HOST = ""
        get() = field.ifEmpty { "http://127.0.0.1" }
    var PORT = ""
    var ROOM = ""
    var MAC_ADDRESS = ""
    var API_KEY = ""
    var USERNAME = ""
    var PASSWORD = ""
    var DEV_ID = ""
    var FIRST_RUN = ""
        get() = field.ifEmpty { "0" }
    var END_DATE = ""
    var REMAINING_DAYS = ""
}
