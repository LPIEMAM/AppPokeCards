package lpiemam.com.apppokecards.model

class Trade(
    var id: Int = -1,
    var user1: User,
    var userCard1: UserCard?,
    var user2: User?,
    var userCard2: UserCard?,
    var validated: Boolean? = null
) : Comparable<Trade> {
    override fun compareTo(other: Trade): Int {
        return when {
            other.userCard1!!.pokemonCard.nationalPokedexNumber == null && this.userCard1!!.pokemonCard.nationalPokedexNumber != null -> -1
            this.userCard1!!.pokemonCard.nationalPokedexNumber == null && other.userCard1!!.pokemonCard.nationalPokedexNumber != null -> 1
            other.userCard1!!.pokemonCard.nationalPokedexNumber == null && this.userCard1!!.pokemonCard.nationalPokedexNumber == null -> this.userCard1!!.pokemonCard.supertype?.compareTo(
                other.userCard1!!.pokemonCard.supertype!!
            )!!
            else -> this.userCard1!!.pokemonCard.nationalPokedexNumber!!.compareTo(other.userCard1!!.pokemonCard.nationalPokedexNumber!!)
        }
    }

    override fun toString(): String {
        return "Trade(id=$id, user1=$user1, userCard1=$userCard1, user2=$user2, userCard2=$userCard2)"
    }

}