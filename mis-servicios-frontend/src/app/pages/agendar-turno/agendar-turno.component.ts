import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ShiftService, Shift } from '../../services/shift.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-agendar-turno',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './agendar-turno.component.html',
  styleUrls: ['./agendar-turno.component.css']
})
export class AgendarTurnoComponent implements OnInit {
  turnos: Shift[] = [];
  turnosAgrupados: { [fecha: string]: Shift[] } = {};
  mensaje: string = '';
  prestadorId: number | null = null;
  prestadorNombre: string = '';

  constructor(
    private shiftService: ShiftService,
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.prestadorId = +params['prestadorId'];

      if (this.prestadorId) {
        // Cargar nombre del prestador
        this.http.get<any>(`http://localhost:8080/providers/${this.prestadorId}`).subscribe({
          next: data => {
            this.prestadorNombre = `${data.firstName} ${data.lastName}`;
          },
          error: () => {
            this.prestadorNombre = '';
          }
        });

        // Cargar turnos
        this.shiftService.getShiftsByProvider(this.prestadorId).subscribe({
          next: data => {
            this.turnos = data;
            this.agrupateTurnosPorFecha();
          },
          error: () => {
            this.mensaje = 'Error al cargar turnos del prestador';
          }
        });
      } else {
        this.mensaje = 'No se seleccionó ningún prestador.';
      }
    });
  }

  agrupateTurnosPorFecha(): void {
    this.turnosAgrupados = this.turnos.reduce((acc, turno) => {
      const fecha = new Date(turno.dateTime).toDateString();
      if (!acc[fecha]) acc[fecha] = [];
      acc[fecha].push(turno);
      return acc;
    }, {} as { [key: string]: Shift[] });
  }

 reservar(id: number): void {
   if (!id || isNaN(id)) {
     this.mensaje = 'ID del turno no válido';
     return;
   }

   this.shiftService.reserveShift(id).subscribe({
     next: () => {
       this.mensaje = 'Turno reservado con éxito';

       // 🔧 Corregido acá:
       const turno = this.turnos.find(t => t.id === id);
       if (turno) turno.available = false;

       this.agrupateTurnosPorFecha();
     },
     error: err => {
       console.error('Error al reservar:', err);
       this.mensaje = 'No se pudo reservar el turno';
     }
   });
 }

}
