package app.finanza.scheduler;

import app.finanza.service.EstadoCuentaSocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Scheduled Task para generar mensualidades automáticamente
 * Se ejecuta el día 1 de cada mes a las 00:15 AM (Bolivia Time)
 * 
 * Bolivia: UTC-4 (todo el año, no cambia por horario de verano)
 * 
 * Expresión de cron: "0 15 0 1 * ?"
 * - Segundo: 0
 * - Minuto: 15
 * - Hora: 0 (00:15 AM)
 * - Día del mes: 1 (primer día)
 * - Mes: * (todos los meses)
 * - Día de la semana: ? (no importa)
 */
@Component
public class MensualidadesScheduler {
    
    @Autowired
    private EstadoCuentaSocioService estadoCuentaService;
    
    /**
     * Genera las mensualidades para todos los socios activos
     * Se ejecuta el primer día de cada mes a las 00:15 AM (Bolivia)
     */
    @Scheduled(cron = "0 15 0 1 * ?", zone = "America/La_Paz")
    public void generarMensualidades() {
        LocalDateTime ahora = LocalDateTime.now(ZoneId.of("America/La_Paz"));
        
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  CRON JOB: Generación Automática de Mensualidades         ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  Fecha/Hora: " + ahora + "                  ║");
        System.out.println("║  Zona Horaria: America/La_Paz (UTC-4)                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        try {
            estadoCuentaService.generarMensualidadesAutomaticas();
            System.out.println("✓ Mensualidades generadas exitosamente");
        } catch (Exception e) {
            System.err.println("✗ ERROR al generar mensualidades: " + e.getMessage());
            e.printStackTrace();
            // No lanzar excepción para no detener el scheduler
        }
        
        System.out.println("════════════════════════════════════════════════════════════\n");
    }
    
    /**
     * Actualiza el estado de las deudas vencidas
     * Se ejecuta diariamente a las 01:00 AM
     */
    @Scheduled(cron = "0 0 1 * * ?", zone = "America/La_Paz")
    public void actualizarDeudasVencidas() {
        LocalDateTime ahora = LocalDateTime.now(ZoneId.of("America/La_Paz"));
        
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  CRON JOB: Actualización de Deudas Vencidas               ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  Fecha/Hora: " + ahora + "                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        try {
            estadoCuentaService.marcarDeudasVencidas();
            System.out.println("✓ Actualización de deudas vencidas completada");
        } catch (Exception e) {
            System.err.println("✗ ERROR al actualizar deudas vencidas: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("════════════════════════════════════════════════════════════\n");
    }
}
