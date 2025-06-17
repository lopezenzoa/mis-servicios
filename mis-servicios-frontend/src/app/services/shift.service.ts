import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Shift {
  id: number;
  providerId: number;
  dateTime: string;
  available: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ShiftService {
  private apiUrl = 'http://localhost:8080/shifts';

  constructor(private http: HttpClient) {}

  getAvailableShifts(): Observable<Shift[]> {
    return this.http.get<Shift[]>(`${this.apiUrl}/availables`);
  }

  getShiftsByProvider(providerId: number): Observable<Shift[]> {
    return this.http.get<Shift[]>(`${this.apiUrl}/availables/${providerId}`);
  }

  reserveShift(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/reservar/${id}`, {});
  }
}
