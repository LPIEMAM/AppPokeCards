package lpiemam.com.apppokecards

class UserInfo(var idUser: Int? = null,
               var pseudo: String? = null,
               var passwd: String? = null,
               var eMail: String? = null,
               var facebookId: Long? = null,
               var googleId: String? = null,
               var firstName: String? = null,
               var lastName: String? = null,
               var facebookPhoto: String? = null,
               var googlePhoto: String? = null

) {
    override fun toString(): String {
        return "UserInfo(idUser=$idUser, pseudo=$pseudo, passwd=$passwd, eMail=$eMail, facebookId=$facebookId, googleId=$googleId, firstName=$firstName, lastName=$lastName, facebookPhoto=$facebookPhoto, googlePhoto=$googlePhoto)"
    }
}