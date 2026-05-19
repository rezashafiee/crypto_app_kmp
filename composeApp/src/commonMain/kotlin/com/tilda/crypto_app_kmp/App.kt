package com.tilda.crypto_app_kmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tilda.crypto.presentation.coin_detail.CoinDetailScreen
import com.tilda.crypto.presentation.coin_list.CoinListScreen
import com.tilda.crypto.presentation.coin_list.CoinListViewModel
import com.tilda.feature.news.presentation.news.CryptoNewsScreen
import com.tilda.feature.news.presentation.news.CryptoNewsViewModel
import org.koin.compose.viewmodel.koinViewModel

private enum class TopLevelDestination(
    val label: String,
    val iconLabel: String
) {
    Coins(
        label = "Coins",
        iconLabel = "$"
    ),
    News(
        label = "News",
        iconLabel = "N"
    )
}

@Composable
@Preview
fun App() {
    MaterialTheme {
        var selectedDestination by rememberSaveable {
            mutableStateOf(TopLevelDestination.Coins)
        }

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            val useNavigationRail = maxWidth >= 720.dp

            Scaffold(
                bottomBar = {
                    if (!useNavigationRail) {
                        CryptoBottomNavigation(
                            selectedDestination = selectedDestination,
                            onDestinationSelected = { selectedDestination = it }
                        )
                    }
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                if (useNavigationRail) {
                    Row(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                    ) {
                        CryptoNavigationRail(
                            selectedDestination = selectedDestination,
                            onDestinationSelected = { selectedDestination = it }
                        )
                        VerticalDivider(
                            modifier = Modifier.fillMaxHeight(),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        DestinationContent(
                            selectedDestination = selectedDestination,
                            modifier = Modifier.weight(1f)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        DestinationContent(
                            selectedDestination = selectedDestination,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CryptoBottomNavigation(
    selectedDestination: TopLevelDestination,
    onDestinationSelected: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        TopLevelDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = destination == selectedDestination,
                onClick = { onDestinationSelected(destination) },
                icon = { DestinationIcon(destination) },
                label = { Text(destination.label) }
            )
        }
    }
}

@Composable
private fun CryptoNavigationRail(
    selectedDestination: TopLevelDestination,
    onDestinationSelected: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        TopLevelDestination.entries.forEach { destination ->
            NavigationRailItem(
                selected = destination == selectedDestination,
                onClick = { onDestinationSelected(destination) },
                icon = { DestinationIcon(destination) },
                label = { Text(destination.label) }
            )
        }
    }
}

@Composable
private fun DestinationIcon(destination: TopLevelDestination) {
    Text(
        text = destination.iconLabel,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun DestinationContent(
    selectedDestination: TopLevelDestination,
    modifier: Modifier = Modifier
) {
    when (selectedDestination) {
        TopLevelDestination.Coins -> CoinContent(modifier = modifier)
        TopLevelDestination.News -> NewsContent(modifier = modifier)
    }
}

@Composable
private fun CoinContent(
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BoxWithConstraints(modifier = modifier) {
        val useTwoPane = maxWidth >= 720.dp

        if (useTwoPane) {
            Row(modifier = Modifier.fillMaxSize()) {
                CoinListScreen(
                    uiState = state,
                    onItemClick = viewModel::onCoinClicked,
                    onFavoriteClick = viewModel::onFavoriteClick,
                    onFavoritesFilterChanged = viewModel::onFavoritesFilterChanged,
                    modifier = Modifier.width(360.dp)
                )
                VerticalDivider()
                CoinDetailScreen(
                    state = state,
                    showBackButton = false,
                    onFavoriteClick = viewModel::onFavoriteClick,
                    modifier = Modifier.weight(1f)
                )
            }
        } else if (state.selectedCoin != null) {
            CoinDetailScreen(
                state = state,
                showBackButton = true,
                onBackClick = viewModel::clearSelectedCoin,
                onFavoriteClick = viewModel::onFavoriteClick,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            CoinListScreen(
                uiState = state,
                onItemClick = viewModel::onCoinClicked,
                onFavoriteClick = viewModel::onFavoriteClick,
                onFavoritesFilterChanged = viewModel::onFavoritesFilterChanged,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun NewsContent(
    modifier: Modifier = Modifier,
    viewModel: CryptoNewsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CryptoNewsScreen(
        state = state,
        onArticleClick = viewModel::onArticleClicked,
        onBackClick = viewModel::clearSelectedArticle,
        modifier = modifier
    )
}
