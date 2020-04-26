package com.foreknowledge.photomemo2.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foreknowledge.photomemo2.EXTRA_MEMO_ID
import com.foreknowledge.photomemo2.R
import com.foreknowledge.photomemo2.adapter.MemoRecyclerAdapter
import com.foreknowledge.photomemo2.base.BaseActivity
import com.foreknowledge.photomemo2.databinding.ActivityMainBinding
import com.foreknowledge.photomemo2.listener.OnItemSingleClickListener
import com.foreknowledge.photomemo2.model.data.Memo
import com.foreknowledge.photomemo2.viewmodel.MainViewModel

@Suppress("UNUSED_PARAMETER")
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
	private val viewModel by lazy {
		ViewModelProvider(this).get(MainViewModel::class.java)
	}

	private val memoRecyclerAdapter = MemoRecyclerAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.lifecycleOwner = this@MainActivity
		binding.memoList.apply {
			layoutManager = LinearLayoutManager(this@MainActivity)
			adapter = memoRecyclerAdapter.apply {
				onClickListener = setItemClickListener()
			}
		}

		subscribeUI()
	}

	override fun onResume() {
		super.onResume()

		viewModel.initMemoList()
	}

	private fun setItemClickListener() = object: OnItemSingleClickListener<Memo>() {
		override fun onSingleClick(item: Memo) {
			startActivity(
					Intent(this@MainActivity, DetailActivity::class.java)
							.apply { putExtra(EXTRA_MEMO_ID, item.id) }
			)
		}
	}

	private fun subscribeUI() {
		with (viewModel) {
			memoList.observe(this@MainActivity, Observer {
				memoRecyclerAdapter.replaceItems(it)
				binding.isEmptyList = it.size == 0
			})
		}
	}

	fun createMemo(view: View) =
		startActivity(Intent(this, CreateActivity::class.java))
}
