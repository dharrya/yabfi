package com.dharrya.clickable.components;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.Nullable;

public class ComponentTemplateReference implements PsiReference  {
        protected  PsiElement mElement;
        protected  TextRange mTextRange;
        protected  Project mProject;
        protected  String mPath;
        protected  VirtualFile mAppDir;
        protected  String mComponentName;

        public ComponentTemplateReference(String path, PsiElement element, TextRange textRange, Project project, VirtualFile appDir, String componentName) {
            this.mElement = element;
            this.mTextRange = textRange;
            this.mProject = project;
            this.mPath = path;
            this.mAppDir = appDir;
            this.mComponentName = componentName;
            if(this.mPath.isEmpty()) {
                 this.mPath = ".default";
            }
        }

        @Override
        public String toString() {
            return getCanonicalText();
        }

        public PsiElement getElement() {
            return this.mElement;
        }

        public TextRange getRangeInElement() {
            return mTextRange;
        }

        public PsiElement handleElementRename(String newElementName)
                throws IncorrectOperationException {
            // TODO: Implement this method
            throw new IncorrectOperationException();
        }

        public PsiElement bindToElement(PsiElement element) throws IncorrectOperationException {
            // TODO: Implement this method
            throw new IncorrectOperationException();
        }

        public boolean isReferenceTo(PsiElement element) {
            return resolve() == element;
        }

        public Object[] getVariants() {
            // TODO: Implement this method
            return new Object[0];
        }

        public boolean isSoft() {
            return false;
        }


    @Nullable
    public PsiElement getTargetFile(final String pPath,final String pFileName) {
        VirtualFile targetFile = mAppDir.findFileByRelativePath(pPath + "/" + pFileName);

        if (targetFile != null) {
            return getProject().findFile(targetFile);
        }

        return null;
    }

    @Nullable
    public PsiElement resolve() {
        final String path = mComponentName.replace(":", "/") + "/templates/" + mPath;
        return getTargetFile(path, "template.php");
//        return result;
    }

    protected PsiManager getProject() {
        return  PsiManager.getInstance(mProject);
    }

    @Override
    public String getCanonicalText() {
        return mPath;
    }
}
