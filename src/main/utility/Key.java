package main.utility;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key {
    double curX, curY;
    public boolean anyKey;
    public boolean w, a, s, d, space, up, down, left, right, esc, enter, del, i, j, k, l, alt, tilde, z, equals, minus, lBrace, rBrace, semicolon, quote,
            k0, k9, k8, k7, k1, q;
    public boolean wL, aL, sL, dL, spaceL, upL, downL, leftL, rightL, escL, enterL, delL, iL, jL, kL, lL, altL, tildeL, zL, equalsL, minusL, lBraceL, rBraceL, semicolonL, quoteL,
            k0L, k9L, k8L, k7L, k1L, qL;
    int frameX, frameY;

    public Key(Container c) {
        c.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                anyKey = true;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> w = true;
                    case KeyEvent.VK_A -> a = true;
                    case KeyEvent.VK_S -> s = true;
                    case KeyEvent.VK_D -> d = true;
                    case KeyEvent.VK_SPACE -> space = true;
                    case KeyEvent.VK_UP -> up = true;
                    case KeyEvent.VK_DOWN -> down = true;
                    case KeyEvent.VK_LEFT -> left = true;
                    case KeyEvent.VK_RIGHT -> right = true;
                    case KeyEvent.VK_ESCAPE -> esc = true;
                    case KeyEvent.VK_ENTER -> enter = true;
                    case KeyEvent.VK_DELETE -> del = true;
                    case KeyEvent.VK_I -> i = true;
                    case KeyEvent.VK_J -> j = true;
                    case KeyEvent.VK_K -> k = true;
                    case KeyEvent.VK_L -> l = true;
                    case KeyEvent.VK_ALT -> alt = true;
                    case KeyEvent.VK_DEAD_TILDE -> tilde = true;
                    case KeyEvent.VK_Z -> z = true;
                    case KeyEvent.VK_EQUALS -> equals = true;
                    case KeyEvent.VK_MINUS -> minus = true;
                    case KeyEvent.VK_BRACERIGHT -> rBrace = true;
                    case KeyEvent.VK_BRACELEFT -> lBrace = true;
                    case KeyEvent.VK_SEMICOLON -> semicolon = true;
                    case KeyEvent.VK_QUOTE -> quote = true;
                    case KeyEvent.VK_0 -> k0 = true;
                    case KeyEvent.VK_9 -> k9 = true;
                    case KeyEvent.VK_8 -> k8 = true;
                    case KeyEvent.VK_7 -> k7 = true;
                    case KeyEvent.VK_1 -> k1 = true;
                    case KeyEvent.VK_Q -> q = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                anyKey = false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W -> w = false;
                    case KeyEvent.VK_A -> a = false;
                    case KeyEvent.VK_S -> s = false;
                    case KeyEvent.VK_D -> d = false;
                    case KeyEvent.VK_SPACE -> space = false;
                    case KeyEvent.VK_UP -> up = false;
                    case KeyEvent.VK_DOWN -> down = false;
                    case KeyEvent.VK_LEFT -> left = false;
                    case KeyEvent.VK_RIGHT -> right = false;
                    case KeyEvent.VK_ESCAPE -> esc = false;
                    case KeyEvent.VK_ENTER -> enter = false;
                    case KeyEvent.VK_DELETE -> del = false;
                    case KeyEvent.VK_I -> i = false;
                    case KeyEvent.VK_J -> j = false;
                    case KeyEvent.VK_K -> k = false;
                    case KeyEvent.VK_L -> l = false;
                    case KeyEvent.VK_ALT -> alt = false;
                    case KeyEvent.VK_DEAD_TILDE -> tilde = false;
                    case KeyEvent.VK_Z -> z = false;
                    case KeyEvent.VK_EQUALS -> equals = false;
                    case KeyEvent.VK_MINUS -> minus = false;
                    case KeyEvent.VK_BRACERIGHT -> rBrace = false;
                    case KeyEvent.VK_BRACELEFT -> lBrace = false;
                    case KeyEvent.VK_SEMICOLON -> semicolon = false;
                    case KeyEvent.VK_QUOTE -> quote = false;
                    case KeyEvent.VK_0 -> k0 = false;
                    case KeyEvent.VK_9 -> k9 = false;
                    case KeyEvent.VK_8 -> k8 = false;
                    case KeyEvent.VK_7 -> k7 = false;
                    case KeyEvent.VK_1 -> k1 = false;
                    case KeyEvent.VK_Q -> q = false;
                }
            }
        });
    }

    double getCurXY(boolean x) {
        Point cursor = MouseInfo.getPointerInfo().getLocation();
        curX = cursor.getX();
        curY = cursor.getY();
        return (x ? curX : curY);
    }

    double getCurXY(boolean x, Container c) {
        frameX = c.getX();
        frameY = c.getY();

        Point cursor = MouseInfo.getPointerInfo().getLocation();
        curX = cursor.getX() - frameX - 8;
        curY = cursor.getY() - frameY - 31;
        return (x ? curX : curY);
    }
}
