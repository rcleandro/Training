package br.com.leandro.training.ui.training

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.training.R
import br.com.leandro.training.core.database.entity.Exercise
import br.com.leandro.training.databinding.ChooseExerciseItemBinding
import com.squareup.picasso.Picasso

/**
 * RecyclerView adapter for displaying a list of exercises.
 *
 * The UI is based on the [ChooseExerciseItemBinding].
 * We use the [Exercise] as a model for the binding.
 */
class ChooseExerciseListAdapter(
    private val selectedExercises: List<Exercise>? = listOf()
) : RecyclerView.Adapter<ChooseExerciseListAdapter.ViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<Exercise> = AsyncListDiffer(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ChooseExerciseItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, selectedExercises)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun getItemViewType(position: Int): Int = position

    fun getSelectedExercises(): List<Exercise> {
        return asyncListDiffer.currentList.filter { it.selected == true }
    }

    fun updateExercises(exercises: List<Exercise>) {
        asyncListDiffer.submitList(exercises)
    }

    class ViewHolder(
        private val binding: ChooseExerciseItemBinding,
        private val selectedExercises: List<Exercise>?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            val name = "Exercício ${exercise.name}"
            binding.exerciseItemName.text = name
            binding.exerciseItemComments.text = exercise.comments

            val selected = selectedExercises?.find { it.name == exercise.name }
            selected?.let { exercise.selected = true }

            binding.checkbox.isChecked = exercise.selected == true

            Picasso.get()
                .load(exercise.image)
                .placeholder(R.drawable.ic_exercise_example)
                .into(binding.imageView)

            binding.root.setOnClickListener {
                binding.checkbox.isChecked = !binding.checkbox.isChecked
                exercise.selected = binding.checkbox.isChecked
            }

            binding.checkbox.setOnClickListener {
                exercise.selected = binding.checkbox.isChecked
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Exercise>() {

        override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
            return oldItem.selected == newItem.selected
        }
    }
}