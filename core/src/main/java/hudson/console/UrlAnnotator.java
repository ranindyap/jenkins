package hudson.console;

import hudson.Extension;
import hudson.MarkupText;
import hudson.MarkupText.SubText;

import java.util.regex.Pattern;

/**
 * Annotates URLs in the console output to hyperlink.
 *
 * @author Kohsuke Kawaguchi
 */
@Extension
public class UrlAnnotator extends ConsoleAnnotatorFactory<Object> {
    @Override
    public ConsoleAnnotator newInstance(Object context) {
        return new UrlConsoleAnnotator();
    }

    private static class UrlConsoleAnnotator extends ConsoleAnnotator {
        public ConsoleAnnotator annotate(Object context, MarkupText text) {
            for (SubText t : text.findTokens(URL))
                t.href(t.getText());
            return this;
        }

        private static final long serialVersionUID = 1L;

        /**
         * Starts with a word boundary and protocol identifier,
         * don't include any whitespace, '&lt;', nor '>'.
         * In addition, the last character shouldn't be ',' ':', '"', etc, as often those things show up right next
         * to URL in plain text (e.g., test="http://www.example.com/")
         */
        private static final Pattern URL = Pattern.compile("\\b(http|https|ftp)://[^\\s<>]+[^\\s<>,:\"'()\\[\\]=]");
    }
}
