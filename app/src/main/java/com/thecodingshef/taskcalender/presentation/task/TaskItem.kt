package com.thecodingshef.taskcalender.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.thecodingshef.taskcalender.R
import com.thecodingshef.taskcalender.model.response.TaskModel
import com.thecodingshef.taskcalender.presentation.commonComposables.SpacerHeight
import com.thecodingshef.taskcalender.util.DateFormatter
import com.thecodingshef.taskcalender.util.DateFormatter.DAY_DD_MMM_YYYY
import com.thecodingshef.taskcalender.util.DateFormatter.DD_MMM_YY
import com.thecodingshef.taskcalender.util.DateFormatter.DD_MM_YY

@Composable
fun TaskItem(task: TaskModel, onDelete: (taskId:Int) -> Unit) {

    Card(
        shape = RoundedCornerShape(8.dp), elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp))
                .height(IntrinsicSize.Min)
        ) {
            Divider(
                color = Color.Blue, modifier = Modifier
                    .fillMaxHeight()
                    .width(8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = task.taskDetail.title ?: "",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily(Font(R.font.nunito_medium)), color = Color.Black
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = "delete icon",
                        modifier = Modifier.clickable { onDelete(task.taskId) })
                }

                SpacerHeight(dp = 4.dp)
                task.taskDetail.date?.let { date ->
                    Text(
                        text = DateFormatter.getFormattedDate(
                            date, inputFormat = DD_MM_YY, desiredDateFormat = DAY_DD_MMM_YYYY
                        ), style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily(
                                Font(R.font.nunito_medium)
                            ), fontStyle = FontStyle.Italic
                        ), modifier = Modifier.align(Alignment.Start)
                    )
                }
                SpacerHeight(dp = 12.dp)
                Text(
                    text = task.taskDetail.description ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily(Font(R.font.nunito_medium)))
                )
                task.taskDetail.created?.let { date ->
                    Text(
                        text = DateFormatter.getFormattedDate(
                            date, inputFormat = DD_MM_YY, desiredDateFormat = DD_MMM_YY
                        ), style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = FontFamily(
                                Font(R.font.nunito_medium)
                            )
                        ), modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }

    }

}