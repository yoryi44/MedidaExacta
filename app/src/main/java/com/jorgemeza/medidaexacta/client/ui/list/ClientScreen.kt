package com.jorgemeza.medidaexacta.client.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.medidaexacta.client.ui.list.components.ClientItemComponent
import com.jorgemeza.ui.components.AlertDialogComponent
import com.jorgemeza.ui.components.CircularProgresIndicatorComponent
import com.jorgemeza.ui.components.FloatingActionButtonComponent
import com.jorgemeza.ui.components.TopBarComponent
import com.jorgemeza.medidaexacta.ui.theme.Danger
import com.jorgemeza.medidaexacta.ui.theme.LigthGray

@Composable
fun ClientScreen(
    clientViewModel: ClientViewModel = hiltViewModel(),
    onDetail: (String?) -> Unit
) {

    val state = clientViewModel.state

    Scaffold(
        floatingActionButton = {
            com.jorgemeza.ui.components.FloatingActionButtonComponent {
                onDetail(null)
            }
        }
    ) { innerPading ->

        Box (modifier = Modifier.fillMaxSize()){

            Column(
                modifier = Modifier
                    .padding()
                    .fillMaxSize()
                    .background(LigthGray)
                    .fillMaxSize()
            ) {

                if (!state.idClientDelete.isNullOrBlank()) {
                    com.jorgemeza.ui.components.AlertDialogComponent(
                        icon = Icons.Default.Warning,
                        title = "Are you sure you want to delete the client?",
                        message = "Once deleted, you will not be able to recover the information.",
                        iconColor = com.jorgemeza.medidaexacta.ui.theme.Warning,
                        onDismiss = {
                            clientViewModel.onEvent(ClientEvent.OnDismissDialog)
                        },
                        onConfirm = {
                            clientViewModel.onEvent(ClientEvent.OnConfirmDialog)
                        }
                    )
                }

                if(!state.error.isNullOrBlank()) {
                    com.jorgemeza.ui.components.AlertDialogComponent(
                        icon = Icons.Default.Warning,
                        title = "Error",
                        message = state.error,
                        iconColor = Danger,
                        onDismiss = {
                            clientViewModel.onEvent(ClientEvent.OnDismissDialog)
                        },
                        onConfirm = {
                            clientViewModel.onEvent(ClientEvent.OnDismissDialog)
                        }
                    )
                }

                com.jorgemeza.ui.components.TopBarComponent(value = state.searchQuery,
                    onChange = {
                        clientViewModel.onEvent(ClientEvent.OnSearchQueryChange(it))
                    },
                    onSearch = {
                        clientViewModel.onEvent(ClientEvent.OnSearchClient)
                    })

                if(state.isLoading)
                {
                    com.jorgemeza.ui.components.CircularProgresIndicatorComponent()
                }
                else
                {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(state.clients) { item ->
                            ClientItemComponent(
                                client = item,
                                onClickItem = {
                                    onDetail(it)
                                },
                                onLongClick = {
                                    clientViewModel.onEvent(ClientEvent.OnDeleteClient(item.id))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}