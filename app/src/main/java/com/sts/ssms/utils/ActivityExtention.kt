package com.sts.ssms.utils

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sts.ssms.R
import org.joda.time.LocalDate


const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 0x0201

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transact {
        add(frameId, fragment)
    }
}

fun AppCompatActivity.setupActionBar(toolbar: Toolbar, action: ActionBar.() -> Unit) {
    setSupportActionBar(toolbar)
    supportActionBar?.run {
        action()
    }
}

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

fun Activity.checkStoragePermissionAndDownload(fileUrl: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (hasPermission()) downloadFile(fileUrl)
        else requestRationalPermissionShowInfoDialog()
    } else downloadFile(fileUrl)
}

fun Activity.hasPermission(): Boolean {
    val permission =
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    return permission == PackageManager.PERMISSION_GRANTED
}

/**
 * Checks and Request WRITE_EXTERNAL_STORAGE permission
 */
@RequiresApi(Build.VERSION_CODES.M)
private fun Activity.requestPermission() =
    requestPermissions(
        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
        REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION
    )

/**
 * Checks requested permissions, and show permission info to the user if denied 1st time
 */
@RequiresApi(Build.VERSION_CODES.M)
fun Activity.requestRationalPermissionShowInfoDialog() {
    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.permission_write_storage_message))
            .setTitle(getString(R.string.title_permission_write_storage_dialog))
        builder.setPositiveButton("OK") { _, _ -> requestPermission() }
        builder.create().show()
    } else requestPermission()
}

/**
 * Send user to the App Details screen when "Never Ask Again" is checked
 */
fun AppCompatActivity.sendUserToAppSettingsForPermissionAccess() {
    val builder = AlertDialog.Builder(this)
    builder.setMessage(getString(R.string.permission_write_storage_message_1))
    builder.setPositiveButton("Go to Settings") { _, _ ->
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            val uri: Uri = Uri.fromParts("package", packageName, null)
            data = uri
            startActivity(this)
        }
    }
    builder.create().show()
}

fun Activity.downloadFile(fileUrl: String) {
    showToast(getString(R.string.document_download_msg))
    val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
    val uri: Uri = Uri.parse(fileUrl)
    val request = DownloadManager.Request(uri)
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    request.setDestinationInExternalPublicDir(
        Environment.DIRECTORY_DOWNLOADS,
        uri.lastPathSegment
    )
    downloadManager!!.enqueue(request)
}

fun AppCompatActivity.showImageDialog(imgUrl: String) {
    val builder = AlertDialog.Builder(this)
    val view = View.inflate(this, R.layout.layout_image_full_screen_dialog, null)
    view.findViewById<ImageView>(R.id.iv_full_screen_image).loadOriginalImage(imgUrl)
    builder.setView(view)
    builder.create().show()
}

fun AppCompatActivity.showDatePickerDialog(
    date: LocalDate, callback: (selectedDate: LocalDate) -> Unit
) {
    val dialog = DatePickerDialog(
        this, R.style.AppTheme_DateTimePickerTheme,
        DatePickerDialog.OnDateSetListener { _, y, m, d ->
            callback.invoke(LocalDate(y, m + 1, d))
        }, date.year, date.monthOfYear - 1, date.dayOfMonth
    )
    dialog.datePicker.minDate = (System.currentTimeMillis() - 1000)
    dialog.show()
}
