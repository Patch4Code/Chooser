/*
 * Copyright (C) 2025 UrAvgCode
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *
 * @author UrAvgCode
 * @description SettingsScreen is the settings screen of the application.
 */

package com.uravgcode.chooser.composables.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.uravgcode.chooser.composables.settings.RestartDialog
import com.uravgcode.chooser.composables.settings.SettingsRowPercentSlider
import com.uravgcode.chooser.composables.settings.SettingsRowSwitch
import com.uravgcode.chooser.composables.settings.SettingsRowTimeSlider
import com.uravgcode.chooser.composables.settings.SettingsSeparator
import com.uravgcode.chooser.composables.settings.SettingsTopAppBar
import com.uravgcode.chooser.utilities.SettingsManager

@Composable
fun SettingsScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val showRestartDialog = remember { mutableStateOf(false) }

    val isSoundEnabled = remember { mutableStateOf(SettingsManager.soundEnabled) }
    val isVibrationEnabled = remember { mutableStateOf(SettingsManager.vibrationEnabled) }
    val isEdgeToEdgeEnabled = remember { mutableStateOf(SettingsManager.edgeToEdgeEnabled) }
    val circleSizeFactor = remember { mutableFloatStateOf(SettingsManager.circleSizeFactor) }

    val circleLifetime = remember { mutableLongStateOf(SettingsManager.circleLifetime) }
    val groupCircleLifetime = remember { mutableLongStateOf(SettingsManager.groupCircleLifetime) }
    val orderCircleLifetime = remember { mutableLongStateOf(SettingsManager.orderCircleLifetime) }

    RestartDialog(
        showRestartDialog = showRestartDialog.value,
        onDismiss = { showRestartDialog.value = false },
        context = context
    )

    Scaffold(
        topBar = {
            SettingsTopAppBar(onNavigateBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp)
        ) {
            SettingsSeparator("General Settings", false)

            SettingsRowSwitch(
                title = "Enable Sound",
                isChecked = isSoundEnabled.value,
                onCheckedChange = { isChecked ->
                    isSoundEnabled.value = isChecked
                    SettingsManager.soundEnabled = isChecked
                }
            )

            SettingsRowSwitch(
                title = "Enable Vibration",
                isChecked = isVibrationEnabled.value,
                onCheckedChange = { isChecked ->
                    isVibrationEnabled.value = isChecked
                    SettingsManager.vibrationEnabled = isChecked
                }
            )

            SettingsSeparator("Display Settings")

            SettingsRowSwitch(
                title = "Enable Edge-to-Edge",
                isChecked = isEdgeToEdgeEnabled.value,
                onCheckedChange = { isChecked ->
                    isEdgeToEdgeEnabled.value = isChecked
                    SettingsManager.edgeToEdgeEnabled = isChecked
                    showRestartDialog.value = true
                }
            )

            SettingsRowPercentSlider(
                title = "Circle Size",
                value = circleSizeFactor.floatValue,
                onValueChange = { sliderValue ->
                    circleSizeFactor.floatValue = sliderValue
                    SettingsManager.circleSizeFactor = sliderValue
                },
                valueRange = 0.5f..1.5f,
                steps = 9
            )

            SettingsSeparator("Circle Lifetimes")

            SettingsRowTimeSlider(
                title = "Circle Lifetime",
                value = circleLifetime.longValue,
                onValueChange = { sliderValue ->
                    circleLifetime.longValue = sliderValue
                    SettingsManager.circleLifetime = sliderValue
                },
                valueRange = 0L..3000L,
                steps = 5,
            )

            SettingsRowTimeSlider(
                title = "Group Circle Lifetime",
                value = groupCircleLifetime.longValue,
                onValueChange = { sliderValue ->
                    groupCircleLifetime.longValue = sliderValue
                    SettingsManager.groupCircleLifetime = sliderValue
                },
                valueRange = 0L..3000L,
                steps = 5,
            )

            SettingsRowTimeSlider(
                title = "Order Circle Lifetime",
                value = orderCircleLifetime.longValue,
                onValueChange = { sliderValue ->
                    orderCircleLifetime.longValue = sliderValue
                    SettingsManager.orderCircleLifetime = sliderValue
                },
                valueRange = 0L..3000L,
                steps = 5,
            )
        }
    }
}
