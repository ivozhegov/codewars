import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class Transpiler {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    public void expect (String actual, String expected) {
        assertEquals (expected, actual);
        //collector.checkThat (expected, CoreMatchers.equalTo (actual));
    }

    public void fromTo (String input, String expected) {
        expect (Transpiler.transpile (input), expected);
    }

    public void shouldFail (String input) {
        fromTo (input, "");
    }

    @Test
    public void testSomething() {
        fromTo ("", "");
        fromTo ("1()", "1()");
        fromTo ("123()", "123()");
        fromTo ("a()", "a()");
        fromTo ("abc()", "abc()");
    }

    @Test
    public void testWhen_there_is_no_lambda () {
        shouldFail ("f({a->)");
        shouldFail ("f(");
        shouldFail ("f)");
        shouldFail ("%^&*(");
        shouldFail ("x9x92xb29xub29bx120()!(");
        fromTo ("call()", "call()");
        fromTo ("invoke  (       a    ,   b   )", "invoke(a,b)");
        fromTo ("invoke(a, b)", "invoke(a,b)");
    }

    @Test
    public void testWhen_there_are_lambda_expressions () {
        fromTo ("call({})", "call((){})");
        fromTo ("f({a->})", "f((a){})");
        fromTo ("f({a->a})", "f((a){a;})");
        fromTo ("f({_->})", "f((_){})");
    }

    @Test
    public void testWhen_lambda_expressions_aren_t_inside_brackets () {
        fromTo ("call(\n){}", "call((){})");
        fromTo ("invoke  (       a    ,   b   ) { } ", "invoke(a,b,(){})");
        fromTo ("f(x){a->}", "f(x,(a){})");
        fromTo ("f(a,b){a->a}", "f(a,b,(a){a;})");
        fromTo ("run{a}", "run((){a;})");
    }

    @Test
    public void testWhen_invoking_a_lambda_directly () {
        fromTo ("{}()", "(){}()");
        fromTo ("{a->a}(233)", "(a){a;}(233)");
    }

    /**
     ********************************************************
     * The source language looks like:
     ********************************************************
     *
     * function ::= expression "(" [parameters] ")" [lambda]
     *            | expression lambda
     *
     * expression ::= name
     *              | lambda
     *
     * parameters ::= expression ["," parameters]
     *
     * lambdaParam ::= nameOrNumber ["," lambdaParam]
     * lambdaStmt  ::= nameOrNumber [lambdaStmt]
     *
     * lambda ::= "{" [lambdaParam "->"] [lambdaStmt] "}"
     *
     ********************************************************
     * The target language looks like:
     ********************************************************
     *
     * function ::= expression "(" [parameters] ")"
     *
     * expression ::= nameOrNumber
     *              | lambda
     *
     * parameters ::= expression ["," parameters]
     *
     * lambdaParam ::= nameOrNumber ["," lambdaParam]
     * lambdaStmt  ::= nameOrNumber ";" [lambdaStmt]
     *
     * lambda ::= "(" [lambdaParam] "){" [lambdaStmt] "}"
     *
     */
    public static String transpile(String expression) {
        System.out.println(">> " + expression);
        String result;
        try {
            result = new Function(expression).toString();
        } catch (IllegalArgumentException e) {
            result = "";
        }
        System.out.println("<< " + result);
        return result;
    }

    private static String removeSugar(String value) {
        if (value.endsWith(")")) {
            return value;
        }
        if (value.endsWith("}")) {
            int bracketIndex = value.lastIndexOf("{");
            if (value.charAt(bracketIndex - 1) == ')') {
                return value.substring(0, bracketIndex - 1) +
                        (value.charAt(bracketIndex - 2) == '(' ? "" : ",") +
                        value.substring(bracketIndex) + ")";
            } else {
                return value.substring(0, bracketIndex) + "(" +
                        value.substring(bracketIndex) + ")";
            }
        }
        throw new IllegalArgumentException("incorrect input");
    }

    private static String removeSpaces(String value) {
        String[] parts = value.split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            result.append(parts[i]);
            if (i < parts.length - 1) {
                if (isMatches(parts[i], ".*\\w") && isMatches(parts[i + 1], "\\w.*")) {
                    result.append(" ");
                }
            }
        }
        return result.toString();
    }

    private static class Function {
        private Expression expression;
        private Parameters parameters;

        private Function(String value) {
            value = removeSpaces(value);
            value = removeSugar(value);
            int bracketIndex = value.length();

            int openBracketCount = 0;
            do {
                bracketIndex--;
                if (bracketIndex < 0) {
                    throw new IllegalArgumentException("incorrect input");
                }
                if (value.charAt(bracketIndex) == ')') {
                    openBracketCount++;
                } else if (value.charAt(bracketIndex) == '(') {
                    openBracketCount--;
                }
            } while (openBracketCount > 0);

            expression = new Expression(value.substring(0, bracketIndex));
            String parametersString = value.substring(bracketIndex + 1, value.length() - 1);
            if (parametersString.length() > 0) {
                parameters = new Parameters(parametersString);
            }
        }

        @Override
        public String toString() {
            return expression.toString() + "(" +
                    (parameters == null ? "" : parameters.toString()) +
                    ")";
        }
    }

    private static class Expression {
        private NameOrNumber name;
        private Lambda lambda;

        private Expression(String value) {
            if (isMatches(value, "\\{.*}")) {
                lambda = new Lambda(value);
            } else {
                name = new NameOrNumber(value, true);
            }
        }

        @Override
        public String toString() {
            return name == null ? lambda.toString() : name.toString();
        }
    }

    private static class Parameters {
        private Expression expression;
        private Parameters parameters;

        private Parameters(String value) {
            int commaIndex;
            if (value.startsWith("{")) {
                commaIndex = value.indexOf(",", value.indexOf("}"));
            } else {
                commaIndex = value.indexOf(",");
            }
            if (commaIndex > 0) {
                expression = new Expression(value.substring(0, commaIndex));
                parameters = new Parameters(value.substring(commaIndex + 1));
            } else {
                expression = new Expression(value);
            }
        }

        @Override
        public String toString() {
            return expression.toString() +
                    (parameters == null ? "" : "," + parameters.toString());
        }
    }

    private static class Lambda {
        private LambdaParam lambdaParam;
        private LambdaStmt lambdaStmt;

        private Lambda(String value) {
            lambdaParam = null;
            lambdaStmt = null;
            int arrowIndex = value.indexOf("->");
            String lambdaStmtValue;
            if (arrowIndex > 0) {
                lambdaParam = new LambdaParam(value.substring(1, arrowIndex));
                lambdaStmtValue = value.substring(arrowIndex + 2, value.length() - 1);
            } else {
                lambdaStmtValue = value.substring(1, value.length() - 1);
            }
            if (lambdaStmtValue.length() > 0) {
                lambdaStmt = new LambdaStmt(lambdaStmtValue);
            }
        }

        @Override
        public String toString() {
            return "(" +
                    (lambdaParam == null ? "" : lambdaParam.toString()) +
                    ")" +
                    "{" +
                    (lambdaStmt == null ? "" : lambdaStmt.toString()) +
                    "}";
        }
    }

    private static class LambdaParam {
        private NameOrNumber nameOrNumber;
        private LambdaParam lambdaParam;

        private LambdaParam(String value) {
            nameOrNumber = null;
            lambdaParam = null;
            int commaIndex = value.indexOf(",");
            if (commaIndex > 0) {
                nameOrNumber = new NameOrNumber(value.substring(0, commaIndex), true);
                lambdaParam = new LambdaParam(value.substring(commaIndex + 1));
            } else {
                nameOrNumber = new NameOrNumber(value, true);
            }
        }

        @Override
        public String toString() {
            return nameOrNumber.toString() +
                    (lambdaParam == null ? "" : "," + lambdaParam.toString());
        }
    }

    private static class LambdaStmt {
        private NameOrNumber nameOrNumber;
        private LambdaStmt lambdaStmt;

        private LambdaStmt(String value) {
            nameOrNumber = null;
            lambdaStmt = null;
            int commaIndex = value.indexOf(" ");
            if (commaIndex > 0) {
                nameOrNumber = new NameOrNumber(value.substring(0, commaIndex), true);
                lambdaStmt = new LambdaStmt(value.substring(commaIndex + 1));
            } else {
                nameOrNumber = new NameOrNumber(value, true);
            }
        }

        @Override
        public String toString() {
            return nameOrNumber.toString() + ";" +
                    (lambdaStmt == null ? "" : lambdaStmt.toString());
        }
    }

    private static class NameOrNumber {
        private String value;

        private NameOrNumber(String value, boolean numbersAllowed) {
            if (numbersAllowed && isMatches(value, "\\d+")) {
                this.value = value;
            } else if (isMatches(value, "[a-zA-Z_]\\w*")) {
                this.value = value;
            } else {
                throw new IllegalArgumentException("incorrect name or number \"" + value + "\"");
            }
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private static boolean isMatches(String value, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
