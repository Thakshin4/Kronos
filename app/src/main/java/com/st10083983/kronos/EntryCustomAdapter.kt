import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.st10083983.kronos.TimesheetReportItems
import com.st10083983.kronos.R

class EntryCustomAdapter(private val mList: List<TimesheetReportItems>) : RecyclerView.Adapter<EntryCustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val reportItems = mList[position]

        // sets the image to the textview from our itemHolder class
        val entryItem = "Category: ${reportItems.entryCategory} \nDate: ${reportItems.entryDate} \nHours Worked: ${reportItems.entryHours} \nDescription: ${reportItems.entryDescription}"
        holder.entry.text =  entryItem
    }

    // return the number of the items in the list
    override fun getItemCount(): Int
    {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val entry: TextView = itemView.findViewById(R.id.item)
    }
}
