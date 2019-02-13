package lpiemam.com.apppokecards.model

import lpiemam.com.apppokecards.fragment.AllCardsFragment
import lpiemam.com.apppokecards.fragment.CollectionFragment
import lpiemam.com.apppokecards.fragment.QuizzFragment
import java.util.*

object Manager {

    lateinit var allCardsList: ArrayList<Card>
    lateinit var allPokemonList : ArrayList<Pokemon>
    lateinit var userSiam : User
    lateinit var cardsPacksList : ArrayList<CardsPack>
    var quizzFragment = QuizzFragment.newInstance()
    var collectionFragment = CollectionFragment.newInstance()
    var allCardsFragment = AllCardsFragment.newInstance()


    fun initializeData() {
        userSiam = User("Annabelle", "Braye", "Siam", "annabelle.braye@gmail.com", "")
        userSiam.coins = 100000
        userSiam.dusts = 100000
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

        val pikachuCard = Card(
            pikachu,
            "https://cdn1.pokemoncarte.com/1589/carte-pokemon-ex-pikachu-ex-130-pv-xy-84.jpg",
            "Pikachu est surnommé Souris électrique...",
            "génération 1"
        )
        val pikachuCard2 = Card(
            pikachu,
            "https://www.pokepedia.fr/images/thumb/9/9f/Carte_Set_de_Base_58.png/250px-Carte_Set_de_Base_58.png",
            "Pikachu est obèse =/",
            "génération 1"
        )
        val mewCard = Card(
            mew,
            "https://www.pokepedia.fr/images/thumb/0/06/Carte_Promo_Mew_Antique.png/250px-Carte_Promo_Mew_Antique.png",
            "Mais qu'il est mignon ce pokemon !",
            "génération 1"
        )
        val florizarreCard = Card(
            florizarre,
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/PL3/PL3_FR_13.png",
            "Raaaaaaah !",
            "génération 2"
        )
        val tortankCard = Card(
            tortank,
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/DP3/DP3_FR_2.png",
            "Taaaaaank !",
            "génération 3"
        )
        val dracaufeuCard = Card(
            dracaufeu,
            "https://cdn1.pokemoncarte.com/1690/carte-pokemon-ex-carte-pokemon-ex-full-art-dracaufeu-ex-pv-180-xy121.jpg",
            "*Crache des flammes*",
            "génération 2"
        )
        val hericendreCard = Card(
            hericendre,
            "https://www.pokepedia.fr/images/thumb/0/04/Carte_L%27Appel_des_Légendes_55.png/250px-Carte_L%27Appel_des_Légendes_55.png",
            "Lui aussi il est chou...",
            "génération 5"
        )
        val germignonCard = Card(
            germignon,
            "https://www.pokepedia.fr/images/3/37/Carte_HeartGold_SoulSilver_59.png",
            "La peluche est choupi !",
            "génération 4"
        )
        val kaiminusCard = Card(
            kaiminus,
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/XY4/XY4_FR_15.png",
            "IV 100 <3",
            "génération 5"
        )
        val mentaliCard = Card(
            mentali,
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/DP5/DP5_FR_18.png",
            "Evolution d'évoli",
            "génération 5"
        )
        val noctaliCard = Card(
            noctali,
            "https://images-na.ssl-images-amazon.com/images/I/A1jfIm67P-L._SY450_.jpg",
            "Evolution d'évoli",
            "génération 6"
        )
        val goeliseCard = Card(
            goelise,
            "https://www.coleka.com/media/item/20160416/pokemon-xy-ciel-rugissant-goelise-18-108.jpg",
            "Je veux mon shiny !",
            "génération 6"
        )

        val arckoCard = Card(
            arcko,
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/XYP/XYP_FR_XY36.png",
            "Il s'y croit un peu là, non ?",
            "génération 7"
        )
        val poussifeuCard = Card(
            poussifeu,
            "https://www.pokepedia.fr/images/thumb/6/63/Carte_Platine_99.png/250px-Carte_Platine_99.png",
            "Encore une mignonnerie !",
            "génération 7"
        )
        val gobouCard = Card(
            gobou,
            "https://www.pokepedia.fr/images/3/37/Carte_Promo_XY_XY38.png",
            "Blurp Blurp...",
            "génération 7"
        )
        val tenefixCard = Card(
            tenefix,
            "https://i.ebayimg.com/images/g/zEgAAOSwgQ9V1d9b/s-l300.jpg",
            "Quelque chose en nous de Ténéfix...",
            "génération 8"
        )
        val tortipoussCard = Card(
            tortipouss,
            "https://www.pokepedia.fr/images/thumb/e/e1/Carte_Promo_DP_DP01.png/250px-Carte_Promo_DP_DP01.png",
            "Choupinou",
            "génération 8"
        )
        val ouisticramCard = Card(
            ouisticram,
            "https://www.pokepedia.fr/images/thumb/2/21/Carte_Platine_Vainqueurs_Suprêmes_97.png/250px-Carte_Platine_Vainqueurs_Suprêmes_97.png",
            "Encore un pokémon inutile...",
            "génération 1"
        )
        val tiploufCard = Card(
            tiplouf,
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/DP1/DP1_FR_93.png",
            "PLOUF !",
            "génération 2"
        )
        val rozboutonCard = Card(
            rozbouton,
            "http://www.mypokecard.com/my/galery/0pcE8RMO9tcy.jpg",
            "Evolué avec la pierre Sinnoh !",
            "génération 3"
        )
        val corbossCard = Card(
            corboss,
            "https://www.pokepedia.fr/images/thumb/d/de/Carte_Noir_%26_Blanc_Dragons_Exaltés_73.png/250px-Carte_Noir_%26_Blanc_Dragons_Exaltés_73.png",
            "Lui je l'ai en shiny =D",
            "génération 4"
        )


        //Liste de TOUS les pokémons
        allPokemonList = arrayListOf(arcko, rozbouton, tenefix, tiplouf, tortipouss, tortank, ouisticram, pikachu, poussifeu, mentali, mew, kaiminus, hericendre, germignon, gobou, goelise, florizarre, dracaufeu, corboss, noctali)
        allPokemonList = ArrayList(allPokemonList.sortedWith(compareBy{it.pokedexNumber}))

        //Liste de TOUTES les cartes de TOUS les pokémons
        allCardsList = arrayListOf(arckoCard, rozboutonCard, tenefixCard, tiploufCard, tortipoussCard, tortankCard, ouisticramCard, pikachuCard, pikachuCard2, poussifeuCard, mentaliCard, mewCard, kaiminusCard, hericendreCard, germignonCard, gobouCard, goeliseCard, florizarreCard, dracaufeuCard, corbossCard, noctaliCard)
        allCardsList = ArrayList(allCardsList.sortedWith(compareBy{it.pokemon.pokedexNumber}))

        //Liste des pokémons dont l'utilisateur possède une carte
        userSiam.userCardList = arrayListOf(UserCard(pikachuCard), UserCard(mewCard), UserCard(hericendreCard), UserCard(goeliseCard), UserCard(corbossCard), UserCard(tortankCard), UserCard(dracaufeuCard), UserCard(mentaliCard), UserCard(noctaliCard))
        userSiam.userCardList = ArrayList(userSiam.userCardList.sortedWith(compareBy{it.card.pokemon.pokedexNumber}))

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
        for(card in allCardsList) {
            var firewall = true
            for(userCard in userSiam.userCardList) {
                if(card.toString().equals(userCard.toString())) {
                    firewall = false
                }
                /*while (card.toString() == userCard.toString()) {
                    firewall = false
                }*/
            }
            if(firewa ll)
            {
                allCardsUserNeeds.add(card)
            }
        }
    }*/

    fun generateQuestions(): PokemonQuestions {
        val question1 = Question(
            "De quelle couleur est pikachu ?",
            Arrays.asList("Bleue", "Orange", "Jaune", "Marron"),
            2
        )

        val question2 = Question(
            "Comment s'appelle le pokémon faisant parti du trio de la team Rocket ?",
            Arrays.asList("Rattata", "Smogo", "Abo", "Miaouss"),
            3
        )

        val question3 = Question(
            "De quelle couleur est le shiny de psykokwak ?",
            Arrays.asList("Orange", "Bleue", "Jaune", "Rose"),
            1
        )

        val question4 = Question(
            "Comment s'appelle l'évolution de rhinoféros ?",
            Arrays.asList("Rhinocorne", "Rhinite", "Rhinastoc", "Rhinopharyngite"),
            2
        )

        val question5 = Question(
            "De quelle génération est issu Tiplouf ?",
            Arrays.asList("Il n'existe pas", "2ème génération", "4ème génération", "3ème génération"),
            2
        )

        val question6 = Question(
            "De quel autre type est Grodrive ? Vol - ?",
            Arrays.asList("Ténèbres", "Normal", "Spectre", "Fée"),
            2
        )

        val question7 = Question(
            "Quel pokémon ressemble à un coeur ?",
            Arrays.asList("Lovdisc", "Baudrive", "Roserade", "Milobellus"),
            0
        )

        val question8 = Question(
            "Quelle pierre faut-il pour faire évoluer cornèbre ?",
            Arrays.asList("Pierre Sinnoh", "Pierre Soleil", "Améliorator", "Aucune des trois"),
            0
        )

        val question9 = Question(
            "De quelle couleur est Lainergie, l'évolution de wattouat ?",
            Arrays.asList("Blanche", "Rose", "Rouge", "Jaune"),
            1
        )

        return PokemonQuestions(
            Arrays.asList(
                question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9
            )
        )
    }

    fun addCardForUser(card : Card) {
        if(card.isCardInArray(userSiam.userCardList)) {
            var userCard = card.getInstanceOfUserCard(userSiam.userCardList)
            userCard.numberOfCard++
        } else {
            userSiam.userCardList.add(UserCard(card))
        }
        userSiam.userCardList = ArrayList(userSiam.userCardList.sortedWith(compareBy{it.card.pokemon.pokedexNumber}))
    }

    fun removeCardForUser(userCard: UserCard) {
        if(userCard.numberOfCard > 1) {
            userCard.numberOfCard--
        } else {
            Manager.userSiam.userCardList.remove(userCard)
        }
        userSiam.userCardList = ArrayList(userSiam.userCardList.sortedWith(compareBy{it.card.pokemon.pokedexNumber}))
    }


}