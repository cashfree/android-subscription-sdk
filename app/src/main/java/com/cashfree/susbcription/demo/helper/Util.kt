package com.cashfree.susbcription.demo.helper

class Util {

    /**
     * X-Client-Id:144436e71d659a3bb9cd8bac74634441
    X-Client-Secret:TEST365fd19c71734164c8cd891474ee09ad39654736
     */
    companion object {
        val header: MutableMap<String, String> = mutableMapOf<String, String>(
            "X-Client-Id" to "144436e71d659a3bb9cd8bac74634441",
            "X-Client-Secret" to "TEST365fd19c71734164c8cd891474ee09ad39654736"
        )
    }
}