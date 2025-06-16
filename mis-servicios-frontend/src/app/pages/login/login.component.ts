import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  mensajeError: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    this.mensajeError = '';

    const payload = {
      identifier: this.email,
      password: this.password
    };

    this.http.post('http://localhost:8080/auth/login', payload, { responseType: 'json' }).subscribe({
      next: (res: any) => {
        // Guardar token y rol en localStorage si se desea
        if (res.token) localStorage.setItem('token', res.token);
        if (res.role) localStorage.setItem('role', res.role);

        // Redireccionar según el rol
        if (res.role === 'CLIENTE') {
          this.router.navigate(['/inicio-usuario']);
        } else if (res.role === 'PROVEEDOR') {
          this.router.navigate(['/inicio-prestador']);
        } else {
          this.mensajeError = 'Rol desconocido. Contacte al administrador.';
        }
      },
      error: err => {
        if (err.status === 401) {
          this.mensajeError = 'Usuario o contraseña incorrectos';
        } else {
          this.mensajeError = 'Error al intentar iniciar sesión';
        }
        console.error(err);
      }
    });
  }

  mostrarRegistro() {
    this.router.navigate(['/registro']);
  }
}
