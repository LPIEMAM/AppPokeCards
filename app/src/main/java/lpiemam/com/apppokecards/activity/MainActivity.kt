package lpiemam.com.apppokecards.activity

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import lpiemam.com.apppokecards.listeners.MainActivityListener
import lpiemam.com.apppokecards.R
import lpiemam.com.apppokecards.fragment.*
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.model.UserCard
import lpiemam.com.apppokecards.model.UserManager
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainActivityListener {


    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer: androidx.drawerlayout.widget.DrawerLayout

    var currentlySelectedDrawerItem: Int = 0

    lateinit var pokemonCardsFragment: PokemonCardsFragment
    lateinit var shopFragment: ShopFragment
    lateinit var userCardsFragment: UserCardsFragment

    private var pokemonCardsViewModel: PokemonCardsViewModel? = null

    private var hasClickedBack = false

    private var toast: Toast? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        pokemonCardsViewModel = ViewModelProviders.of(this).get(PokemonCardsViewModel::class.java)
        pokemonCardsViewModel!!.initializeData()

        pokemonCardsFragment = PokemonCardsFragment.newInstance()
        shopFragment = ShopFragment.newInstance()
        userCardsFragment = UserCardsFragment.newInstance("userCards")

        drawer = drawer_layout
        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            currentlySelectedDrawerItem = R.id.menuItemCollection
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainActivityContainer, userCardsFragment, "userCardsFragment")
                .commit()
        }

        updateUserInfos()

        nav_view.setNavigationItemSelectedListener(this)
    }


    private fun getVisibleFragment(): Fragment? {
        val fragmentManager = this@MainActivity.supportFragmentManager
        val fragments = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != null && fragment.isVisible)
                return fragment
        }
        return null
    }

    private fun endQuizz() {
        toast!!.cancel()
        UserManager.loggedUser?.dateLastQuizzEndedDate = Calendar.getInstance().time
        pokemonCardsViewModel!!.updateUserInDB(UserManager.loggedUser!!)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, userCardsFragment, "userCardsFragment")
            .commit()
        toast = Toast.makeText(this, "Echec du quizz.", Toast.LENGTH_SHORT)
        toast!!.show()
    }

    private fun warnQuizz() {
        toast =
            Toast.makeText(
                this,
                "Si vous recliquez, votre quizz quotidien sera considéré comme un echec.",
                Toast.LENGTH_SHORT
            )
        toast!!.show()
        hasClickedBack = true
        Handler().postDelayed({
            hasClickedBack = false
            // If this is the last question, ends the game.
            // Else, display the next question.
        }, 3000) // LENGTH_SHORT is usually 2 second long
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (hasClickedBack) {
            endQuizz()
        } else {
            var currentFragment = getVisibleFragment()
            when (currentFragment) {
                is PokemonCardDetailFragment -> supportFragmentManager.popBackStack()
                is FullScreenCardFragment -> supportFragmentManager.popBackStack()
                is UserCardsFragment -> {
                    if(supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStack()
                    } else {
                        moveTaskToBack(true)
                    }
                }
                is TradeListFragment -> supportFragmentManager.popBackStack()
                is QuizzFragment -> {
                    warnQuizz()
                }
                else -> {
                    for (i in 0 until supportFragmentManager.getBackStackEntryCount()) {
                        supportFragmentManager.popBackStack()
                    }
                    currentlySelectedDrawerItem = R.id.menuItemCollection
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainActivityContainer, userCardsFragment, "userCardsFragment")
                        .commit()
                }
            }
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        if(currentlySelectedDrawerItem != item.itemId) {
            currentlySelectedDrawerItem = item.itemId
            when (item.itemId) {
                R.id.menuItemCollection -> {
                    for (i in 0 until supportFragmentManager.backStackEntryCount) {
                        supportFragmentManager.popBackStack()
                    }
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainActivityContainer, userCardsFragment, "userCardsFragment")
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
                        .replace(R.id.mainActivityContainer, QuizzStartFragment.newInstance(), "quizzStartFragment")
                        .commit()

                }
                R.id.menuItemTrade -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainActivityContainer, TradeFragment.newInstance(), "tradeFragment")
                        .commit()
                }
                R.id.menuItemAllCards -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainActivityContainer, pokemonCardsFragment, "pokemonCardsFragment")
                        .commit()
                }
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

    override fun setUpBackButton(enabled: Boolean) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(enabled)
        if (enabled) {
            toolbar.setNavigationOnClickListener {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    supportFragmentManager.popBackStack()
                }
            }

        } else {
            toolbar.setNavigationOnClickListener {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    drawer_layout.openDrawer(GravityCompat.START)
                }

            }
        }
    }


    override fun replaceWithFullScreenCard(pokemonCard: PokemonCard, boolean: Boolean) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.mainActivityContainer,
                FullScreenCardFragment.newInstance(pokemonCard, boolean),
                "fullScreenCardFragment"
            ).addToBackStack(null)
            .commit()
    }

    override fun replaceWithUserDetailFragment(userCard: UserCard) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, UserCardDetailFragment.newInstance(userCard), "userCardDetailFragment")
            .addToBackStack(null)
            .commit()
    }

    override fun replaceWithAllCardsFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, pokemonCardsFragment, "pokemonCardsFragment")
            .commit()
    }

    override fun replaceWithAllCardsDetailFragment(pokemonCard: PokemonCard) {
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.mainActivityContainer,
                PokemonCardDetailFragment.newInstance(pokemonCard),
                "allCardsDetailFragment"
            )
            .addToBackStack(null)
            .commit()
    }

    override fun goBackFromFullScreenToAllCardsFragment() {
        supportFragmentManager.popBackStack()
        supportFragmentManager.popBackStack()
    }

    override fun replaceWithQuizzFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, QuizzFragment.newInstance(), "quizzFragment")
            .commit()
    }

    override fun replaceWithQuizzStartFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, QuizzStartFragment.newInstance(), "quizzStartFragment")
            .commit()
    }

    override fun replaceWithQuizzEndedFragment(userWonQuiz: Boolean) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, QuizzEndedFragment.newInstance(userWonQuiz), "quizzEndedFragment")
            .commit()
    }

    override fun replaceWithPickCardFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, UserCardsFragment.newInstance("trade"), "tradeFragment")
            .addToBackStack(null)
            .commit()
    }


    override fun replaceWithChooseCardFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, TradeListFragment.newInstance(), "tradeFragment")
            .addToBackStack(null)
            .commit()
    }

    override fun replaceWithFragment(fragment: androidx.fragment.app.Fragment, tag: String?) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, fragment, tag)
            .commit()
    }

    override fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

    override fun notifyCollectionDataSetChanged() {
        userCardsFragment.userCardAdapter!!.notifyDataSetChanged()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText || v is SearchView) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun setFragmentTitle(title: String) {
        supportActionBar!!.title = title
    }

    override fun showActionBar(value: Boolean) {
        if (value) supportActionBar!!.show() else supportActionBar!!.hide()
    }

    override fun updateUserInfos() {
        drawer.nav_view.getHeaderView(0).userNickNameTextField.text = UserManager.loggedUser?.nickName
        drawer.nav_view.getHeaderView(0).userCoinsTextField.text = UserManager.loggedUser?.coins.toString()
        drawer.nav_view.getHeaderView(0).userDustsTextField.text = UserManager.loggedUser?.dusts.toString()
    }
}
