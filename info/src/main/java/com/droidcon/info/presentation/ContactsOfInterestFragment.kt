package com.droidcon.info.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.droidcon.info.R
import dagger.android.support.DaggerFragment


class ContactsOfInterestFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_contacts_of_interest, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Shitty code to render 112 phone number text as a link redirecting to Dialer
        val emergenciesLabelText = getString(R.string.emergencies)
        val startOf112 = emergenciesLabelText.length - 3
        val endOf112 = emergenciesLabelText.length
        view.findViewById<TextView>(R.id.emergenciesLabel).run {
            val span = SpannableString(emergenciesLabelText)

            val spanColor = ForegroundColorSpan(linkTextColors.defaultColor)
            val spanClick = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Intent(Intent.ACTION_DIAL).run {
                        data = Uri.parse("tel:112")
                        startActivity(this)
                    }
                }
            }
            span.setSpan(spanColor, startOf112, endOf112, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            span.setSpan(spanClick, startOf112, endOf112, Spanned.SPAN_INCLUSIVE_INCLUSIVE)

            text = span
            movementMethod = LinkMovementMethod.getInstance()
        }

        view.findViewById<Toolbar>(R.id.toolbar).setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}