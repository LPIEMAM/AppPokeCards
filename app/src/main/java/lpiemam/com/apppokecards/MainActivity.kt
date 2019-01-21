package lpiemam.com.apppokecards

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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
//                supportFragmentManager
//                    .beginTransaction()
//                    .add(R.id.mainActivityContainer, AchievementsFragment.newInstance(), "collectionFragment")
//                    .commit()
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
        val pikachuCard = Card("Pikachu", 25, "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjexNqog__fAhWz5OAKHaKlBWkQjRx6BAgBEAU&url=https%3A%2F%2Fwww.amazon.fr%2Fcarte-pikachu-Cartes-%25C3%25A0-collectionner-Pok%25C3%25A9mon%2Fs%3Fie%3DUTF8%26page%3D1%26rh%3Dn%253A363600031%252Ck%253Acarte%2520pikachu%252Cp_n_featured_character_browse-bin%253A374090011&psig=AOvVaw0epwHCYCz2OQBqgN_yqrhD&ust=1548165297395426", "Electrik", "Pikachu est surnommé Souris électrique...")
        val miewCard = Card("Mew", 150, "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwiSi7bDg__fAhXN6eAKHXAnCGYQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pinterest.fr%2Fpin%2F337418197061977264%2F&psig=AOvVaw2C9-l6ZHAxbIvK3NGLLK2P&ust=1548165332119194", "Psy", "Mais qu'il est mignon ce pokemon !")
        val hericendreCard = Card("Héricendre", 155, "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjIo82lhP_fAhVD6uAKHQOmAsEQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokepedia.fr%2FH%25C3%25A9ricendre_(EX_Forces_Cach%25C3%25A9es_54)&psig=AOvVaw3ycyyP7nwEdcVwkMj8o3c5&ust=1548165560939302", "Feu", "Lui aussi il est chou...")
        val goeliseCard = Card("Goélise", 278, "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwiB6_jlhP_fAhWdD2MBHT3lDy4QjRx6BAgBEAU&url=https%3A%2F%2Fwww.coleka.com%2Ffr%2Fcartes-de-collection%2Fcartes-pokemon%2Fpokemon-xy%2Fpokemon-xy-ciel-rugissant%2Fgoelise_i8576&psig=AOvVaw0anVzxC--dukhqIDh_9Vca&ust=1548165697519755", "Feu", "Je veux mon shiny !")
        val corbossCard = Card("Corboss", 430, "https://www.google.com/url?sa=i&source=images&cd=&cad=rja&uact=8&ved=2ahUKEwjIo82lhP_fAhVD6uAKHQOmAsEQjRx6BAgBEAU&url=https%3A%2F%2Fwww.pokepedia.fr%2FH%25C3%25A9ricendre_(EX_Forces_Cach%25C3%25A9es_54)&psig=AOvVaw3ycyyP7nwEdcVwkMj8o3c5&ust=1548165560939302", "Feu", "Lui je l'ai en shiny =D")

        siam.userCardsList.add(pikachuCard)
        siam.userCardsList.add(miewCard)
        siam.userCardsList.add(hericendreCard)
        siam.userCardsList.add(goeliseCard)
        siam.userCardsList.add(corbossCard)
    }
}
