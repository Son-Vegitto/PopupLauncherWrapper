package com.invisible.popuplauncher

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val targetPackage = "com.ss.popuplauncher"
        val possibleActivities = listOf(
            "$targetPackage.LaunchActivity",
            "$targetPackage.MainActivity"
        )

        var launchedSuccessfully = false

        for (activityName in possibleActivities) {
            val component = ComponentName(targetPackage, activityName)
            if (isActivityAvailable(component)) {
                val intent = Intent(Intent.ACTION_MAIN).apply {
                    addCategory(Intent.CATEGORY_LAUNCHER)
                    setComponent(component)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                try {
                    startActivity(intent)
                    launchedSuccessfully = true
                    break
                } catch (_: Exception) {
                    // Fallthrough to next candidate
                }
            }
        }

        if (!launchedSuccessfully) {
            Toast.makeText(
                this,
                "Popup Launcher is not installed or its launch activity has changed.",
                Toast.LENGTH_LONG
            ).show()
        }

        // Instantly kill activity without animations or splash delay
        finish()
        overridePendingTransition(0, 0)
    }

    private fun isActivityAvailable(componentName: ComponentName): Boolean {
        return try {
            packageManager.getActivityInfo(componentName, 0).enabled
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
