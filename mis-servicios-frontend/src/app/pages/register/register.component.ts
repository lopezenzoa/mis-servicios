import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  firstName = '';
  lastName = '';
  email = '';
  address = '';
  phoneNumber = '';
  username = '';
  password = '';
  confirmPassword = '';
  rol: 'usuario' | 'prestador' = 'usuario';

  licenseNumber = '';
  categorias: string[] = [];
  categoriaSeleccionada: string = '';

  camposInvalidos: string[] = [];
  mensajeError: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/categorias').subscribe({
      next: data => this.categorias = data.map(c => c.nombre),
      error: err => console.error('Error al cargar categorías', err)
    });
  }

  onSubmit() {
    this.camposInvalidos = [];
    this.mensajeError = '';

    const campos = [
      { nombre: 'firstName', valor: this.firstName },
      { nombre: 'lastName', valor: this.lastName },
      { nombre: 'email', valor: this.email },
      { nombre: 'address', valor: this.address },
      { nombre: 'phoneNumber', valor: this.phoneNumber },
      { nombre: 'username', valor: this.username },
      { nombre: 'password', valor: this.password },
      { nombre: 'confirmPassword', valor: this.confirmPassword }
    ];

    campos.forEach(campo => {
      if (!campo.valor.trim()) {
        this.camposInvalidos.push(campo.nombre);
      }
    });

    if (this.rol === 'prestador') {
      if (!this.licenseNumber.trim()) this.camposInvalidos.push('licenseNumber');
      if (!this.categoriaSeleccionada) this.camposInvalidos.push('categoria');
    }

    if (this.camposInvalidos.length > 0) {
      this.mensajeError = 'Por favor completá todos los campos obligatorios.';
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.mensajeError = 'Las contraseñas no coinciden.';
      this.camposInvalidos.push('password', 'confirmPassword');
      return;
    }
const regexPasswordFuerte = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_]).{8,}$/;

if (!regexPasswordFuerte.test(this.password)) {
  this.mensajeError = 'La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas, un número y un símbolo.';
  this.camposInvalidos.push('password');
  return;
}
    const payload: any = {
      firstName: this.firstName,
      lastName: this.lastName,
      email: this.email,
      address: this.address,
      phoneNumber: this.phoneNumber,
      username: this.username,
      password: this.password,
    };

    let url = this.rol === 'prestador'
      ? 'http://localhost:8080/providers/register'
      : 'http://localhost:8080/customers/register';

    if (this.rol === 'prestador') {
      payload.licenseNumber = this.licenseNumber;
      payload.facility = this.categoriaSeleccionada;
    }

 this.http.post(url, payload, { responseType: 'json' }).subscribe({
   next: (res: any) => {
     this.router.navigate(['/login']);
   },
   error: err => {
     // Captura mensaje del backend
     if (err.error && typeof err.error === 'string') {
       this.mensajeError = err.error;
     } else if (err.error?.message) {
       this.mensajeError = err.error.message;
     } else {
       this.mensajeError = 'Error en el registro. Intentá nuevamente.';
     }
     console.error(err);
   }
 });

}
}
