package edu.tec.ic6821.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InstantiationCheck extends AbstractCheck {

    private static final String MSG_UNEXPECTED_INSTANTIATION = "[IC-6821] Llamada inesperada al constructor %s";

    private final Set<String> ignore = new HashSet<>();
    private final Set<String> include = new HashSet<>();
    private final Set<String> constructors = new HashSet<>();

    public void setIgnore(final String... ignore) {
        this.ignore.addAll(Arrays.asList(ignore));
    }

    public void setInclude(final String... include) {
        this.include.addAll(Arrays.asList(include));
    }

    public void setConstructors(final String... constructors) {
        this.constructors.addAll(Arrays.asList(constructors));
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF, TokenTypes.ENUM_DEF};
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
    public void visitToken(final DetailAST ast) {
        super.visitToken(ast);

        final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
        final List<String> matches = this.ignore.stream()
            .filter(name -> ident.getText().matches(name) && !this.include.contains(ident.getText()))
            .collect(Collectors.toList());

        if (!matches.isEmpty()) {
            return;
        }

        traverse(ast);
    }

    private void traverse(final DetailAST ast) {
        if (ast.getType() == TokenTypes.LITERAL_NEW) {
            final DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
            if (ident != null) {
                final String constructor = ident.getText();
                if (this.constructors.contains(constructor)) {
                    log(ast.getLineNo(), String.format(MSG_UNEXPECTED_INSTANTIATION, constructor));
                }
            }
        } else if (ast.hasChildren()) {
            DetailAST child = ast.getFirstChild();
            while (child != null) {
                traverse(child);
                child = child.getNextSibling();
            }
        }
    }
}
