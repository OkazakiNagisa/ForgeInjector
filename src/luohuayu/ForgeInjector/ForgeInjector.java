package luohuayu.ForgeInjector;

import com.sun.tools.attach.VirtualMachine;

public class ForgeInjector {
    private int pid;

    public ForgeInjector(int pid) {
        this.pid = pid;
    }

    public void inject() throws Exception {
        VirtualMachine vm = VirtualMachine.attach(String.valueOf(pid));
        vm.loadAgent("C:\\Users\\Administrator\\Desktop\\ForgeInjector.jar");
        vm.detach();
    }
}
