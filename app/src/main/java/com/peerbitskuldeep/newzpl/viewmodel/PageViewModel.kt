package com.peerbitskuldeep.newzpl.viewmodel



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class PageViewModel : ViewModel() {


    var header = MutableLiveData("This is a Header")

    fun bodyData(){
        header.value = "Button Click executed"
    }
}