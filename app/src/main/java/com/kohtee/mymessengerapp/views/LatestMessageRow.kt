package com.kohtee.mymessengerapp.views

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kohtee.mymessengerapp.R
import com.kohtee.mymessengerapp.models.ChatMessage
import com.kohtee.mymessengerapp.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow(val chatMessage: ChatMessage): Item<GroupieViewHolder>() {
    var chatPartnerUser: User? = null
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.message_textview_latest_message.text = chatMessage.text

        val chatPartnerId: String
        if(chatMessage.fromId == FirebaseAuth.getInstance().uid){
            chatPartnerId = chatMessage.toId
        }else{
            chatPartnerId = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatPartnerUser= snapshot.getValue(User::class.java)
                viewHolder.itemView.username_textview_leatest_message.text = chatPartnerUser?.username

                val targettImageView = viewHolder.itemView.imageview_latest_message
                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targettImageView)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

//        viewHolder.itemView.username_textview_leatest_message.text = "WHATEVER THE HECK YOU WANT"

    }
    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}