// ==========================================
// CASO DE USO: GESTIÓN DE CITAS MÉDICAS
// ==========================================

// --- 1. PATRÓN CREACIONAL: SINGLETON ---
// Explicación: Aseguramos una única instancia para el registro global de citas.
class RegistroCitas {
    private static RegistroCitas instancia;

    private RegistroCitas() {} // Constructor privado

    public static RegistroCitas getInstancia() {
        if (instancia == null) {
            instancia = new RegistroCitas();
        }
        return instancia;
    }

    public void registrarCita(String paciente, String doctor) {
        System.out.println("📌 [Singleton] Registrando cita: Paciente " + paciente + " con el Dr. " + doctor);
    }
}

// --- 2. PATRÓN ESTRUCTURAL: ADAPTER ---
// Explicación: Adaptamos un servicio externo antiguo para que funcione con nuestra interfaz moderna.
interface ServicioNotificacion {
    void notificar(String mensaje);
}

// Clase antigua que no podemos modificar (ej. API de un tercero)
class SistemaSmsAntiguo {
    public void enviarMensajeTexto(String texto) {
        System.out.println("📱 [Adapter] SMS enviado: " + texto);
    }
}

// El Adaptador traduce nuestra interfaz al sistema antiguo
class AdaptadorSms implements ServicioNotificacion {
    private SistemaSmsAntiguo sistemaAntiguo;

    public AdaptadorSms(SistemaSmsAntiguo sistema) {
        this.sistemaAntiguo = sistema;
    }

    @Override
    public void notificar(String mensaje) {
        sistemaAntiguo.enviarMensajeTexto(mensaje);
    }
}

// --- 3. PATRÓN DE COMPORTAMIENTO: STRATEGY ---
// Explicación: Diferentes algoritmos de cobro que se pueden intercambiar en tiempo de ejecución.
interface EstrategiaPago {
    void pagar(double monto);
}

class PagoEfectivo implements EstrategiaPago {
    @Override
    public void pagar(double monto) {
        System.out.println("💵 [Strategy] Pagando $" + monto + " en efectivo en ventanilla.");
    }
}

class PagoSeguro implements EstrategiaPago {
    @Override
    public void pagar(double monto) {
        double copago = monto * 0.20; // El seguro cubre el 80%
        System.out.println("🏥 [Strategy] Pagando copago de $" + copago + " con el Seguro.");
    }
}

// Clase Contexto que usa la estrategia
class CitaMedica {
    private EstrategiaPago estrategiaPago;

    public void setEstrategiaPago(EstrategiaPago estrategia) {
        this.estrategiaPago = estrategia;
    }

    public void cobrar(double precioBase) {
        if (estrategiaPago != null) {
            estrategiaPago.pagar(precioBase);
        } else {
            System.out.println("Error: No se ha seleccionado un método de pago.");
        }
    }
}

// --- CLASE PRINCIPAL PARA EJECUTAR EN CONSOLA ---
public class servicioCitasMedicas {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA DE CITAS MÉDICAS ===\n");

        // 1. Usando Singleton
        RegistroCitas registro = RegistroCitas.getInstancia();
        registro.registrarCita("Ana Gómez", "Pérez");

        // 2. Usando Adapter
        SistemaSmsAntiguo smsViejo = new SistemaSmsAntiguo();
        ServicioNotificacion notificador = new AdaptadorSms(smsViejo);
        notificador.notificar("Su cita ha sido confirmada para mañana a las 10:00 AM.");

        // 3. Usando Strategy
        CitaMedica cita = new CitaMedica();
        
        // Paciente decide pagar en efectivo
        cita.setEstrategiaPago(new PagoEfectivo());
        cita.cobrar(50.0);

        System.out.println("-----------------------------------");
        
        // Paciente decide usar su seguro
        cita.setEstrategiaPago(new PagoSeguro());
        cita.cobrar(50.0);
    }
}