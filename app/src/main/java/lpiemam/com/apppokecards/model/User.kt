package lpiemam.com.apppokecards.model

class User(val firstName : String, val lastName : String, val nickName : String, val email : String, val url : String){
    lateinit var userPokemonList : ArrayList<Pokemon>
}