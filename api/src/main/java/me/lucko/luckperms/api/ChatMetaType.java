/*
 * This file is part of LuckPerms, licensed under the MIT License.
 *
 *  Copyright (c) lucko (Luck) <luck@lucko.me>
 *  Copyright (c) contributors
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.lucko.luckperms.api;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a type of chat meta
 *
 * @since 3.2
 */
public enum ChatMetaType {

    /**
     * Represents a prefix
     */
    PREFIX("prefix") {
        @Override
        public boolean matches(@NonNull Node node) {
            return Objects.requireNonNull(node, "node").isPrefix();
        }

        @Override
        public Map.@NonNull Entry<Integer, String> getEntry(@NonNull Node node) {
            return Objects.requireNonNull(node, "node").getPrefix();
        }
    },

    /**
     * Represents a suffix
     */
    SUFFIX("suffix") {
        @Override
        public boolean matches(@NonNull Node node) {
            return Objects.requireNonNull(node, "node").isSuffix();
        }

        @Override
        public Map.@NonNull Entry<Integer, String> getEntry(@NonNull Node node) {
            return Objects.requireNonNull(node, "node").getSuffix();
        }
    };

    private final String str;

    ChatMetaType(String str) {
        this.str = str;
    }

    /**
     * Returns if the passed node matches the type
     *
     * @param node the node to test
     * @return true if the node has the same type
     */
    public abstract boolean matches(@NonNull Node node);

    /**
     * Returns if the passed node should be ignored when searching for meta of this type
     *
     * @param node the node to test
     * @return true if the node does not share the same type
     */
    public boolean shouldIgnore(@NonNull Node node) {
        return !matches(node);
    }

    /**
     * Maps the corresponding entry from the given node
     *
     * @param node the node to retrieve the entry from
     * @return the entry
     * @throws IllegalStateException if the node does not share the same type
     */
    public abstract Map.@NonNull Entry<Integer, String> getEntry(@NonNull Node node);

    @Override
    public String toString() {
        return this.str;
    }

    /**
     * Parses a ChatMetaType from the given node.
     *
     * @param node the node
     * @return the parsed chat meta type
     * @since 3.4
     */
    public static @NonNull Optional<ChatMetaType> ofNode(@NonNull Node node) {
        if (node.isPrefix()) {
            return Optional.of(PREFIX);
        } else if (node.isSuffix()) {
            return Optional.of(SUFFIX);
        } else {
            return Optional.empty();
        }
    }

}
