package io.github.ch8n.compose97.routes.desktop

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ComponentContext
import io.github.ch8n.compose97.R
import io.github.ch8n.compose97.navigation.DecomposeComponent
import io.github.ch8n.compose97.ui.components.windowscaffold.StatusBarProps
import io.github.ch8n.compose97.ui.components.windowscaffold.WindowAddressProps
import io.github.ch8n.compose97.ui.components.windowscaffold.WindowProps
import io.github.ch8n.compose97.ui.components.windowscaffold.WindowScaffold


class DesktopComponent(
    componentContext: ComponentContext,
) : DecomposeComponent(componentContext) {

    private val desktopItems = listOf(
        DesktopItemProps(
            iconResId = R.drawable.my_computer_32x32,
            itemName = "My Computer",
        ),
        DesktopItemProps(
            iconResId = R.drawable.recycle_bin_32x32,
            itemName = "Recycle Bin",
        ),
        DesktopItemProps(
            iconResId = R.drawable.my_documents_folder_32x32,
            itemName = "My Documents",
        ),
        DesktopItemProps(
            iconResId = R.drawable.internet_explorer_32x32,
            itemName = "Internet\nExplorer",
        ),
        DesktopItemProps(
            iconResId = R.drawable.notepad_32x32,
            itemName = "Notepad",
        ),
    )

    @Composable
    override fun render() {
        Desktop(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            desktopItemItems = desktopItems
        )
    }
}


@Composable
fun Desktop(
    modifier: Modifier,
    desktopItemItems: List<DesktopItemProps>
) {
    val (isWindowOpen, setWindowOpen) = remember { mutableStateOf(false) }
    val (currentWindow, setCurrentWindow) = remember { mutableStateOf(WindowProps.NoWindow) }

    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            desktopItemItems.forEach { desktopItem ->
                Spacer(modifier = Modifier.size(12.dp))
                DesktopItem(
                    itemProps = desktopItem,
                    onItemClicked = {
                        setWindowOpen(true)
                        setCurrentWindow(
                            WindowProps.NoWindow.copy(
                                statusBar = StatusBarProps(
                                    title = desktopItem.itemName,
                                    mainIcon = desktopItem.iconResId
                                ),
                                addressBar = WindowAddressProps(
                                    iconRes = desktopItem.iconResId,
                                    path = "~/${desktopItem.itemName}",
                                    name = desktopItem.itemName,
                                ),
                                isMinimised = false,
                                isMaximised = false
                            )
                        )
                    }
                )
            }
        }

        if (isWindowOpen) {
            WindowScaffold(
                props = currentWindow,
                onMinimiseClicked = {
                    setCurrentWindow(currentWindow.copy(isMinimised = true))
                },
                onMaximiseClicked = {
                    setCurrentWindow(
                        currentWindow.copy(
                            isMaximised = !currentWindow.isMaximised
                        )
                    )
                },
                onCloseClicked = { setWindowOpen(false) }
            ) {

            }
        }
    }
}

@Preview
@Composable
fun DesktopPreview() {
    Desktop(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        desktopItemItems = listOf(
            DesktopItemProps(
                iconResId = R.drawable.my_computer_32x32,
                itemName = "My Computer",

                ),
            DesktopItemProps(
                iconResId = R.drawable.recycle_bin_32x32,
                itemName = "Recycle Bin",

                ),
            DesktopItemProps(
                iconResId = R.drawable.my_documents_folder_32x32,
                itemName = "My Documents",

                ),
            DesktopItemProps(
                iconResId = R.drawable.internet_explorer_32x32,
                itemName = "Internet\nExplorer",

                ),
            DesktopItemProps(
                iconResId = R.drawable.notepad_32x32,
                itemName = "Notepad",

                ),
        )
    )
}
