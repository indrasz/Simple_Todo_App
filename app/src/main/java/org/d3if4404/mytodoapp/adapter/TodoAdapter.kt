package org.d3if4404.mytodoapp.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.d3if4404.mytodoapp.databinding.ItemListTodoBinding
import org.d3if4404.mytodoapp.model.Todo

class TodoAdapter( private val todos: MutableList<Todo>)
    : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {



    inner class TodoViewHolder(private val binding: ItemListTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo){
            with(binding){

                binding.apply {
                    tvTodoTitle.text = todo.title
                    cbDone.isChecked = todo.isChecked

                    toggleStrikeThrough(tvTodoTitle, todo.isChecked)
                    cbDone.setOnCheckedChangeListener { _, isChecked ->
                        toggleStrikeThrough(binding.tvTodoTitle, isChecked)
                        todo.isChecked = !todo.isChecked
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder(
            ItemListTodoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoAdapter.TodoViewHolder, position: Int) {
       holder.bind(todos[position])
    }

    override fun getItemCount(): Int = todos.size
}
