import { Component, OnInit } from '@angular/core';
import { PrestadorService } from '../services/prestador.service';
import { CategoryService } from '../services/category.service';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Categoria {
  id: number;
  nombre: string;
}

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
  categorias: string[] = [];

  constructor(
    private prestadorService: PrestadorService,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    this.aplicarFiltros();
    this.categoryService.getCategorias().subscribe((data: Categoria[]) => {
      this.categorias = data.map((c: Categoria) => c.nombre);
    });
  }

  aplicarFiltros(): void {
    this.prestadorService
      .obtenerPrestadoresFiltrados(this.filtroCategoria, this.filtroUbicacion)
      .subscribe((data: any[]) => {
        this.prestadores = data;
      });
  }
contactarPorWhatsApp(numero: string): void {
  const url = `https://wa.me/${numero.replace(/\D/g, '')}`;
  window.open(url, '_blank');
}

}
