/*
 * Copyright (C) 2017  TextMd
 *
 * This file is part of TextMd.
 *
 * TextMd is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.desive.utilities.constants;

import java.time.LocalDateTime;

import static java.time.Duration.between;

/*
 Created by Jack DeSive on 11/11/2017 at 2:55 AM
*/
public class Timer {

    private LocalDateTime startDateTime, endDateTime;

    // Calling start will reset if the timer has already been started
    public void start() {
        startDateTime = LocalDateTime.now();
        endDateTime = LocalDateTime.now();
    }

    public long end() {
        endDateTime = LocalDateTime.now();
        return between(startDateTime, endDateTime).toMillis();
    }



}
