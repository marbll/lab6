package data;

import java.io.Serializable;

/**
 * Описывает сложность лабораторной работы
 */
public enum Difficulty implements Serializable {
    VERY_EASY,
    EASY,
    NORMAL,
    INSANE;
}