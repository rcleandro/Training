package br.com.leandro.training.ui.training

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.leandro.training.core.database.entity.Training
import br.com.leandro.training.databinding.TrainingItemBinding
import br.com.leandro.training.utils.timestampToString

/**
 * RecyclerView adapter for displaying a list of trainings.
 *
 * The UI is based on the [TrainingItemBinding].
 * We use the [Training] as a model for the binding.
 */
class TrainingListAdapter(
    private val viewModel: TrainingListViewModel
) : RecyclerView.Adapter<TrainingListAdapter.ViewHolder>() {

    private val asyncListDiffer: AsyncListDiffer<Training> = AsyncListDiffer(this, DiffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TrainingItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(asyncListDiffer.currentList[position])
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    fun getTraining(position: Int): Training {
        notifyItemChanged(position)
        return asyncListDiffer.currentList[position]
    }

    fun updateTrainings(trainings: List<Training>) {
        asyncListDiffer.submitList(trainings)
    }

    class ViewHolder(
        private val binding: TrainingItemBinding,
        private val viewModel: TrainingListViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(training: Training) {
            val name = "Treino ${training.name}"
            binding.trainingName.text = name
            binding.trainingDescription.text = training.description
            binding.trainingDate.text = training.date.timestampToString()

//            binding.completeCheckBox.setOnClickListener {
//                viewModel.toggleTrainingCompleted(training.id)
//            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Training>() {

        override fun areItemsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Training, newItem: Training): Boolean {
            return oldItem.description == newItem.description
        }
    }
}