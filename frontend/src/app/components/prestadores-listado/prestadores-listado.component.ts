import { Component, OnInit } from '@angular/core';
import { PrestadorService } from '../services/prestador.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-prestadores-listado',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './prestadores-listado.component.html',
  styleUrls: ['./prestadores-listado.component.css']
})
export class PrestadoresListadoComponent implements OnInit {
  prestadores: any[] = [];
  filtroCategoria = '';
  filtroUbicacion = '';

  constructor(
    private prestadorService: PrestadorService
  ) {}

  ngOnInit(): void {
    this.aplicarFiltros();
  }

  aplicarFiltros(): void {
    this.prestadorService
      .obtenerPrestadoresFiltrados(this.filtroCategoria, this.filtroUbicacion)
      .subscribe((data: any[]) => {
        this.prestadores = data;
      });
  }
}
