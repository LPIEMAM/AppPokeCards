package lpiemam.com.apppokecards

class UserInfo(var idUser: Int, var pseudo: String?, var passwd: String?, private var eMail: String?) {

    fun geteMail(): String? {
        return eMail
    }

    fun seteMail(eMail: String) {
        this.eMail = eMail
    }
}