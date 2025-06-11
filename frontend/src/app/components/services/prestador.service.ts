import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CategoryService } from '../services/category.service';

@Injectable({
  providedIn: 'root'
})
export class PrestadorService {
  private apiUrl = 'http://localhost:8080/providers/disponibles';

  constructor(private http: HttpClient) {}

  obtenerPrestadoresFiltrados(categoria?: string, ubicacion?: string): Observable<any[]> {
    let params = new HttpParams();
    if (categoria) params = params.set('categoria', categoria);
    if (ubicacion) params = params.set('ubicacion', ubicacion);

    return this.http.get<any[]>(this.apiUrl, { params });
  }
}
