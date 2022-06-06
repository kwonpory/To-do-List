package com.example.todolist

import android.os.Bundle
import android.widget.CheckBox
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.theme.ToDoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                CheckList()
            }
        }
    }
}

// 전체 화면 구성
@Composable
fun CheckList() {
    var count by rememberSaveable { mutableStateOf(0) } // Check Box 개수
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        modifier = Modifier
            .clickable( // 화면 클릭 가능
                interactionSource = interactionSource,
                indication = null   // click effect null
            ) { focusManager.clearFocus() },    // click 시 focus 제거
        // App Bar
        topBar = {
            TopAppBar(
                title = { Text("To do List") },
                backgroundColor = Color.Unspecified,
                contentColor = MaterialTheme.colors.onSecondary,
                elevation = 0.dp
            )
        },
        // Floating Btn
        floatingActionButton = {
            FloatingBtn(
                onFloatingClicked = { count += 1 }
            )},
        floatingActionButtonPosition = FabPosition.End
    ) {
        // Check Box
        LazyColumn {
            items(count = count) {
                CheckBox()
            }
        }
    }
}

@Composable
fun FloatingBtn(onFloatingClicked: () -> Unit) {
    FloatingActionButton(
        onClick = onFloatingClicked
    ) {
        // floating btn icon (+)
        Icon(Icons.Filled.Add, null, tint = Color.White)
    }
}

@Composable
fun CheckBox() {
    var checked by rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val iconColor: Color
    val backgroundColor: Color
    val textColor: Color

    // check 여부에 따른 색상 설정
    if (checked) {
        backgroundColor = Color.Black
        iconColor = Color.White
        textColor = Color.White
    } else {
        backgroundColor = Color.White
        iconColor = Color.LightGray
        textColor = Color.Black
    }

    // Check Box (TextField)
    Column {
        Card(
            modifier = Modifier.padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester = focusRequester),
                value = text,
                onValueChange = { text = it },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = backgroundColor,
                    cursorColor = textColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = textColor
                ),
                leadingIcon = { // Check Icon
                    IconButton(onClick = { checked = !checked }) {  // icon click event
                        Icon(
                            tint = iconColor,
                            imageVector = Icons.Outlined.Check,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
    // TextField focus request
//    LaunchedEffect(Unit) {
//        focusRequester.requestFocus()
//    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ToDoListTheme {
        CheckList()
    }
}