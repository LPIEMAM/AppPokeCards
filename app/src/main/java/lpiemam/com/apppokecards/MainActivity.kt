package lpiemam.com.apppokecards

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.Pokemon
import lpiemam.com.apppokecards.model.User

class MainActivity() : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var allCardsList: List<Card>
    lateinit var allPokemonList : List<Pokemon>
    lateinit var userSiam : User
    lateinit var collectionFragment : Fragment
    lateinit var allCardsFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        collectionFragment = CollectionFragment()
        allCardsFragment = AllCardsFragment()

        initializeData()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainActivityContainer, CollectionFragment.newInstance(), "collectionFragment")
                .commit()
        }


        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.menuItemCollection -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainActivityContainer, CollectionFragment.newInstance(), "collectionFragment")
                    .commit()
            }
            R.id.menuItemShop -> {
//                supportFragmentManager
//                .beginTransaction()
//                .add(R.id.mainActivityContainer, ShopFragment.newInstance(), "collectionFragment")
//                .commit()
            }
            R.id.menuItemQuizz -> {
//                supportFragmentManager
//                .beginTransaction()
//                .add(R.id.mainActivityContainer, QuizzFragment.newInstance(), "collectionFragment")
//                .commit()
            }
            R.id.menuItemAchievements -> {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.mainActivityContainer, UserCardDetailFragment.newInstance(), "collectionFragment")
                    .commit()
            }
            R.id.menuItemAllCards -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainActivityContainer, AllCardsFragment.newInstance(), "allCardsFragment")
                    .commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun initializeData() {
        userSiam = User("Annabelle", "Braye", "Siam", "annabelle.braye@gmail.com", "")
        val pikachuCard = Card(
                "Pikachu",
                "https://cdn1.pokemoncarte.com/1589/carte-pokemon-ex-pikachu-ex-130-pv-xy-84.jpg",
                "Pikachu est surnommé Souris électrique...",
                "génération 1"
        )
        val mewCard = Card(
                "Mew",
                "https://www.pokepedia.fr/images/thumb/0/06/Carte_Promo_Mew_Antique.png/250px-Carte_Promo_Mew_Antique.png",
                "Mais qu'il est mignon ce pokemon !",
                "génération 1"
        )
        val florizarreCard = Card(
            "Florizarre",
                "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/PL3/PL3_FR_13.png",
                "Raaaaaaah !",
                "génération 2"
        )
        val tortankCard = Card(
            "Tortank",
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/DP3/DP3_FR_2.png",
            "Taaaaaank !",
            "génération 3"
        )
        val dracaufeCard = Card(
            "Dracaufeu",
            "https://cdn1.pokemoncarte.com/1690/carte-pokemon-ex-carte-pokemon-ex-full-art-dracaufeu-ex-pv-180-xy121.jpg",
            "*Crache des flammes*",
                "génération 2"
        )
        val hericendreCard = Card(
            "Héricendre",
            "https://www.pokepedia.fr/images/thumb/0/04/Carte_L%27Appel_des_Légendes_55.png/250px-Carte_L%27Appel_des_Légendes_55.png",
            "Lui aussi il est chou...",
                "génération 5"
        )
        val germignonCard = Card(
            "Germignon",
            "https://www.pokepedia.fr/images/3/37/Carte_HeartGold_SoulSilver_59.png",
            "La peluche est choupi !",
                "génération 4"
        )
        val kaiminusCard = Card(
            "Kaiminus",
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/XY4/XY4_FR_15.png",
            "IV 100 <3",
                "génération 5"
        )
        val mentaliCard = Card(
                "Mentali",
                "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/DP5/DP5_FR_18.png",
                "Evolution d'évoli",
                "génération 5"
        )
        val noctaliCard = Card(
                "Noctali",
                "https://images-na.ssl-images-amazon.com/images/I/A1jfIm67P-L._SY450_.jpg",
                "Evolution d'évoli",
                "génération 6"
        )
        val goeliseCard = Card(
            "Goélise",
            "https://www.coleka.com/media/item/20160416/pokemon-xy-ciel-rugissant-goelise-18-108.jpg",
            "Je veux mon shiny !",
                "génération 6"
        )

        val arckoCard = Card(
            "Arcko",
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/XYP/XYP_FR_XY36.png",
            "Il s'y croit un peu là, non ?",
                "génération 7"
        )
        val poussifeuCard = Card(
            "Poussifeu",
            "https://www.pokepedia.fr/images/thumb/6/63/Carte_Platine_99.png/250px-Carte_Platine_99.png",
            "Encore une mignonnerie !",
                "génération 7"
        )
        val gobouCard = Card(
            "Gobou",
            "https://www.pokepedia.fr/images/3/37/Carte_Promo_XY_XY38.png",
            "Blurp Blurp...",
                "génération 7"
        )
        val tenefixCard = Card(
            "Ténéfix",
            "https://i.ebayimg.com/images/g/zEgAAOSwgQ9V1d9b/s-l300.jpg",
            "Quelque chose en nous de Ténéfix...",
                "génération 8"
        )
        val tortipoussCard = Card(
            "Tortipouss",
            "https://www.pokepedia.fr/images/thumb/e/e1/Carte_Promo_DP_DP01.png/250px-Carte_Promo_DP_DP01.png",
            "Choupinou",
                "génération 8"
        )
        val ouisticramCard = Card(
            "Ouisticram",
            "https://www.pokepedia.fr/images/thumb/2/21/Carte_Platine_Vainqueurs_Suprêmes_97.png/250px-Carte_Platine_Vainqueurs_Suprêmes_97.png",
            "Encore un pokémon inutile...",
                "génération 1"
        )
        val tiploufCard = Card(
            "Tiplouf",
            "https://assets.pokemon.com/assets/cms2-fr-fr/img/cards/web/DP1/DP1_FR_93.png",
            "PLOUF !",
                "génération 2"
        )
        val rozboutonCard = Card(
            "Rozbouton",
            "http://www.mypokecard.com/my/galery/0pcE8RMO9tcy.jpg",
            "Evolué avec la pierre Sinnoh !",
                "génération 3"
        )
        val corbossCard = Card(
            "Corboss",
            "https://www.pokepedia.fr/images/thumb/d/de/Carte_Noir_%26_Blanc_Dragons_Exaltés_73.png/250px-Carte_Noir_%26_Blanc_Dragons_Exaltés_73.png",
            "Lui je l'ai en shiny =D",
                "génération 4"
        )

        var pikachu = Pokemon("Pikachu", 25, "Electrik", arrayListOf<Card>(pikachuCard))
        var mew = Pokemon("Mew", 150, "Psy", arrayListOf<Card>(mewCard))
        var tortank = Pokemon("Tortank", 9, "Eau", arrayListOf<Card>(tortankCard))
        var dracaufeu = Pokemon("Dracaufeu", 6, "Feu", arrayListOf<Card>(dracaufeCard))
        var florizarre = Pokemon("Florizarre", 3, "Plante", arrayListOf<Card>(florizarreCard))
        var hericendre = Pokemon("Héricendre", 155, "Feu", arrayListOf<Card>(hericendreCard))
        var germignon = Pokemon("Germignon", 152, "Plante", arrayListOf<Card>(germignonCard))
        var kaiminus = Pokemon("Kaiminus", 158, "Eau", arrayListOf<Card>(kaiminusCard))
        var mentali = Pokemon("Mentali", 196, "Psy", arrayListOf<Card>(mentaliCard))
        var noctali = Pokemon("Noctali", 197, "Ténèbres", arrayListOf<Card>(noctaliCard))
        var arcko = Pokemon("Arcko", 252, "Plante", arrayListOf<Card>(arckoCard))
        var poussifeu = Pokemon("Poussifeu", 255, "Feu", arrayListOf<Card>(poussifeuCard))
        var gobou = Pokemon("Gobou", 258, "Eau", arrayListOf<Card>(gobouCard))
        var tenefix = Pokemon("Ténéfix", 302, "Ténèbres", arrayListOf<Card>(tenefixCard))
        var goelise = Pokemon("Goélise", 278, "Eau, Vol", arrayListOf<Card>(goeliseCard))
        var ouisticram = Pokemon("Ouisticram", 390, "Feu", arrayListOf<Card>(ouisticramCard))
        var tortipouss = Pokemon("Tortipouss", 387, "Plante", arrayListOf<Card>(tortipoussCard))
        var tiplouf = Pokemon("Tiplouf", 393, "Eau", arrayListOf<Card>(tiploufCard))
        var corboss = Pokemon("Corboss", 430, "Ténèbres, Vol", arrayListOf<Card>(corbossCard))
        var rozbouton = Pokemon("Rozbouton", 406, "Plante, Poison", arrayListOf<Card>(rozboutonCard))


        allPokemonList = arrayListOf(arcko, rozbouton, tenefix, tiplouf, tortipouss, tortank, ouisticram, pikachu, poussifeu, mentali, mew, kaiminus, hericendre, germignon, gobou, goelise, florizarre, dracaufeu, corboss, noctali)
        allPokemonList = allPokemonList.sortedWith(compareBy({it.pokedexNumber}))


        userSiam.userPokemonList = arrayListOf(pikachu, mew, hericendre, goelise, corboss, tortank, dracaufeu, mentali, noctali)
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        userSiam.userPokemonList.add(pikachu);
        //userSiam.userPokemonList = userSiam.userPokemonList.sortedWith(compareBy({it.pokedexNumber}))


        //allCardsList = arrayListOf<Card>(mentaliCard, noctaliCard, dracaufeCard, arckoCard, rozboutonCard, tenefixCard, tiploufCard, tortipoussCard, tortankCard, ouisticramCard, pikachuCard, poussifeuCard, florizarreCard, germignonCard, gobouCard, goeliseCard, hericendreCard, kaiminusCard, mewCard, corbossCard)
        var userCardsList = ArrayList<Card>()

        for(pokemon in userSiam.userPokemonList) {
            userCardsList.addAll(pokemon.pokemonCardsList)
        }

        for(card in userCardsList) {
            println("carte " + card.name + " : " + card.version)
        }
    }
}
