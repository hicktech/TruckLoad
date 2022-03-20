package com.github.jw3.tl.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.jw3.tl.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.load_list_fragment.view.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoadList : Fragment() {

    private val viewModel: LoadListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.load_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerViewAdapter = RecyclerViewAdapter()

        view.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.getPaged().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }
}
