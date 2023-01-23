package com.desco.examplemod.events.packet

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import net.minecraft.network.Packet
import net.minecraftforge.common.MinecraftForge

class CustomChannelDuplexHandler: ChannelDuplexHandler() {

    override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
        if (msg is Packet<*>) {
            val event = PacketReceivedEvent(msg)
            MinecraftForge.EVENT_BUS.post(event)

            if (!event.isCanceled) {
                ctx?.fireChannelRead(event.packet)
            }
        }
    }

    override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
        if (msg is Packet<*>) {
            val event = PacketSentEvent(msg)
            MinecraftForge.EVENT_BUS.post(event)

            if (!event.isCanceled) {
                ctx?.write(event.packet, promise)
            }
        }
    }
}