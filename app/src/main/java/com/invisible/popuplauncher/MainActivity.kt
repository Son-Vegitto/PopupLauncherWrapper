package com.invisible.popuplauncher

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val targetPackage = "com.ss.popuplauncher"
        val launchIntent = packageManager.getLaunchIntentForPackage(targetPackage)

        if (launchIntent != null) {
            startActivity(launchIntent)
        }

        // Instantly kill activity without animations
        finish()
        overridePendingTransition(0, 0)
    }
}
