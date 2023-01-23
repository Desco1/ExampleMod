package com.desco.examplemod.events.packet

import com.desco.examplemod.ExampleMod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent

object PacketEvent {

    @SubscribeEvent
    fun onJoinServer(event: FMLNetworkEvent.ClientConnectedToServerEvent) {
        event.manager.channel().pipeline().addAfter(
            "fml:packet_handler",
            "${ExampleMod.MODID}_packet_handler",
            CustomChannelDuplexHandler()
        )
    }
}