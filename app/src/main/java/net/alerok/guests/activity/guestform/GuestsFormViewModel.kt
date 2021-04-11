package net.alerok.guests.activity.guestform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.alerok.guests.service.models.GuestModel
import net.alerok.guests.service.repository.GuestRepository

class GuestsFormViewModel(application: Application) : AndroidViewModel(application) {

    private val mContext = application.applicationContext
    private var mGuestRepository: GuestRepository = GuestRepository.getInstance(mContext)

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest

    fun save(name: String, isPresent: Boolean) {
        val guest = GuestModel(name = name, isPresent = isPresent)
        mSaveGuest.value = mGuestRepository.save(guest)
    }

}
