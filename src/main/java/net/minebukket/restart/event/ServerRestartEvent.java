package net.minebukket.restart.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*******************************************************************************
 *  Copyright (C) SparkNetwork - All Rights Reserved
 *   * Unauthorized copying of this file, via any medium is strictly prohibited
 *   * Proprietary and confidential
 *   * Written by Gilberto Garcia <gilbertodamian14@gmail.com>, May 2018
 *
 ******************************************************************************/
@RequiredArgsConstructor
public class ServerRestartEvent extends Event {

    @Getter
    private final Server server;

    private static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlersList() {
        return handlerList;
    }
}
