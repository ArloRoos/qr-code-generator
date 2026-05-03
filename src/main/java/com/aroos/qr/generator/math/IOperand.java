package com.aroos.qr.generator.math;

/**
 * The {@link IOperand} interface defines a mathematical structure that can be
 * operated on via basic arthimetic operations.
 */
public interface IOperand<T>
{
    /**
     * Returns the result of adding another operand to this one.
     * @param other The other operand.
     * @return The sum of the operation.
     */
    T plus(T other);

    /**
     * Returns the result of subtracting another operand from this one.
     * @param other The other operand.
     * @return The difference of the operation.
     */
    T minus(T other);

    /**
     * Returns the result of multiplying another operand with this one.
     * @param other The other operand.
     * @return The product of the operation.
     */
    T multipliedBy(T other);

    /**
     * Returns the result of dividing this by another operand.
     * @param other The other operand.
     * @return The quotient of the operation.
     */
    T dividedBy(T other);
}