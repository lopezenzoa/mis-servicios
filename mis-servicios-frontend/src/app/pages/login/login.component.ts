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

  this.http.post('http://localhost:8080/auth/login', payload).subscribe({
    next: (res: any) => {
      localStorage.setItem('token', res.token);
      localStorage.setItem('role', res.role);

      // 👇 Redirigir según el rol
      if (res.role === 'ROLE_CUSTOMER') {
        this.router.navigate(['/inicio-usuario']);
      } else if (res.role === 'ROLE_PROVIDER') {
        this.router.navigate(['/inicio-prestador']);
      } else {
        alert('Rol no reconocido');
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
