package io.dreamstudio.springcloud.netflix.zuul.proxy;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Ricky Fung
 */
public class ZuulTest {

    @Test
    @Ignore
    public void testApp() {

        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
    }

    @Test
    @Ignore
    public void testGroovy() {
        final String scriptRoot = System.getProperty("zuul.filter.root");
        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            FilterFileManager.init(5,
                    scriptRoot + "/pre",
                    scriptRoot + "/route",
                    scriptRoot + "/post");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setDebugRouting(true);
        ctx.setDebugRequest(true);
        return null;
    }
}
