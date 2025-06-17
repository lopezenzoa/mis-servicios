import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ShiftService, Shift } from '../../services/shift.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
        const headers = new HttpHeaders({
          Authorization: `Bearer ${localStorage.getItem('token')}`
        });

        // ✅ Cargar nombre del prestador con token
        this.http.get<any>(`http://localhost:8080/providers/${this.prestadorId}`, { headers }).subscribe({
          next: data => {
            this.prestadorNombre = `${data.firstName} ${data.lastName}`;
          },
          error: err => {
            console.error('Error al cargar prestador', err);
            this.prestadorNombre = '';
          }
        });

        // ✅ Cargar turnos disponibles del prestador con token
        this.http.get<Shift[]>(`http://localhost:8080/shifts/availables/${this.prestadorId}`, { headers }).subscribe({
          next: data => {
            this.turnos = data;
            this.agrupateTurnosPorFecha();
          },
          error: err => {
            console.error('Error al cargar turnos', err);
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

    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });

    this.http.post(`http://localhost:8080/shifts/reservar/${id}`, {}, { headers }).subscribe({
      next: () => {
        this.mensaje = 'Turno reservado con éxito';

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
