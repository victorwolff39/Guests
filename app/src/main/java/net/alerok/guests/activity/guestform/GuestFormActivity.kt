package net.alerok.guests.activity.guestform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_guest_form.*
import net.alerok.guests.R

class GuestFormActivity : AppCompatActivity() {

    private lateinit var mViewModel: GuestsFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        mViewModel = ViewModelProvider(this).get(GuestsFormViewModel::class.java)

        observe()

        button_save.setOnClickListener() {
            val name = edit_name.text.toString()
            val isPresent = radio_present.isChecked

            mViewModel.save(name, isPresent)
        }

    }

    private fun observe() {
        mViewModel.saveGuest.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, getString(R.string.success_save), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, getString(R.string.failed_save), Toast.LENGTH_SHORT).show()
            }
            finish()
        })
    }

}
