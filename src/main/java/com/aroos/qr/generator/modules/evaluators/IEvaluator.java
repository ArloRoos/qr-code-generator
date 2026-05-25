package com.aroos.qr.generator.modules.evaluators;

import com.aroos.qr.generator.modules.IQRCode;

/**
 * The {@link IEvaluator} interface defines behavior for an evaluation function
 * which operates on a QR code.
 */
public interface IEvaluator
{
    /**
     * Evaluate the given QR code, based on several criteria for how well the
     * code can be read by a scanner.
     * @param code The QR code to evaluate.
     * @return An integer score for how well the QR code performed.
     */
    int evaluate(IQRCode code);
}