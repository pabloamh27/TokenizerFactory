package edu.tec.ic6821.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.List;

public class OpenClosedCheck extends AbstractCheck {

    private static final String MSG_IF_CONDITIONAL = "[IC-6821] El uso de condicionales en %s.%s rompe el principio de abierto cerrado";
    private static final String MSG_SWITCH_CONDITIONAL = "[IC-6821] El uso de condicionales en %s.%s rompe el principio de abierto cerrado";

    private String className;
    private String methodName;

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getDefaultTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        super.visitToken(ast);
        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        if (ident == null || !this.className.equals(ident.getText())) {
            return;
        }

        final DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        if (objBlock != null) {
            final List<DetailAST> methodDefs = new ASTWalker(objBlock).filterTokensByType(TokenTypes.METHOD_DEF);
            methodDefs.forEach(methodDef -> {
                final DetailAST methodDefIdent = methodDef.findFirstToken(TokenTypes.IDENT);
                if (this.methodName.equals(methodDefIdent.getText())) {
                    if (methodDef.branchContains(TokenTypes.LITERAL_IF)) {
                        log(methodDef.getLineNo(),
                            String.format(MSG_IF_CONDITIONAL, this.className, this.methodName));
                    }

                    if (methodDef.branchContains(TokenTypes.LITERAL_SWITCH)) {
                        log(methodDef.getLineNo(),
                            String.format(MSG_SWITCH_CONDITIONAL, this.className, this.methodName));
                    }
                }
            });
        }
    }
}
