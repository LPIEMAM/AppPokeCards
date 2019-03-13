package lpiemam.com.apppokecards

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import lpiemam.com.apppokecards.model.PokemonCard
import lpiemam.com.apppokecards.fragment.*
import lpiemam.com.apppokecards.model.User
import lpiemam.com.apppokecards.viewmodel.PokemonCardsViewModel
import lpiemam.com.apppokecards.model.UserCard
import lpiemam.com.apppokecards.model.UserManager
import java.util.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainActivityListener {

    var user = UserManager.user

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawer: androidx.drawerlayout.widget.DrawerLayout

    lateinit var pokemonCardsFragment: PokemonCardsFragment
    lateinit var addNewCardFragment: AddNewCardFragment
    lateinit var shopFragment: ShopFragment
    lateinit var quizzEndedFragment: QuizzEndedFragment
    lateinit var quizzStartFragment: QuizzStartFragment
    lateinit var userCardsFragment: UserCardsFragment

    var pokemonCardsViewModel : PokemonCardsViewModel? = null

    var hasClickedBack = false

    var toast : Toast? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        pokemonCardsFragment = PokemonCardsFragment.newInstance()
        shopFragment = ShopFragment.newInstance()
        userCardsFragment = UserCardsFragment()

        drawer = drawer_layout
        toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.mainActivityContainer, userCardsFragment, "userCardsFragment")
                .commit()
        }

        pokemonCardsViewModel = ViewModelProviders.of(this).get(PokemonCardsViewModel::class.java)
        pokemonCardsViewModel!!.initializeData()

        drawer.nav_view.getHeaderView(0).userNickNameTextField.text = user?.nickName
        nav_view.setNavigationItemSelectedListener(this)
    }


    private fun getVisibleFragment(): Fragment? {
        val fragmentManager = this@MainActivity.supportFragmentManager
        val fragments = fragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible)
                    return fragment
            }
        }
        return null
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (hasClickedBack) {
            toast!!.cancel()
            user?.dateLastQuizzEnded = Calendar.getInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainActivityContainer, userCardsFragment, "userCardsFragment")
                .commit()
            toast = Toast.makeText(this, "Echec du quizz.", Toast.LENGTH_SHORT)
            toast!!.show()
        } else {
            var currentFragment = getVisibleFragment()
            when (currentFragment) {
                is PokemonCardDetailFragment -> supportFragmentManager.popBackStack()
                is UserCardsFragment -> super.onBackPressed()
                is QuizzFragment -> {
                    toast =
                        Toast.makeText(this, "Si vous recliquez, votre quizz quotidien sera considéré comme un echec.", Toast.LENGTH_SHORT)
                    toast!!.show()
                    hasClickedBack = true
                    Handler().postDelayed({
                        hasClickedBack = false
                        // If this is the last question, ends the game.
                        // Else, display the next question.
                    }, 3000) // LENGTH_SHORT is usually 2 second long
                }
                else -> {
                    for (i in 0 until supportFragmentManager.getBackStackEntryCount()) {
                        supportFragmentManager.popBackStack()
                    }
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.mainActivityContainer, userCardsFragment, "userCardsFragment")
                        .commit()
                }
            }
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
            R.id.menuItemAllCards -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.mainActivityContainer, pokemonCardsFragment, "pokemonCardsFragment")
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

    override fun replaceWithCollectionFragment() {
        val tempCollectionFragment = supportFragmentManager.findFragmentByTag("userCardsFragment")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, tempCollectionFragment!!, "userCardsFragment")
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
            .replace(R.id.mainActivityContainer, PokemonCardDetailFragment.newInstance(pokemonCard), "allCardsDetailFragment")
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

    override fun replaceWithAddNewCardFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainActivityContainer, AddNewCardFragment.newInstance(), "userCardDetailFragment")
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
        if(value) supportActionBar!!.show() else supportActionBar!!.hide()
    }
}
