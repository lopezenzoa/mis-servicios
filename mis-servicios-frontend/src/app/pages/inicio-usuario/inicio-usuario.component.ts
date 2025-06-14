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
    {
      name: 'Carlos Gómez',
      service: 'Plomero',
      description: 'Soluciones rápidas para cualquier problema de plomería en tu hogar.',
      contact: 'carlos@example.com'
    },
    {
      name: 'Ana Torres',
      service: 'Pintor',
      description: 'Pintura de interiores y exteriores con acabados impecables.',
      contact: 'ana@example.com'
    },
    {
      name: 'Luis Fernández',
      service: 'Paseador de perros',
      description: 'Cuido y paseo tu mascota con cariño y responsabilidad.',
      contact: 'luis@example.com'
    },
    {
      name: 'Sofía Martínez',
      service: 'Limpieza',
      description: 'Servicios de limpieza profesional para hogares y oficinas.',
      contact: 'sofia@example.com'
    }
  ];

  constructor() {}

  ngOnInit(): void {
    this.prestadoresFiltrados = this.prestadores;
  }

  filtrar(): void {
    this.prestadoresFiltrados = this.prestadores.filter(p => {
      const coincideServicio = this.filtroServicio ? p.service.toLowerCase() === this.filtroServicio.toLowerCase() : true;
      const coincideUbicacion = this.filtroUbicacion ? true : true; // Se puede mejorar si se usa backend con ubicación
      return coincideServicio && coincideUbicacion;
    });
  }
}
