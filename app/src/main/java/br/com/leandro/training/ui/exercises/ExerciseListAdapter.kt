package br.com.leandro.training.ui.exercises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.training.R
import br.com.leandro.training.core.database.entity.Exercise
import br.com.leandro.training.core.database.entity.Training
import br.com.leandro.training.databinding.ExerciseItemBinding
import com.squareup.picasso.Picasso

/**
 * RecyclerView adapter for displaying a list of exercises.
 *
 * The UI is based on the [ExerciseItemBinding].
 * We use the [Exercise] as a model for the binding.
 */
class ExerciseListAdapter(
    private val viewModel: ExerciseListViewModel
) : RecyclerView.Adapter<ExerciseListAdapter.ViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<Exercise> = AsyncListDiffer(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ExerciseItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    fun getExercise(position: Int): Exercise {
        notifyItemChanged(position)
        return asyncListDiffer.currentList[position]
    }

    fun updateExercises(exercises: List<Exercise>) {
        asyncListDiffer.submitList(exercises)
    }

    class ViewHolder(
        private val binding: ExerciseItemBinding,
        private val viewModel: ExerciseListViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: Exercise) {
            val name = "Exercício ${exercise.name}"
            binding.exerciseItemName.text = name
            binding.exerciseItemComments.text = exercise.comments

            Picasso.get()
                .load(exercise.image)
                .placeholder(R.drawable.ic_exercise_example)
                .into(binding.imageView)

//            binding.exerciseCheckBox.setOnClickListener {
//                viewModel.toggleExerciseCompleted(exercise.id)
//            }
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