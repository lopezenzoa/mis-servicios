import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-inicio-prestador',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './inicio-prestador.component.html',
  styleUrls: ['./inicio-prestador.component.css']
})
export class InicioPrestadorComponent implements OnInit {
  nombrePrestador = '';
  solicitudes: any[] = [];
  turnosAgendados: any[] = [];
  turnosDisponibles: any[] = [];
  turnosAgregados: any[] = [];
  nuevoTurno = { dateTime: '', available: true };

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit() {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });

    this.http.get<any>('http://localhost:8080/providers/me', { headers }).subscribe({
      next: (data) => {
        this.nombrePrestador = `${data.firstName} ${data.lastName}`;
        localStorage.setItem('providerId', data.id);
      },
      error: () => alert('Error al obtener nombre del prestador')
    });


    this.http.get<any[]>('http://localhost:8080/shifts/mis-turnos', { headers }).subscribe({
      next: (data) => {
        this.solicitudes = data.filter(t => t.customerName && t.status === 'PENDING');
      },
      error: () => alert('Error al obtener solicitudes')
    });

    this.cargarTurnosDelPrestador();
  }

  aceptarSolicitud(id: number) {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });

    this.http.post(`http://localhost:8080/shifts/aceptar/${id}`, {}, { headers }).subscribe({
      next: () => {
        this.solicitudes = this.solicitudes.filter(s => s.id !== id);
        alert('Solicitud aceptada');
        this.cargarTurnosDelPrestador();
      },
      error: () => alert('Error al aceptar solicitud')
    });
  }

  agregarTurno() {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });

    const providerId = Number(localStorage.getItem('providerId'));
    if (!providerId) {
      alert('No se pudo obtener el ID del prestador');
      return;
    }

    const turno = {
      providerId: providerId,
      dateTime: this.nuevoTurno.dateTime,
      available: true
    };

    this.http.post('http://localhost:8080/shifts/create-multiple', [turno], { headers }).subscribe({
      next: () => {
        alert('Turno agregado correctamente');
        this.nuevoTurno.dateTime = '';
        this.cargarTurnosDelPrestador();
      },
      error: err => {
        console.error('Error al agregar turno:', err);
        alert('Error al agregar turno');
      }
    });
  }

cargarTurnosDelPrestador() {
  const headers = new HttpHeaders({
    Authorization: `Bearer ${localStorage.getItem('token')}`
  });

  this.http.get<any[]>('http://localhost:8080/shifts/mis-turnos', { headers }).subscribe({
    next: (data) => {
      console.log('TODOS LOS TURNOS DEL BACKEND:', data);

      this.turnosDisponibles = data.filter(t => !t.customerName);

      this.turnosAgendados = data
        .filter(t => t.customerName && (t.status === 'ACCEPTED' || t.status === 'PENDING'));
    },
    error: () => alert('Error al cargar los turnos del prestador')
  });
}




  eliminarTurno(id: number) {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    });

    this.http.delete(`http://localhost:8080/shifts/${id}`, { headers }).subscribe({
      next: () => {
        this.turnosDisponibles = this.turnosDisponibles.filter(t => t.id !== id);
        this.turnosAgregados = this.turnosAgregados.filter(t => t.id !== id);
        alert('Turno eliminado correctamente');
      },
      error: () => alert('Error al eliminar el turno')
    });
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('providerId');
    this.router.navigate(['/login']);
  }
}
