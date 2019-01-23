package lpiemam.com.apppokecards

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import lpiemam.com.apppokecards.fragment.AddNewCardFragment
import lpiemam.com.apppokecards.fragment.AllCardsFragment
import lpiemam.com.apppokecards.fragment.CollectionFragment
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.Pokemon
import lpiemam.com.apppokecards.model.User
import android.support.v4.widget.DrawerLayout
import lpiemam.com.apppokecards.fragment.UserCardDetailFragment


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ReplaceFragmentListener {


    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer: DrawerLayout

    lateinit var allCardsList: ArrayList<Card>
    lateinit var allPokemonList : ArrayList<Pokemon>
    lateinit var userSiam : User
    lateinit var collectionFragment : CollectionFragment
    lateinit var allCardsFragment : AllCardsFragment
    lateinit var addNewCardFragment : AddNewCardFragment
    lateinit var allCardsUserNeeds: ArrayList<Card>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        collectionFragment = CollectionFragment.newInstance()
        allCardsFragment = AllCardsFragment.newInstance()
        addNewCardFragment = AddNewCardFragment.newInstance()

        initializeData()

        drawer = drawer_layout
        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainActivityContainer, collectionFragment, "collectionFragment")
                .commit()
        }



        nav_view.setNavigationItemSelectedListener(this)
    }



    override fun onBackPressed() {
        Snackbar.make(mainActivityContainer, "Add a new Card", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else {
            super.onBackPressed()
        }
    }


//    override fun onSupportNavigateUp(): Boolean {
//        Snackbar.make(mainActivityContainer, "Add a new Card", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show()
//        supportFragmentManager.popBackStack()
//        return super.onSupportNavigateUp()
//    }


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
            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.menuItemCollection -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainActivityContainer, collectionFragment, "collectionFragment")
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
//                supportFragmentManager
//                    .beginTransaction()
//                    .add(R.id.mainActivityContainer, UserCardDetailFragment.newInstance(), "collectionFragment")
//                    .commit()
            }
            R.id.menuItemAllCards -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainActivityContainer, allCardsFragment, "allCardsFragment")
                    .commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun initializeData() {
        userSiam = User("Annabelle", "Braye", "Siam", "annabelle.braye@gmail.com", "")

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
        allPokemonList = ArrayList(allPokemonList.sortedWith(compareBy({it.pokedexNumber})))

        //Liste de TOUTES les cartes de TOUS les pokémons
        allCardsList = arrayListOf(arckoCard, rozboutonCard, tenefixCard, tiploufCard, tortipoussCard, tortankCard, ouisticramCard, pikachuCard, pikachuCard2, poussifeuCard, mentaliCard, mewCard, kaiminusCard, hericendreCard, germignonCard, gobouCard, goeliseCard, florizarreCard, dracaufeuCard, corbossCard, noctaliCard)

        //Liste des pokémons dont l'utilisateur possède une carte
        userSiam.userCardList = arrayListOf(pikachuCard, mewCard, hericendreCard, goeliseCard, corbossCard, tortankCard, dracaufeuCard, mentaliCard, noctaliCard)
        userSiam.userCardList = ArrayList(userSiam.userCardList.sortedWith(compareBy{it.pokemon.pokedexNumber}))

        //Liste des cartes pokémons que l'utilisateur n'a pas
        allCardsUserNeeds = ArrayList()

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
            if(firewall)
            {
                allCardsUserNeeds.add(card)
            }
        }

    }


    override fun setDrawerEnabled(enabled: Boolean) {
        val lockMode = if (enabled) {
            DrawerLayout.LOCK_MODE_UNLOCKED
        } else {
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        }
        drawer.setDrawerLockMode(lockMode)
        toggle.isDrawerIndicatorEnabled = enabled

    }

    override fun showUserCardDetail(card: Card) {
        val userCardDetailFragment = UserCardDetailFragment.newInstance()
        userCardDetailFragment.card = card
        supportFragmentManager!!
            .beginTransaction()
            .add(R.id.mainActivityContainer, userCardDetailFragment, "userCardDetailFragment").addToBackStack("CollectionFragment")
            .addToBackStack("userCardDetailFragment")
            .commit()
    }

    override fun setUpBackButton(enabled: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(enabled)
        if(enabled) {
            toolbar.setNavigationOnClickListener{
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                }
                else {
                    super.onBackPressed()
                }
            }

        } else {
            toolbar.setNavigationOnClickListener{
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    drawer_layout.openDrawer(GravityCompat.START)
                }

            }
        }
    }
}
