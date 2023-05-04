package com.example.netschool.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.netschool.databinding.FragmentCoursesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoursesFragment : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
    private val viewModel:CoursesViewModel by viewModels()
    private var subject:MutableList<String> = mutableListOf()
    private var grade:MutableList<String> = mutableListOf()

    private lateinit var subjectAdapter:ArrayAdapter<String>
    private lateinit var gradeAdapter:ArrayAdapter<String>
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainScope().launch {
            viewModel.getCourses()
        }

        viewModel.apply {
            subjects.value?.let {
                subject.addAll(it)
            }
            grades.value?.let {
                grade.addAll(it)
            }
        }

        provideObversers()

        subjectAdapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.select_dialog_item_material, subject)
        gradeAdapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.select_dialog_item_material, grade)
        binding.subjectinput.apply {
            setAdapter( subjectAdapter)
            var job:Job? = null
            onItemClickListener =  AdapterView.OnItemClickListener { adapterView, view, i, l ->
                job = null
                job = MainScope().launch {
                    viewModel.getGrades(adapterView.getItemAtPosition(i).toString())
                }
                binding.classinput.isEnabled = true
            }
        }

        binding.classinput.apply {
            setAdapter(gradeAdapter)
            var job:Job? = null
            onItemClickListener =  AdapterView.OnItemClickListener { adapterView, view, i, l ->
                job = null
//                job = MainScope().launch {
//                    viewModel.getGrades(adapterView.getItemAtPosition(i).toString())
//                }
                Toast.makeText(context, "GRGRG", Toast.LENGTH_SHORT).show()
            }
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupRecyclerView(){

    }

    fun provideObversers(){
        viewModel.subjects.observe(viewLifecycleOwner) {
            subject.clear()
            subject.addAll(it)
            subjectAdapter.notifyDataSetChanged()
        }
        viewModel.grades.observe(viewLifecycleOwner) {
            grade.clear()
            grade.addAll(it)
            gradeAdapter.notifyDataSetChanged()
        }
    }
}