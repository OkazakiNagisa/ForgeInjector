package luohuayu.ForgeInjector;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

public class Agent {
    private static String injectClass = LaunchInjector.class.getCanonicalName().replaceAll("\\.", "/");

    public static void agentmain(String agentOps) throws Exception {
        byte[] byteClass = getClassByte();

        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            ClassLoader cl = thread.getContextClassLoader();
            if (cl.getClass().getSimpleName().equals("LaunchClassLoader")) {
                Method method = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class, ProtectionDomain.class);
                method.setAccessible(true);
                Class<?> clazz = (Class<?>) method.invoke(cl, injectClass.replace("/", "."), getClassByte(), 0, byteClass.length, null);
                clazz.getMethod("run").invoke(null);
                break;
            }
        }
    }

    public static byte[] getClassByte() throws IOException {
        InputStream in = ClassLoader.getSystemResourceAsStream(injectClass + ".class");
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();
        return data;
    }
}
