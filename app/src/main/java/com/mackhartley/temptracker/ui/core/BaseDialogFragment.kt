package com.mackhartley.temptracker.ui.core

import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {
    abstract val fragTag: String
}