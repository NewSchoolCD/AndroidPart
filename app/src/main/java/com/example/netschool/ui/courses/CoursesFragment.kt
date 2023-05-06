package com.example.netschool.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.netschool.adapters.CourseRVAdapter
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

    lateinit var recyclerView:RecyclerView
    lateinit var rvAdapter:CourseRVAdapter
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
            viewModel.getTutor()
        }

        viewModel.apply {
            subjects.value?.let {
                subject.addAll(it)
            }
            grades.value?.let {
                grade.addAll(it)
            }
        }
        setupRecyclerView()
        provideObversers()

        subjectAdapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.select_dialog_item_material, subject)
        gradeAdapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.select_dialog_item_material, grade)
        binding.subjectinput.apply {
            setAdapter( subjectAdapter)
            var job:Job? = null
            onItemClickListener =  AdapterView.OnItemClickListener { adapterView, view, i, l ->
                job = null
                job = MainScope().launch {
                    adapterView.getItemAtPosition(i).toString().apply {
                        viewModel.getGrades(this)
                        viewModel.getTutor(this)
                    }
                }
                binding.classinput.isEnabled = true
                binding.classinput.clearListSelection()
                binding.classinput.setText("", false)
            }
        }

        binding.classinput.apply {
            setAdapter(gradeAdapter)
            var job:Job? = null
            onItemClickListener =  AdapterView.OnItemClickListener { adapterView, view, i, l ->
                job = null
                job = MainScope().launch {
                    viewModel.getTutor(binding.subjectinput.text.toString(), adapterView.getItemAtPosition(i).toString().toInt())
                }
            }
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupRecyclerView(){
        recyclerView = binding.rvCources
        rvAdapter = CourseRVAdapter()
        recyclerView.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    private fun provideObversers(){
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
        viewModel.tutors.observe(viewLifecycleOwner){
            rvAdapter.diffList.submitList(it)
        }
    }
}