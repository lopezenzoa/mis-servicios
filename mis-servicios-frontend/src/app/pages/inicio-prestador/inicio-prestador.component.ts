import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-inicio-prestador',
  templateUrl: './inicio-prestador.component.html',
  styleUrls: ['./inicio-prestador.component.css']
})
export class InicioPrestadorComponent implements OnInit {
  nombrePrestador: string = 'Juan Pérez';
  solicitudes: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarSolicitudes();
  }

  cargarSolicitudes(): void {
    this.http.get<any[]>('http://localhost:8080/solicitudes')
      .subscribe({
        next: (data) => this.solicitudes = data,
        error: (err) => console.error('Error al cargar solicitudes:', err)
      });
  }

  aceptarSolicitud(solicitudId: number): void {
    this.http.post(`http://localhost:8080/solicitudes/${solicitudId}/aceptar`, {})
      .subscribe({
        next: () => {
          alert('Solicitud aceptada');
          this.solicitudes = this.solicitudes.filter(s => s.id !== solicitudId);
        },
        error: () => alert('Error al aceptar solicitud')
      });
  }
}
