import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PrestadorService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    });
  }

  obtenerDatosPrestador(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/providers/me`, {
      headers: this.getAuthHeaders()
    });
  }

  obtenerSolicitudes(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/shifts/mis-turnos`, {
      headers: this.getAuthHeaders()
    });
  }

  aceptarSolicitud(id: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/shifts/aceptar/${id}`, {}, {
      headers: this.getAuthHeaders()
    });
  }
}
