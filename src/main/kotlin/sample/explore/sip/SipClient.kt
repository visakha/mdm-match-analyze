package sample.explore.sip

import com.siperian.sif.client.HttpSiperianClient
import com.siperian.sif.client.SiperianClient
import com.siperian.sif.message.Field
import com.siperian.sif.message.Record
import com.siperian.sif.message.RecordKey
import com.siperian.sif.message.mrm.*
import java.io.File


object SipClient {
    var propFilePath = "src\\main\\resources\\"
    val sipClient =
        SiperianClient.newSiperianClient(File(propFilePath + "siperian-client.properties")) as HttpSiperianClient

    fun pingSip() {
        println("Entering sip ping ")
        val request = AuthenticateRequest()
        val response = sipClient.process(request) as AuthenticateResponse
        println("!!!!!!!!!!!!!!!!! - $response")
    }

}

fun SearchMatchRequest.addStringField(name: String, value: String ?){
    if (value != null ) {
        val field = Field(name)
        field.setValue(value!!)
        this.addMatchColumnField(field)
    }
}


inline fun Record.ifEmptyThenString(fieldName: String, defaultValue: String?) = if (this.getField(fieldName).stringValue != null) this.getField(fieldName).stringValue else defaultValue
inline fun Field.ifEmptyThenString(defaultValue: String): String = if (this != null) this.stringValue else defaultValue
