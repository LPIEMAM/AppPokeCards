package lpiemam.com.apppokecards.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import lpiemam.com.apppokecards.MainActivityListener

abstract class BaseFragment : Fragment() {
    var mainActivityListener: MainActivityListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivityListener = context as? MainActivityListener
        if (mainActivityListener == null) {
            throw ClassCastException("$context must implement MainActivityListener")
        }
    }

    override fun onDetach() {

        mainActivityListener = null
        super.onDetach()
    }


    fun setFragmentTitle(value: String) {
        mainActivityListener?.setFragmentTitle(value)
    }

    fun setDrawerEnabled(value: Boolean) {
        mainActivityListener?.setDrawerEnabled(value)
    }

    fun setUpBackButton(value: Boolean) {
        mainActivityListener?.setUpBackButton(value)
    }

    fun showActionBar(value: Boolean) {
        mainActivityListener?.showActionBar(value)
    }

    fun updateUserInfos() {
        mainActivityListener?.updateUserInfos()
    }

}