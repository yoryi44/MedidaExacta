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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jorgemeza.medidaexacta.R
import com.jorgemeza.medidaexacta.client.ui.list.components.ClientItemComponent
import com.jorgemeza.medidaexacta.core.ui.AlertDialogComponent
import com.jorgemeza.medidaexacta.core.ui.CircularProgresIndicatorComponent
import com.jorgemeza.medidaexacta.core.ui.FloatingActionButtonComponent
import com.jorgemeza.medidaexacta.core.ui.TopBarComponent
import com.jorgemeza.medidaexacta.ui.theme.LigthGray

@Composable
fun ClientScreen(
    clientViewModel: ClientViewModel = hiltViewModel(),
    onDetail: (String?) -> Unit
) {

    val state = clientViewModel.state

    Scaffold(
        floatingActionButton = {
            FloatingActionButtonComponent {
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
                    AlertDialogComponent(
                        icon = Icons.Default.Warning,
                        title = stringResource(R.string.delete_client_alert_dialog),
                        message = stringResource(R.string.delete_alert_dialog),
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
                    AlertDialogComponent(
                        icon = Icons.Default.Warning,
                        title = "Error",
                        message = state.error,
                        iconColor = com.jorgemeza.medidaexacta.ui.theme.Danger,
                        onDismiss = {
                            clientViewModel.onEvent(ClientEvent.OnDismissDialog)
                        },
                        onConfirm = {
                            clientViewModel.onEvent(ClientEvent.OnDismissDialog)
                        }
                    )
                }

                TopBarComponent(value = state.searchQuery,
                    onChange = {
                        clientViewModel.onEvent(ClientEvent.OnSearchQueryChange(it))
                    },
                    onSearch = {
                        clientViewModel.onEvent(ClientEvent.OnSearchClient)
                    })

                if(state.isLoading)
                {
                    CircularProgresIndicatorComponent()
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