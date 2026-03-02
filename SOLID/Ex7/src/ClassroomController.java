public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        InputControl pj = reg.getFirstByCapability(InputControl.class);
        if (pj instanceof PowerControl) {
            ((PowerControl) pj).powerOn();
        }
        pj.connectInput("HDMI-1");
        BrightnessControl lights = reg.getFirstByCapability(BrightnessControl.class);

        lights.setBrightness(60);
        TemperatureControl ac = reg.getFirstByCapability(TemperatureControl.class);
        ac.setTemperatureC(24);

        ScannerControl scan = reg.getFirstByCapability(ScannerControl.class);
        System.out.println("Attendance scanned: present=" + scan.scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        for (PowerControl device : reg.getAllByCapability(PowerControl.class)) {
            device.powerOff();
        }
    }
}