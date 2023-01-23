package com.desco.examplemod

import com.desco.examplemod.commands.ExampleCommand
import com.desco.examplemod.core.Config
import com.desco.examplemod.events.packet.PacketEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

@Mod(
    modid = ExampleMod.MODID,
    version = ExampleMod.VERSION,
    name = ExampleMod.NAME,
    clientSideOnly = true,
    acceptedMinecraftVersions = "[1.8.9]",
    modLanguageAdapter = "gg.essential.api.utils.KotlinAdapter"
)
object ExampleMod: CoroutineScope {
    const val NAME = "ExampleMod"
    const val MODID = "examplemod"
    const val VERSION = "1.0"

    override val coroutineContext: CoroutineContext = Executors.newFixedThreadPool(10).asCoroutineDispatcher() + SupervisorJob()

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        Config.preload()
        ExampleCommand.register()
        MinecraftForge.EVENT_BUS.register(PacketEvent)


    }
}