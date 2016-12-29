/**
 * Copyright (c) 2016-2017 Toluwanimi Salako. http://csthings.net

 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package net.csthings.antreminder.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.csthings.antreminder.entity.User;

public class SecurityUtils {
    public static final String ANONYMOUS_USER = "anonymousUser";

    public static boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.isAuthenticated() && !auth.getName().equals(ANONYMOUS_USER) && auth.getPrincipal() != null
                && auth.getPrincipal() instanceof User && ((User) auth.getPrincipal()).isAuthenticated();
    }

    public static UUID getAccountId() {
        return ((AuthenticationImpl) SecurityContextHolder.getContext().getAuthentication()).getPrincipal()
                .getAccountId();
    }

    public static String getEmail() {
        return ((AuthenticationImpl) SecurityContextHolder.getContext().getAuthentication()).getPrincipal().getEmail();
    }
}
