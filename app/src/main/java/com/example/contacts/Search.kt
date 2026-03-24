package com.example.contacts

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    navController: NavController, contacts: MutableList<Contact>
) {
    val context = LocalContext.current
    var filterContact by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedContact by remember { mutableStateOf<Contact?>(null) }
    var newText by remember { mutableStateOf("") }
    val search = contacts.filter {
        it.name.contains(filterContact, ignoreCase = true) || it.phoneNumber.toString()
            .contains(filterContact, ignoreCase = true) || it.city.contains(
            filterContact, ignoreCase = true
        )
    }
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Конткатҳо", fontSize = 45.sp) })
    }, floatingActionButton = {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FloatingActionButton(
                onClick = { navController.navigate("AddNewContacts") }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
        {
            TextField(
                value = filterContact,
                onValueChange = { filterContact = it },
                placeholder = { Text("Ҷустуҷӯ", color = Color.Gray) },
                shape = RoundedCornerShape(50.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (search.isNotEmpty() && filterContact.isEmpty() || search.isEmpty() || filterContact.isBlank()) {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Контакт ёфт нашуд!", color = Color.Gray, fontSize = 20.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.background(Color(0xFF2C0B0B))
                ) {
                    items(search) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    navController.navigate(
                                        "PrintThisContact/${it.name}/${it.phoneNumber}/${it.city}/${it.email}/${it.id}"
                                    )
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(
                                    text = it.name,
                                    fontSize = 22.sp,
                                    color = Color.White,
                                )
                                Text(
                                    text = it.phoneNumber.toString(),
                                    fontSize = 16.sp,
                                    color = Color.Gray,
                                )
                            }
                            IconButton(onClick = {
                                if (it.name.isNotEmpty()) {
                                    newText = it.name
                                } else {
                                    newText = it.phoneNumber.toString()
                                }
                                selectedContact = it
                                showDialog = true
                            }
                            )
                            {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.Red,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        if (showDialog && selectedContact != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Огоҳӣ") },
                text = { Text("Шумо дар ҳақикат контакти ($newText) - ро тоза кардан мехохед?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            Toast.makeText(
                                context, "Контакти $newText тоза шуд✅", Toast.LENGTH_SHORT
                            ).show()
                            contacts.remove(selectedContact)
                            showDialog = false
                        }
                    )
                    {
                        Text(
                            text = "Ҳа", color = Color.Red
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                        }) {
                        Text("Не")
                    }
                }
            )
        }
    }
}
