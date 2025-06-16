import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { ShiftService, Shift } from '../../services/shift.service';

@Component({
  selector: 'app-agendar-turno',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './agendar-turno.component.html',
  styleUrls: ['./agendar-turno.component.css']
})
export class AgendarTurnoComponent implements OnInit {
  turnos: Shift[] = [];
  mensaje: string = '';
  prestadorId: number | null = null;

  constructor(private shiftService: ShiftService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Obtener el ID del prestador desde los parámetros
    this.route.queryParams.subscribe(params => {
      this.prestadorId = +params['prestadorId'];

      if (this.prestadorId) {
        this.shiftService.getShiftsByProvider(this.prestadorId).subscribe({
          next: data => this.turnos = data,
          error: () => this.mensaje = 'Error al cargar turnos del prestador'
        });
      } else {
        this.mensaje = 'No se seleccionó ningún prestador.';
      }
    });
  }

  reservar(id: number): void {
    this.shiftService.reserveShift(id).subscribe({
      next: () => {
        this.mensaje = 'Turno reservado con éxito';
        this.turnos = this.turnos.filter(t => t.id !== id);
      },
      error: () => {
        this.mensaje = 'No se pudo reservar el turno';
      }
    });
  }
}
