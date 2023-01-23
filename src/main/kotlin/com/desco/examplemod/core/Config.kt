package com.desco.examplemod.core

import com.desco.examplemod.ExampleMod
import gg.essential.vigilance.Vigilant
import java.io.File

object Config: Vigilant(File("./config/${ExampleMod.MODID}config.toml")) {

    init {
        initialize()
    }
}