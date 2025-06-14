import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router'; // 👈 FALTA ESTO

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule], // 👈 AGREGA RouterModule
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient) {}

onSubmit() {
  const payload = {
    identifier: this.email,
    password: this.password
  };

this.http.post('http://localhost:8080/auth/login', payload, { responseType: 'text' }).subscribe({
  next: (res: string) => {
    alert(res);

  },
  error: err => {
    alert('Error en el login');
    console.error(err);
  }
});
}


  mostrarRegistro() {
    alert('Función de cambio a registro pendiente de implementar');
  }
}
