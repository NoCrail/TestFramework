import annotations.NTest
import annotations.NTestMethod
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import java.lang.StringBuilder
import java.lang.reflect.Method


fun main() {

    val reflections = Reflections(
        ConfigurationBuilder()
        .addUrls(ClasspathHelper.forPackage(""))
        .setScanners(TypeAnnotationsScanner(), SubTypesScanner(false)))

    val allClasses =
        reflections.getSubTypesOf(Any::class.java)


    allClasses.forEach { annotatedClass ->
        findClasses(annotatedClass)
    }
}


fun findClasses(annotatedClass : Class<out Any>) {
    val annotations = annotatedClass.annotations
    annotations.forEach { annotation ->
        val annotationClass = annotation.annotationClass.qualifiedName
        if (annotationClass == NTest::class.qualifiedName) {
            annotatedClass.methods.forEach { method ->
                findMethods(method, annotatedClass)
            }
        }

    }
}

fun findMethods(method: Method, annotatedClass: Class<out Any>){
    val methodAnnotations = method.annotations
    methodAnnotations.forEach {methodAnnotation ->
        val annotatedMethod = methodAnnotation.annotationClass.qualifiedName
        if (annotatedMethod==NTestMethod::class.qualifiedName){
            callTestMethod(method, annotatedClass)
            //println(method.invoke(annotatedClass.getConstructor().newInstance()))
        }
    }
}

fun callTestMethod(method: Method, annotatedClass: Class<out Any>){
    val result = method.invoke(annotatedClass.getConstructor().newInstance()).toString()
    println(buildCallInfo(method.name, annotatedClass.name, result))
}

fun buildCallInfo(methodName: String, className: String, result: String): String{
    val sb = StringBuilder()
    sb.append("Called: ")
    sb.append(methodName)
    sb.append(" from ")
    sb.append(className)
    sb.append(" returned: ")
    sb.append(result)
    return sb.toString()
}