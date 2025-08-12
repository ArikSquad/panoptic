package eu.mikart.panoptic.script;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.type.AviatorObject;
import com.googlecode.aviator.runtime.type.AviatorRuntimeJavaType;

public final class AviatorScriptEngine {
    private static final ConcurrentHashMap<String, Expression> INLINE_CACHE = new ConcurrentHashMap<>();

    private AviatorScriptEngine() {}

    public static void clearCaches() {
        INLINE_CACHE.clear();
    }

    public static Object executeInline(String script, Map<String, Object> env) {
        Expression compiled = INLINE_CACHE.computeIfAbsent(Objects.requireNonNull(script), AviatorEvaluator::compile);
        return compiled.execute(env);
    }

}
