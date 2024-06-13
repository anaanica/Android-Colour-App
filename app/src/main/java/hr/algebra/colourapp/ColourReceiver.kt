package hr.algebra.colourapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.colourapp.framework.setBooleanPreference
import hr.algebra.colourapp.framework.startActivity

class ColourReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //FOREGROUND
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()
    }
}