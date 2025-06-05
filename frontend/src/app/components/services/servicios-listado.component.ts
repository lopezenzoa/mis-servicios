import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServiceService, Servicio } from './service.service';
import { FormsModule } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-servicios-listado',
 imports: [CommonModule, FormsModule],
  templateUrl: './servicios-listado.component.html',
})
export class ServiciosListadoComponent implements OnInit {
  servicios: Servicio[] = [];
  servicioSeleccionado: string = '';

  constructor(private serviceService: ServiceService) {}

  ngOnInit(): void {
    this.serviceService.getServicios().subscribe((data) => {
      this.servicios = data;
      console.log('Servicios cargados:', this.servicios);
    });
  }
}
