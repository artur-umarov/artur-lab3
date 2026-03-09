package ru.omgtu.lr2.fragments
import ru.omgtu.lr2.fragments.BaseFragment
import android.content.Context
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    // Интерфейс для связи с Activity
    interface OnFragmentInteractionListener {
        fun onButtonPressed(buttonId: Int)
    }

    // Переменная для хранения слушателя
    protected var listener: OnFragmentInteractionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}