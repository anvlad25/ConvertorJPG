package com.example.convertorjpg.convertor

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.convertorjpg.R
import com.example.convertorjpg.databinding.FragmentConvertBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ConvertFragment : MvpAppCompatFragment(), ConvertView {
    private val binding: FragmentConvertBinding by viewBinding()

    private val presenter: ConvertPresenter by moxyPresenter {
        ConvertPresenter(
            Uri.EMPTY
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_convert, container, false)
    }

    private var converterDisposable: CompositeDisposable? = null

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { uri ->
                val bitmap = MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver,
                    uri.data?.data
                )
                binding.imageConv.setImageBitmap(bitmap)
                convertPic(
                    bitmap,
                    getPathFromUri(uri.data?.data!!)
                )
            }

        binding.buttonSelect.setOnClickListener {
            getContent.launch(Intent(Intent.ACTION_PICK).setType("image/*"))

        }

    }

    private fun getPathFromUri(uri: Uri): String {
        var res = "not found"
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(projection[0])
            columnIndex.let {
                res = cursor.getString(columnIndex)
            }
            cursor.close()
        }
        return res
    }

    private fun convertPic(imagePicked: Bitmap, pathImagePicked: String) {
        converterDisposable = CompositeDisposable()
        converterDisposable?.add(
            ImageConverter.convertJpgToPng(imagePicked, pathImagePicked)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Toast.makeText(context, "${it.first} converted to png.", Toast.LENGTH_LONG)
                        .show()
                }, {})
        )
    }

    companion object {
        fun newInstance() = ConvertFragment()
    }
}