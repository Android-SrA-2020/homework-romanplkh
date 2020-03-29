package com.example.android.trackmysleepquality.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding


class SleepNightAdapter : ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position);
        holder.bind(item)


    }


    class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
        
        override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {

            //tests whether the two passed-in SleepNight items, oldItem and newItem,
            // are the same. If the items have the same nightId, they are the same item,
            // so return true. Otherwise, return false
            return oldItem.nightId == newItem.nightId

        }

        override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
           //check whether oldItem and newItem contain the same data; that is, whether they are equal.
            // This equality check will check all the fields, because SleepNight is a data class.
            // Data classes automatically define equals and a few other methods for you.
            // If there are differences between oldItem and newItem, this code tells DiffUtil that the item has been updated.
            return oldItem == newItem

            
        }
    }



    class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){


        //With the binding object in place, you don't need to define the sleepLength,
        // quality, and qualityImage properties at all anymore.
        // DataBinding will cache the lookups, so there is no need to declare these properties.

        public fun bind(item: SleepNight) {

            binding.sleep = item
            //This call is an optimization that asks data binding to execute any pending bindings right away.
            // It's always a good idea to call executePendingBindings()
            // when you use binding adapters in a RecyclerView, because it can slightly speed up sizing the views.
            binding.executePendingBindings()

        }




        companion object {
            public fun from(parent: ViewGroup): ViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context);


                //ADD BINDING that inflates the ListItemSleepNightBinding
                val binding = ListItemSleepNightBinding.inflate(layoutInflater, parent, false)

                //ALT + ENTER Select Change parameter 'itemView' type of primary constructor of class 'ViewHolder' to 'ListItemSleepNightBinding'. This updates the parameter type of the ViewHolder class.
                return ViewHolder(binding)
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


