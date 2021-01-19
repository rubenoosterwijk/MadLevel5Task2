package com.example.madlevel5task2.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.adapter.GameBacklogAdapter
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.viewmodel.GameViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_game_backlog.*
import kotlinx.android.synthetic.main.item_card_game_backlog.view.*
import java.text.SimpleDateFormat




/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    private lateinit var navController: NavController

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameBacklogAdapter(games)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_backlog, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
        view.findViewById<FloatingActionButton>(R.id.fabAddGame).setOnClickListener { view ->
            navController.navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        initViews()

        observeAddGameResult()
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvBacklog.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvBacklog.adapter = gameAdapter
        rvBacklog.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))

        createItemTouchHelper().attachToRecyclerView(rvBacklog)
    }


    private fun observeAddGameResult() {
        viewModel.games.observe(viewLifecycleOwner , androidx.lifecycle.Observer { games ->
            this@GameBacklogFragment.games.clear()
            this@GameBacklogFragment.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu , menuInflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_backlog, menu)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
        activity?.title = "Game Backlog"

        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }



    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete_all -> {
            val backlogTemp = arrayListOf<Game>()
            backlogTemp.addAll(games)
            viewModel.deleteAll()
            val snackbar = Snackbar.make(rvBacklog,
                R.string.delete_all,
                Snackbar.LENGTH_SHORT)
            snackbar.setAction(R.string.undo_string, UndoClearBacklog(backlogTemp))
            snackbar.show()
            true
        } else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private inner class UndoClearBacklog(val backlogArr: ArrayList<Game>): View.OnClickListener {
        override fun onClick(v: View) {
            for (game in backlogArr) {
                viewModel.insertGame(game)
            }
        }
    }


    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                games.removeAt(position)
                gameAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }


}