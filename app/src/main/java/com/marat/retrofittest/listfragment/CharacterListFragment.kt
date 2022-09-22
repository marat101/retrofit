package com.marat.retrofittest.listfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marat.retrofittest.R
import com.marat.retrofittest.databinding.FragmentCharacterListBinding
import com.marat.retrofittest.detailinfotfragment.DetailInformationFragment
import com.marat.retrofittest.model.Result

class CharacterListFragment : Fragment(R.layout.fragment_character_list) {

    private lateinit var binding: FragmentCharacterListBinding
    private var adapter = RikAdapter(onClick = { clickOnItem(it) })

    companion object {
        fun newInstance() = CharacterListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(ListFragmentViewModel::class.java)
        viewModel.getCharacterList()
        viewModel.characterList.observe(viewLifecycleOwner) { list ->
            list.body()?.let { adapter.setData(it.results) }
        }
        binding.rcView.adapter = adapter

        return binding.root
    }

    private fun clickOnItem(item: Result) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, DetailInformationFragment.newInstance(item))
            .addToBackStack(CharacterListFragment::class.java.name).commit()
    }
}