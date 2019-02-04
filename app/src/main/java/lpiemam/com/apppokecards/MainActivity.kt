package lpiemam.com.apppokecards

import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import lpiemam.com.apppokecards.model.Card
import lpiemam.com.apppokecards.model.Pokemon
import lpiemam.com.apppokecards.model.User
import androidx.drawerlayout.widget.DrawerLayout
import lpiemam.com.apppokecards.fragment.*
import lpiemam.com.apppokecards.model.Manager


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    ReplaceFragmentListener {


    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer: androidx.drawerlayout.widget.DrawerLayout

    lateinit var collectionFragment : CollectionFragment
    lateinit var allCardsFragment : AllCardsFragment
    lateinit var addNewCardFragment : AddNewCardFragment
    lateinit var shopFragment : ShopFragment
    lateinit var quizzFragment: QuizzFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        collectionFragment = CollectionFragment.newInstance()
        allCardsFragment = AllCardsFragment.newInstance()
        addNewCardFragment = AddNewCardFragment.newInstance()
        shopFragment = ShopFragment.newInstance()
        quizzFragment = QuizzFragment.newInstance()


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

        Manager.initializeData()

        nav_view.setNavigationItemSelectedListener(this)
    }



    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStack()
        else {
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
                    .replace(R.id.mainActivityContainer, collectionFragment, "collectionFragment")
                    .commit()
            }
            R.id.menuItemShop -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainActivityContainer, shopFragment, "shopFragment")
                    .commit()
            }
            R.id.menuItemQuizz -> {
                supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainActivityContainer, quizzFragment, "quizzFragment")
                .commit()
            }
            R.id.menuItemAchievements -> {
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.mainActivityContainer, UserCardDetailFragment.newInstance(), "collectionFragment")
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




    override fun setDrawerEnabled(enabled: Boolean) {
        val lockMode = if (enabled) {
            androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
        } else {
            androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
        }
        drawer.setDrawerLockMode(lockMode)
        toggle.isDrawerIndicatorEnabled = enabled

    }

    override fun replaceWithUserDetailFragment(card: Card) {
        val userCardDetailFragment = UserCardDetailFragment.newInstance()
        userCardDetailFragment.card = card
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, userCardDetailFragment, "userCardDetailFragment")
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


    override fun replaceWithAddNewCardFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, AddNewCardFragment.newInstance(), "userCardDetailFragment")
            .addToBackStack("userCardDetailFragment")
            .commit()
    }

    override fun replaceWithFragment(fragment: androidx.fragment.app.Fragment, tag : String?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

    override fun notifyCollectionDataSetChanged() {
        collectionFragment.userCardAdapter!!.notifyDataSetChanged()
    }
}
