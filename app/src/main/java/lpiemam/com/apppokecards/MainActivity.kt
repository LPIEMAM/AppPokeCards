package lpiemam.com.apppokecards

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.User

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

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
        val siam = User("Annabelle", "Braye", "Siam", "annabelle.braye@gmail.com", "")
        val pikachuCard = Card(
            "Pikachu",
            25,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjexNqog__fAhWz5OAKHaKlBWkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.amazon.fr%2Fcarte-pikachu-Cartes-%25C3%25A0-collectionner-Pok%25C3%25A9mon%2Fs%3Fie%3DUTF8%26page%3D1%26rh%3Dn%253A363600031%252Ck%253Acarte%2520pikachu%252Cp_n_featured_character_browse-bin%253A374090011&psig=AOvVaw0epwHCYCz2OQBqgN_yqrhD&ust=1548165297395426",
            "Electrik",
            "Pikachu est surnommé Souris électrique..."
        )
        val miewCard = Card(
            "Mew",
            150,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiSi7bDg__fAhXN6eAKHXAnCGYQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pinterest.fr%2Fpin%2F337418197061977264%2F&psig=AOvVaw2C9-l6ZHAxbIvK3NGLLK2P&ust=1548165332119194",
            "Psy",
            "Mais qu'il est mignon ce pokemon !"
        )
        val florizarreCard = Card(
            "Florizarre",
            3,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwj_m77xif_fAhWiAGMBHYTTDN0QjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokemon.com%2Ffr%2Fjcc-pokemon%2Fcartes-pokemon%2Fplatinum-series%2Fpl3%2F13%2F&psig=AOvVaw34B6QgYx8Q47F_ZUmNbI91&ust=1548167058814917",
            "Plante",
            "Raaaaaaah !"
        )
        val tortankCard = Card(
            "Tortank",
            9,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjz1ZmDiv_fAhVJ8OAKHcLwBPkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokemon.com%2Ffr%2Fjcc-pokemon%2Fcartes-pokemon%2Fdiamond-pearl-series%2Fdp3%2F2%2F&psig=AOvVaw2pFdT7sKC-E528e4Me_X17&ust=1548167100847774",
            "Eau",
            "Taaaaaank !"
        )
        val dracaufeCard = Card(
            "Dracaufeu",
            6,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi0xaqPiv_fAhWPlhQKHQxmBScQjRx6BAgBEAU&url=%2Furl%3Fsa%3Di%26source%3Dimages%26cd%3D%26ved%3D%26url%3Dhttps%253A%252F%252Fwww.amazon.fr%252Fcarte-Pok%2525C3%2525A9mon-108-Dracaufeu-Niv-76%252Fdp%252FB01NB1U8WQ%26psig%3DAOvVaw2OaYX0nNeHfFUV0-nqnZww%26ust%3D1548167122948881&psig=AOvVaw2OaYX0nNeHfFUV0-nqnZww&ust=1548167122948881",
            "Feu",
            "*Crache des flammes*"
        )
        val hericendreCard = Card(
            "Héricendre",
            155,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjIo82lhP_fAhVD6uAKHQOmAsEQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokepedia.fr%2FH%25C3%25A9ricendre_(EX_Forces_Cach%25C3%25A9es_54)&psig=AOvVaw3ycyyP7nwEdcVwkMj8o3c5&ust=1548165560939302",
            "Feu",
            "Lui aussi il est chou..."
        )
        val germignonCard = Card(
            "Germignon",
            152,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiY1s_liv_fAhUR4OAKHfmzByIQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokemon.com%2Ffr%2Fjcc-pokemon%2Fcartes-pokemon%2Fhgss-series%2Fcol1%2F53%2F&psig=AOvVaw3943WOl_96Ou-HPOK-Dq6U&ust=1548167307843757",
            "Plante",
            "La peluche est choupi !"
        )
        val kaiminusCard = Card(
            "Kaiminus",
            158,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwj2_P70iv_fAhUKnhQKHZaPCxIQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokepedia.fr%2FKaiminus_(L%2527Appel_des_L%25C3%25A9gendes_74)&psig=AOvVaw04B-ZW7ezse8QGVvBY3q5l&ust=1548167340460726",
            "Eau",
            "IV 100 <3"
        )
        val goeliseCard = Card(
            "Goélise",
            278,
            "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiB6_jlhP_fAhWdD2MBHT3lDy4QjRx6BAgBEAU&url=https%3A%2F%2Fwww.coleka.com%2Ffr%2Fcartes-de-collection%2Fcartes-pokemon%2Fpokemon-xy%2Fpokemon-xy-ciel-rugissant%2Fgoelise_i8576&psig=AOvVaw0anVzxC--dukhqIDh_9Vca&ust=1548165697519755",
            "Vol, Eau",
            "Je veux mon shiny !"
        )

        val arckoCard = Card(
            "Arcko",
            252,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjiuu6zi__fAhXzDmMBHXlVBN4QjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokemon.com%2Ffr%2Fjcc-pokemon%2Fcartes-pokemon%2Fxy-series%2Fxyp%2FXY36%2F&psig=AOvVaw2Ef1DiSo722Vl6sec2R0ek&ust=1548167471902814",
            "Plante",
            "Il s'y croit un peu là, non ?"
        )
        val poussifeuCard = Card(
            "Poussifeu",
            255,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi5kZvEi__fAhVw1eAKHaveDuoQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokemon.com%2Ffr%2Fjcc-pokemon%2Fcartes-pokemon%2Fxy-series%2Fxy5%2F25%2F&psig=AOvVaw1kjKO5rjzZbvPAhfLtOajE&ust=1548167506499756",
            "Feu",
            "Encore une mignonnerie !"
        )
        val gobouCard = Card(
            "Gobou",
            258,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjMzbnRi__fAhV18OAKHdjTAkkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokepedia.fr%2FGobou_(Promo_Nintendo_010)&psig=AOvVaw36l7sS1boZMx5j4sOfFmyz&ust=1548167533634543",
            "Eau",
            "Blurp Blurp..."
        )
        val tenefixCard = Card(
            "Ténéfix",
            302,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjCl_eWjf_fAhVSA2MBHXSeCxEQjRx6BAgBEAU&url=%2Furl%3Fsa%3Di%26source%3Dimages%26cd%3D%26ved%3D%26url%3Dhttps%253A%252F%252Fwww.pokepedia.fr%252FT%2525C3%2525A9n%2525C3%2525A9fix_(XY_68)%26psig%3DAOvVaw1D7AYPPtHyU2iSDNrqcpec%26ust%3D1548167941126265&psig=AOvVaw1D7AYPPtHyU2iSDNrqcpec&ust=1548167941126265",
            "Spectre, Ténèbres",
            "Quelque chose en nous de Ténéfix..."
        )
        val tortipoussCard = Card(
            "Tortipouss",
            387,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwi69ennjf_fAhWOERQKHeuqC_AQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokepedia.fr%2FTortipouss_(Promo_DP_01)&psig=AOvVaw1g7OpfyhVCUAxEkn-sCGLC&ust=1548168118363163",
            "Plante",
            "Choupinou"
        )
        val ouisticramCard = Card(
            "Ouisticram",
            390,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwic55KGjv_fAhUMmBQKHZCBD_UQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokepedia.fr%2FOuisticram_(Platine_70)&psig=AOvVaw2dIK5QTggAjezyjEREKX7H&ust=1548168179833582",
            "Feu",
            "Encore un pokémon inutile..."
        )
        val tiploufCard = Card(
            "Tiplouf",
            393,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjH3syfjv_fAhVE2OAKHREuCuQQjRx6BAgBEAU&url=https%3A%2F%2Fwww.ebay.fr%2Fitm%2FCARTE-POKEMON-TIPLOUF-32-156-SOLEIL-ET-LUNE-5-ULTRA-PRISME-%2F292442024318&psig=AOvVaw30pK3gnq9kindJiZF0Glwu&ust=1548168235247487",
            "Eau",
            "PLOUF !"
        )
        val rozboutonCard = Card(
            "Rozbouton",
            406,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjkvL7Jjv_fAhWPkhQKHdvxBDEQjRx6BAgBEAU&url=http%3A%2F%2Fwww.mypokecard.com%2Ffr%2FGalerie%2FPokemon-Rozbouton-15&psig=AOvVaw0Yszai0gcOsO4mDBUsDiLR&ust=1548168322668923",
            "Plante, Poison",
            "Evolué avec la pierre Sinnoh !"
        )
        val corbossCard = Card(
            "Corboss",
            430,
            "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjIo82lhP_fAhVD6uAKHQOmAsEQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokepedia.fr%2FH%25C3%25A9ricendre_(EX_Forces_Cach%25C3%25A9es_54)&psig=AOvVaw3ycyyP7nwEdcVwkMj8o3c5&ust=1548165560939302",
            "Feu",
            "Lui je l'ai en shiny =D"
        )

        val cardsList = arrayListOf<Card>(dracaufeCard, arckoCard, rozboutonCard, tenefixCard, tiploufCard, tortipoussCard, tortankCard, ouisticramCard, pikachuCard, poussifeuCard, florizarreCard, germignonCard, gobouCard, goeliseCard, hericendreCard, kaiminusCard, miewCard, corbossCard)
        siam.userCardsList = arrayListOf(pikachuCard, miewCard, hericendreCard, goeliseCard, corbossCard, tortankCard, dracaufeCard)


        for(card in cardsList) {
            println("carte " + card.pokemonName + " : " + card.version)
        }
    }
}
