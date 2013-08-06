package com.dharrya.clickable.components;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiReferenceRegistrar;
import com.intellij.psi.PsiReferenceContributor;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;

public class ComponentSrcContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        final String regexp = "[\"'][a-z.]+:[a-z_.]+[\"']";

        PsiElementPattern.Capture<StringLiteralExpression> psiElementCapture = PlatformPatterns.psiElement(
                StringLiteralExpression.class).withText(StandardPatterns.string().matches(regexp));

        ComponentSrcReferenceProvider provider = new ComponentSrcReferenceProvider();

        registrar.registerReferenceProvider(
                psiElementCapture,
                provider,
                PsiReferenceRegistrar.DEFAULT_PRIORITY
        );
    }
}
