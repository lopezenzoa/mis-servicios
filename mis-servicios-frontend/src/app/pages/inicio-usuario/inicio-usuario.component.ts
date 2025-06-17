import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router'; // <-- Asegurate de importar Router
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-inicio-usuario',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './inicio-usuario.component.html',
  styleUrls: ['./inicio-usuario.component.css']
})
export class InicioUsuarioComponent implements OnInit {
  prestadoresFiltrados: any[] = [];
  prestadoresOriginal: any[] = [];
  categorias: string[] = [];
  filtroServicio = '';
  filtroUbicacion = '';

  constructor(private http: HttpClient, private router: Router) {} // <-- INYECTAR Router

ngOnInit(): void {
  const headers = { Authorization: `Bearer ${localStorage.getItem('token')}` };

  this.http.get<any[]>('http://localhost:8080/providers/', { headers }).subscribe({
    next: data => {
      this.prestadoresOriginal = data;
      this.prestadoresFiltrados = data;
    },
    error: err => console.error('Error al cargar prestadores', err)
  });

  this.http.get<any[]>('http://localhost:8080/categorias').subscribe({
    next: data => this.categorias = data.map(c => c.nombre),
    error: err => console.error('Error al cargar categorías')
  });
}

  verTurnos(id: number): void {
    if (id) {
      this.router.navigate(['/agendar-turno'], {
        queryParams: { prestadorId: id }
      });
    } else {
      console.error('El ID del prestador no está definido');
    }
  }

  filtrar(): void {
    this.prestadoresFiltrados = this.prestadoresOriginal.filter((p: any) =>
      (this.filtroServicio ? p.facility === this.filtroServicio : true) &&
      (this.filtroUbicacion ? p.address?.toLowerCase().includes(this.filtroUbicacion.toLowerCase()) : true)
    );
  }
logout(): void {
  localStorage.removeItem('token');
  localStorage.removeItem('role');
  this.router.navigate(['/login']);
}

}
