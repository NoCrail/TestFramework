import annotations.NTest
import annotations.NTestMethod
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import java.lang.reflect.Method


fun main() {

    Reflect.getAllClasses().forEach { annotatedClass ->
        annotatedClass.findAnnotatedMethodsInAnnotatedClasses().forEach {
            println(buildCallInfo(it.name, annotatedClass.name, annotatedClass.testMethod(it)))
        }
    }
}


object Reflect {
    private val reflections =
            Reflections(
                    ConfigurationBuilder()
                            .addUrls(ClasspathHelper.forPackage(""))
                            .setScanners(
                                    TypeAnnotationsScanner(),
                                    SubTypesScanner(false)
                            )
            )

    fun getAllClasses(): Set<Class<out Any>> = reflections.getSubTypesOf(Any::class.java)
}


//Поиск методов с аннотацией NTestMethod в классе с аннотацией NTest
fun Class<out Any>.findAnnotatedMethodsInAnnotatedClasses(): Collection<Method> {
    val m = mutableListOf<Method>()
    if (getAnnotation(NTest::class.java) != null) {
        methods.forEach { method ->
            if (method.getAnnotation(NTestMethod::class.java) != null)
                m.add(method)
        }
    }

    return m
}

fun Class<out Any>.testMethod(method: Method): Any? =
        method.invoke(getConstructor().newInstance())


//Построение сообщения для вывода
fun buildCallInfo(methodName: String, className: String, result: Any?): String =
        "Called: $methodName from $className returned: $result"