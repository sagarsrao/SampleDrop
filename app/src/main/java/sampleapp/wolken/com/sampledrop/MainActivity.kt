package sampleapp.wolken.com.sampledrop

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.View
import android.view.WindowManager
import android.view.ViewGroup
import android.widget.*
import org.jetbrains.annotations.NotNull


class MainActivity : AppCompatActivity() {


    var TAG = "MainActivity.java"

    var popupWindowDogs: PopupWindow? = null
    var buttonShowDropDown: EditText? = null

    lateinit var popUpContents: Array<String?>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonShowDropDown = findViewById(R.id.buttonShowDropDown)


        // initialize pop up window items list

        // add items on the array dynamically
        // format is DogName::DogID
        val dogsList = ArrayList<String>()
        dogsList.add("Akita Inu::1")
        dogsList.add("Alaskan Klee Kai::2")
        dogsList.add("Papillon::3")
        dogsList.add("Tibetan Spaniel::4")


        // convert to simple array

        popUpContents = arrayOfNulls<String>(dogsList.size)
        dogsList.toArray(popUpContents)


        // initialize pop up window
        popupWindowDogs = popupWindowDogs()



        buttonShowDropDown!!.setOnClickListener {


            popupWindowDogs().showAsDropDown(it,-5,0)
        }
    }


    /*Enable the pope window*/
    fun popupWindowDogs(): PopupWindow {

        // initialize a pop up window type
        var popupWindow = PopupWindow(this)

        // the drop down list is a list view
        val listViewDogs = ListView(this)

        // set our adapter and pass our pop up window contents
        listViewDogs.adapter = dogsAdapter(popUpContents)

        // set the item click listener
        listViewDogs.onItemClickListener = DogsDropdownOnItemClickListener()

        // some other visual settings
        popupWindow.isFocusable = true
        popupWindow.width = 400
        popupWindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.GREEN))


        // set the list view as pop up window content
        popupWindow.contentView = listViewDogs

        return popupWindow
    }


    private fun dogsAdapter(dogsArray: Array<String?>?): ArrayAdapter<String> {

        return object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dogsArray) {

           @NotNull
           override fun getView(@NotNull position: Int, @NotNull convertView: View?, @NotNull parent: ViewGroup): View? {

                // setting the ID and text for every items in the list
                val item = getItem(position)
                val itemArr = item!!.split("::".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val text = itemArr[0]
                val id = itemArr[1]

                // visual settings for the list item
                val listItem = TextView(this@MainActivity)

                listItem.text = text
                listItem.tag = id
                listItem.textSize = 22f
                listItem.setPadding(10, 10, 10, 10)
                listItem.setTextColor(Color.WHITE)

                return listItem
            }
        }
    }
}
