package com.dharrya.clickable.components;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;

public class ComponentTemplateReferenceProvider extends PsiReferenceProvider {

    public ComponentTemplateReferenceProvider() {
    }

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull final ProcessingContext context) {
        Project project = element.getProject();
        VirtualFile appDir = project.getBaseDir().findFileByRelativePath("bitrix/components");
        if (appDir == null) {
            return PsiReference.EMPTY_ARRAY;
        }

        try {

            MethodReference method = (MethodReference) element.getParent().getParent();
            if (isIncludeComponentMethod(method)) {
                StringLiteralExpression el = (StringLiteralExpression) element;
                TextRange textRange = el.getValueRange();
                String value = el.getText().substring(textRange.getStartOffset(), textRange.getEndOffset());
                String componentName = element.getParent().getFirstChild().getText();
                componentName = componentName.substring(1, componentName.length() - 1);
                PsiReference ref = new ComponentTemplateReference(value, element, textRange, project, appDir, componentName);
                return new PsiReference[]{ref};
            }
        } catch (Exception e) {
        }

        return PsiReference.EMPTY_ARRAY;
    }

    public static boolean isIncludeComponentMethod(@NotNull MethodReference element) {
        try {
            if (element.getName().toLowerCase().equals("includecomponent")) {
                return true;
            }
        } catch (Exception ex) {

        }
        return false;
    }



}
