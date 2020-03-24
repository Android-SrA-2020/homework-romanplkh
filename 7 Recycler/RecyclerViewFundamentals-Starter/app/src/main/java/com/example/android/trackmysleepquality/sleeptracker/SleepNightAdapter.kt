package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight


//it adapts a SleepNight object into something that RecyclerView can use.
// The adapter needs to know what view holder to use, so pass in TextItemViewHolder
class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {


    //variable to hold the data
    var data = listOf<SleepNight>()
        //To tell the RecyclerView when the data that it's displaying has changed,
        // add a custom setter to the data variable that's at the top of the SleepNightAdapter
        // class. In the setter, give data a new value, then call notifyDataSetChanged()
        // to trigger redrawing the list with the new data.
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    // The RecyclerView needs to know how many items the adapter has for it to display,
    // and it does that by calling getItemCount()
    override fun getItemCount(): Int {
        return data.size
    }

    // is called by RecyclerView to display the data for one list item at the specified position.
    // So the onBindViewHolder() method takes two arguments: a view holder,
    // and a position of the data to bind. For this app, the holder is the TextItemViewHolder,
    // and the position is the position in the list.

    //Because these view holders are recycled, make sure onBindViewHolder() sets
    // or resets any customizations that previous items might have set on a view holder.

    //For example, you could set the text color to red in view holders that hold quality
    // ratings that are less than or equal to 1 and represent poor sleep.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        //create a variable for one item at a given position in the data.
        val item = data[position];


        holder.bind(item)


        //you could set the text color to red in view holders that hold quality
        // ratings that are less than or equal to 1 and represent poor sleep.
//        if (item.sleepQuality <= 1) {
//            holder.textView.setTextColor(Color.RED) // red
//        } else {
//            // reset
//            holder.textView.setTextColor(Color.BLACK) // black
//        }


        //The ViewHolder you created has a property called textView.
        // Inside onBindViewHolder(), set the text of the textView to the sleep-quality number.
//        holder.textView.text = item.sleepQuality.toString()

    }


    //Create ViewHolder  AS NESTED CLASS
    //ange the signature of the ViewHolder class so that the constructor is private.
    // Because from() is now a method that returns a new ViewHolder instance,
    // there's no reason for anyone to call the constructor of ViewHolder anymore.
    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        //nside ViewHolder, get references to the views.
        // You need a reference to the views that this ViewHolder will update.
        // Every time you bind this ViewHolder, you need to access the image and both text views.

        val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
        val quality: TextView = itemView.findViewById(R.id.quality_string)
        val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)


        //move all the view holder functionality into the ViewHolder. The purpose of this refactoring is
        // not to change how the app looks to the user, but make it easier and safer for developers to work on the code.
        public fun bind(item: SleepNight) {
            // holds a reference to the resources for this view.
            val res = itemView.context.resources

            //Set the text of the sleepLength text view to the duration.
            // Copy the code below, which calls a formatting function that's provided with the starter code.
            sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)


            quality.text = convertNumericQualityToString(item.sleepQuality, res)

            //Set the correct icon for the quality. The new ic_sleep_active icon is provided for you in the starter code.
            qualityImage.setImageResource(when (item.sleepQuality) {
                0 -> R.drawable.ic_sleep_0
                1 -> R.drawable.ic_sleep_1
                2 -> R.drawable.ic_sleep_2
                3 -> R.drawable.ic_sleep_3
                4 -> R.drawable.ic_sleep_4
                5 -> R.drawable.ic_sleep_5
                else -> R.drawable.ic_sleep_active
            })
        }



        //The from() function needs to be in a companion object so it can be called on the ViewHolder
        // class, not called on a ViewHolder instance.
        companion object {
            public fun from(parent: ViewGroup): ViewHolder {
                //The layout inflater knows how to create views from XML layouts.
                // The context contains information on how to correctly inflate the view.
                // In an adapter for a recycler view, you always pass in the context of
                // the parent view group, which is the RecyclerView.
                val layoutInflater = LayoutInflater.from(parent.context);


                //create the view by asking the layoutinflater to inflate it.
                //
                //Pass in the XML layout for the view, and the parent view group for the view.
                // The third, boolean, argument is attachToRoot. This argument needs to be false,
                // because RecyclerView adds this item to the view hierarchy for you when it's time.
                val view = layoutInflater
                        .inflate(R.layout.list_item_sleep_night, parent, false)

                return ViewHolder(view)
            }
        }


    }


    //This function takes two parameters and returns a ViewHolder.
    // The parent parameter, which is the view group that holds the view holder, is always the RecyclerView.
    // The viewType parameter is used when there are multiple views in the same RecyclerView.
    // For example, if you put a list of text views, an image, and a video all in the same
    // RecyclerView, the onCreateViewHolder() function would need to know what type of view to use.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder.from(parent)

    }




}


