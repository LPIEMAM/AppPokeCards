package lpiemam.com.apppokecards;

public class UserInfo {

    private int idUser;
    private String pseudo;
    private String passwd;
    private String eMail;


    public UserInfo(int idUser, String pseudo, String passwd, String eMail) {
        this.idUser = idUser;
        this.pseudo = pseudo;
        this.passwd = passwd;
        this.eMail = eMail;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}