import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Servicio {
  id: number;
  name: string;
  description: string;
}

@Injectable({ providedIn: 'root' })
export class ServiceService {
  private apiUrl = 'http://localhost:8080/servicios';

  constructor(private http: HttpClient) {}

  getServicios(): Observable<Servicio[]> {
    return this.http.get<Servicio[]>(this.apiUrl);
  }
}
