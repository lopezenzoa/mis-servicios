import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-inicio-usuario',
  templateUrl: './inicio-usuario.component.html',
  styleUrls: ['./inicio-usuario.component.css']
})
export class InicioUsuarioComponent implements OnInit {
  filtroServicio: string = '';
  filtroUbicacion: string = '';
  prestadoresFiltrados: any[] = [];

  categorias: string[] = [
    'Gasista', 'Plomero', 'Jardinero', 'Pintor', 'Paseador de perros', 'Limpieza'
  ];

  prestadores: any[] = [
    {
      name: 'Juan Pérez',
      service: 'Gasista',
      description: 'Experto en instalaciones y reparaciones de gas con 10 años de experiencia.',
      contact: 'juan@example.com'
    },
    {
      name: 'María López',
      service: 'Jardinero',
      description: 'Diseño y mantenimiento de jardines, trato amable y profesional.',
      contact: 'maria@example.com'
    },

  ];

  constructor() {}

  ngOnInit(): void {
    this.prestadoresFiltrados = this.prestadores;
  }

  filtrar(): void {
    this.prestadoresFiltrados = this.prestadores.filter(p => {
      const coincideServicio = this.filtroServicio ? p.service.toLowerCase() === this.filtroServicio.toLowerCase() : true;
      const coincideUbicacion = this.filtroUbicacion ? true : true;
      return coincideServicio && coincideUbicacion;
    });
  }
}
