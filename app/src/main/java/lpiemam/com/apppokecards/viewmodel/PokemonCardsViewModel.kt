package lpiemam.com.apppokecards.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lpiemam.com.apppokecards.model.*
import lpiemam.com.apppokecards.retrofit.PokemonCardsRepository

class PokemonCardsViewModel : ViewModel() {


    var pokemonCardsLiveData = MutableLiveData<ArrayList<PokemonCard>>()

    fun fetchPokemonCards(name: String) {

        PokemonCardsRepository.fetchPokemonCardsForName(name).observeForever {
            if (it.isEmpty()) {
//                val data = DataBaseFactory.mycardsDatabase.rickCardDao().fetchAll()
//                data.observeForever(object : Observer<List<RickCard>> {
//                    override fun onChanged(t: List<RickCard>?) {
//                        rickCardsLiveData.postValue(t)
//                        data.removeObserver(this)
//                    }
//                })
            } else {
                pokemonCardsLiveData.postValue(it)
//                Thread(Runnable {
//                    DataBaseFactory.mycardsDatabase.rickCardDao().insertAll(rickCardsLiveData.value)
//                }).start()
            }
        }

        // gestion de l'erreur
        /*CardsRepository.error.observeForever {
            if (it) {
                val data = DataBaseFactory.mycardsDatabase.rickCardDao().fetchAll()
                data.observeForever(object : Observer<List<RickCard>> {
                    override fun onChanged(t: List<RickCard>?) {
                        rickCardsLiveData.postValue(t)
                        data.removeObserver(this)
                    }
                })
            }
        }*/

        // Mise en place d'une extension
        /*CardsRepository.error.observeOnce(Observer {
            val data = DataBaseFactory.mycardsDatabase.rickCardDao().fetchAll()
            data.observeOnce(Observer {
                rickCardsLiveData.postValue(it)
            })
        })*/
    }



    lateinit var allCardsList: ArrayList<PokemonCard>
    lateinit var allPokemonList: ArrayList<Pokemon>
    lateinit var cardsPacksList: ArrayList<CardsPack>
    lateinit var userCardList: ArrayList<UserCard>


    fun initializeData() {
        User.setUpUser("Annabelle", "Braye", "Siam", "annabelle.braye@gmail.com", 100000, 100000)

        var pikachu = Pokemon("Pikachu", 25, "Electrik")
        var mew = Pokemon("Mew", 150, "Psy")
        var tortank = Pokemon("Tortank", 9, "Eau")
        var dracaufeu = Pokemon("Dracaufeu", 6, "Feu")
        var florizarre = Pokemon("Florizarre", 3, "Plante")
        var hericendre = Pokemon("Héricendre", 155, "Feu")
        var germignon = Pokemon("Germignon", 152, "Plante")
        var kaiminus = Pokemon("Kaiminus", 158, "Eau")
        var mentali = Pokemon("Mentali", 196, "Psy")
        var noctali = Pokemon("Noctali", 197, "Ténèbres")
        var arcko = Pokemon("Arcko", 252, "Plante")
        var poussifeu = Pokemon("Poussifeu", 255, "Feu")
        var gobou = Pokemon("Gobou", 258, "Eau")
        var tenefix = Pokemon("Ténéfix", 302, "Ténèbres")
        var goelise = Pokemon("Goélise", 278, "Eau, Vol")
        var ouisticram = Pokemon("Ouisticram", 390, "Feu")
        var tortipouss = Pokemon("Tortipouss", 387, "Plante")
        var tiplouf = Pokemon("Tiplouf", 393, "Eau")
        var corboss = Pokemon("Corboss", 430, "Ténèbres, Vol")
        var rozbouton = Pokemon("Rozbouton", 406, "Plante, Poison")

//        val pikachuCard = PokemonCard(
//            pikachu,
//            "https://cdn1.pokemoncarte.com/1589/carte-pokemon-ex-pikachu-ex-130-pv-xy-84.jpg",
//            "Pikachu est surnommé Souris électrique...",
//            "génération 1"
//        )
//        val pikachuCard2 = PokemonCard(
//            pikachu,
//            "https://www.pokepedia.fr/images/thumb/9/9f/Carte_Set_de_Base_58.png/250px-Carte_Set_de_Base_58.png",
//            "Pikachu est obèse =/",
//            "génération 1"
//        )
//        val mewCard = PokemonCard(
//            mew,
//            "https://www.pokepedia.fr/images/thumb/0/06/Carte_Promo_Mew_Antique.png/250px-Carte_Promo_Mew_Antique.png",
//            "Mais qu'il est mignon ce pokemon !",
//            "génération 1"
//        )
//        val florizarreCard = PokemonCard(
//            florizarre,
//            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/PL3/PL3_FR_13.png",
//            "Raaaaaaah !",
//            "génération 2"
//        )
//        val tortankCard = PokemonCard(
//            tortank,
//            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/DP3/DP3_FR_2.png",
//            "Taaaaaank !",
//            "génération 3"
//        )
//        val dracaufeuCard = PokemonCard(
//            dracaufeu,
//            "https://cdn1.pokemoncarte.com/1690/carte-pokemon-ex-carte-pokemon-ex-full-art-dracaufeu-ex-pv-180-xy121.jpg",
//            "*Crache des flammes*",
//            "génération 2"
//        )
//        val hericendreCard = PokemonCard(
//            hericendre,
//            "https://www.pokepedia.fr/images/thumb/0/04/Carte_L%27Appel_des_Légendes_55.png/250px-Carte_L%27Appel_des_Légendes_55.png",
//            "Lui aussi il est chou...",
//            "génération 5"
//        )
//        val germignonCard = PokemonCard(
//            germignon,
//            "https://www.pokepedia.fr/images/3/37/Carte_HeartGold_SoulSilver_59.png",
//            "La peluche est choupi !",
//            "génération 4"
//        )
//        val kaiminusCard = PokemonCard(
//            kaiminus,
//            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/XY4/XY4_FR_15.png",
//            "IV 100 <3",
//            "génération 5"
//        )
//        val mentaliCard = PokemonCard(
//            mentali,
//            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/DP5/DP5_FR_18.png",
//            "Evolution d'évoli",
//            "génération 5"
//        )
//        val noctaliCard = PokemonCard(
//            noctali,
//            "https://images-na.ssl-images-amazon.com/images/I/A1jfIm67P-L._SY450_.jpg",
//            "Evolution d'évoli",
//            "génération 6"
//        )
//        val goeliseCard = PokemonCard(
//            goelise,
//            "https://www.coleka.com/media/item/20160416/pokemon-xy-ciel-rugissant-goelise-18-108.jpg",
//            "Je veux mon shiny !",
//            "génération 6"
//        )
//
//        val arckoCard = PokemonCard(
//            arcko,
//            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/XYP/XYP_FR_XY36.png",
//            "Il s'y croit un peu là, non ?",
//            "génération 7"
//        )
//        val poussifeuCard = PokemonCard(
//            poussifeu,
//            "https://www.pokepedia.fr/images/thumb/6/63/Carte_Platine_99.png/250px-Carte_Platine_99.png",
//            "Encore une mignonnerie !",
//            "génération 7"
//        )
//        val gobouCard = PokemonCard(
//            gobou,
//            "https://www.pokepedia.fr/images/3/37/Carte_Promo_XY_XY38.png",
//            "Blurp Blurp...",
//            "génération 7"
//        )
//        val tenefixCard = PokemonCard(
//            tenefix,
//            "https://i.ebayimg.com/images/g/zEgAAOSwgQ9V1d9b/s-l300.jpg",
//            "Quelque chose en nous de Ténéfix...",
//            "génération 8"
//        )
//        val tortipoussCard = PokemonCard(
//            tortipouss,
//            "https://www.pokepedia.fr/images/thumb/e/e1/Carte_Promo_DP_DP01.png/250px-Carte_Promo_DP_DP01.png",
//            "Choupinou",
//            "génération 8"
//        )
//        val ouisticramCard = PokemonCard(
//            ouisticram,
//            "https://www.pokepedia.fr/images/thumb/2/21/Carte_Platine_Vainqueurs_Suprêmes_97.png/250px-Carte_Platine_Vainqueurs_Suprêmes_97.png",
//            "Encore un pokémon inutile...",
//            "génération 1"
//        )
//        val tiploufCard = PokemonCard(
//            tiplouf,
//            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/DP1/DP1_FR_93.png",
//            "PLOUF !",
//            "génération 2"
//        )
//        val rozboutonCard = PokemonCard(
//            rozbouton,
//            "http://www.mypokecard.com/my/galery/0pcE8RMO9tcy.jpg",
//            "Evolué avec la pierre Sinnoh !",
//            "génération 3"
//        )
//        val corbossCard = PokemonCard(
//            corboss,
//            "https://www.pokepedia.fr/images/thumb/d/de/Carte_Noir_%26_Blanc_Dragons_Exaltés_73.png/250px-Carte_Noir_%26_Blanc_Dragons_Exaltés_73.png",
//            "Lui je l'ai en shiny =D",
//            "génération 4"
//        )


        //Liste de TOUS les pokémons
        allPokemonList = arrayListOf(
            arcko,
            rozbouton,
            tenefix,
            tiplouf,
            tortipouss,
            tortank,
            ouisticram,
            pikachu,
            poussifeu,
            mentali,
            mew,
            kaiminus,
            hericendre,
            germignon,
            gobou,
            goelise,
            florizarre,
            dracaufeu,
            corboss,
            noctali
        )
        allPokemonList = ArrayList(allPokemonList.sortedWith(compareBy { it.pokedexNumber }))

        //Liste de TOUTES les cartes de TOUS les pokémons
//        allCardsList = arrayListOf(
//            arckoCard,
//            rozboutonCard,
//            tenefixCard,
//            tiploufCard,
//            tortipoussCard,
//            tortankCard,
//            ouisticramCard,
//            pikachuCard,
//            pikachuCard2,
//            poussifeuCard,
//            mentaliCard,
//            mewCard,
//            kaiminusCard,
//            hericendreCard,
//            germignonCard,
//            gobouCard,
//            goeliseCard,
//            florizarreCard,
//            dracaufeuCard,
//            corbossCard,
//            noctaliCard
//        )
//        allCardsList = ArrayList(allCardsList.sortedWith(compareBy { it.pokemon.pokedexNumber }))
//
//        //Liste des pokémons dont l'utilisateur possède une carte
//        userCardList = arrayListOf(
//            UserCard(pikachuCard),
//            UserCard(mewCard),
//            UserCard(hericendreCard),
//            UserCard(goeliseCard),
//            UserCard(corbossCard),
//            UserCard(tortankCard),
//            UserCard(dracaufeuCard),
//            UserCard(mentaliCard),
//            UserCard(noctaliCard)
//        )
//        userCardList = ArrayList(userCardList.sortedWith(compareBy { it.pokemonCard.pokemon.pokedexNumber }))
        userCardList = ArrayList()

        //Liste des cartes pokémons que l'utilisateur n'a pas
        // allCardsUserNeeds = ArrayList()

        //setAllCardsUserNeeds()

        cardsPacksList = ArrayList()

        var petitPack = CardsPack("Petit")
        var moyenPack = CardsPack("Moyen")
        var grandPack = CardsPack("Grand")

        cardsPacksList.add(petitPack)
        cardsPacksList.add(moyenPack)
        cardsPacksList.add(grandPack)
    }

    /* fun setAllCardsUserNeeds() {
         allCardsUserNeeds.clear()
         for(pokemonCard in allCardsList) {
             var firewall = true
             for(userCard in userSiam.userCardList) {
                 if(pokemonCard.toString().equals(userCard.toString())) {
                     firewall = false
                 }
                 /*while (pokemonCard.toString() == userCard.toString()) {
                     firewall = false
                 }*/
             }
             if(firewa ll)
             {
                 allCardsUserNeeds.add(pokemonCard)
             }
         }
     }*/

    fun buyAPack(pack: CardsPack) {
        for(card in pack.listPokemonCards) {

            if(card.isCardInArray(userCardList)) {
                var userCard = card.getInstanceOfUserCard(userCardList)
                userCard.numberOfCard++
            } else {
                userCardList.add(UserCard(card))
            }
        }
//        userCardList = ArrayList(userCardList.sortedWith(compareBy{it.pokemonCard.pokemon.pokedexNumber}))
        User.coins -= pack.costPack
    }


    fun addUserCard(pokemonCard: PokemonCard) {
        if (pokemonCard.isCardInArray(userCardList)) {
            var userCard = pokemonCard.getInstanceOfUserCard(userCardList)
            userCard.numberOfCard++
        } else {
            userCardList.add(UserCard(pokemonCard))
        }
//        userCardList = ArrayList(userCardList.sortedWith(compareBy { it.pokemonCard.pokemon.pokedexNumber }))
    }

    fun removeUserCard(userCard: UserCard) {
        if (userCard.numberOfCard > 1) {
            userCard.numberOfCard--
        } else {
            userCardList.remove(userCard)
        }
//        userCardList = ArrayList(userCardList.sortedWith(compareBy { it.pokemonCard.pokemon.pokedexNumber }))
    }


}