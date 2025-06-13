import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  mostrarLogin = true;

  loginData = {
    email: '',
    password: ''
  };

registroData = {
  role: 'CLIENTE',
  nombre: '',
  apellido: '',
  username: '',
  email: '',
  password: '',
  confirmPassword: '',
  categoria: '',
  descripcion: '',
  phoneNumber: '',
  address: ''
};
registroErrores: { [key: string]: string } = {};



  categorias: string[] = [];

  constructor(private http: HttpClient) {}

ngOnInit() {
  this.http.get<any[]>('http://localhost:8080/categorias')
    .subscribe({
      next: data => this.categorias = data.map(c => c.nombre),
      error: err => console.error('Error al cargar categorías', err)
    });
}

  handleLoginSubmit() {
    const { email, password } = this.loginData;

    if (!email || !password) {
      alert('Por favor, completa todos los campos.');
      return;
    }

    this.http.post<any>('http://localhost:8080/auth/login', {
      identifier: email,
      password: password
    }).subscribe({
      next: res => {
        localStorage.setItem('token', res.token);
        alert('Inicio de sesión exitoso.');
      },
      error: err => alert('Error al iniciar sesión.')
    });
  }

 handleRegisterSubmit() {
   const { role, nombre, apellido, email, password, confirmPassword, categoria, descripcion, username, phoneNumber, address } = this.registroData;

   if (!nombre || !apellido || !email || !password || !confirmPassword || !username || !phoneNumber || !address) {
     alert('Por favor, completa todos los campos.');
     return;
   }

   if (password !== confirmPassword) {
     alert('Las contraseñas no coinciden.');
     return;
   }

   const body: any = {
     firstName: nombre,
     lastName: apellido,
     username,
     email,
     address,
     phoneNumber,
     password,
     role
   };

   if (role === 'PROVEEDOR') {
     if (!categoria || !descripcion) {
       alert('Todos los campos son obligatorios para prestadores de servicio.');
       return;
     }

     body.descripcion = descripcion;
     body.categoria = categoria;
   }

   this.http.post('http://localhost:8080/auth/register', body).subscribe({
     next: () => {
       alert('Registro exitoso.');
       this.mostrarLogin = true;
     },
     error: err => {
       this.registroErrores = {};

       if (err.status === 400 && err.error) {
         this.registroErrores = err.error;
       } else {
           this.registroErrores['general'] = "Error al registrarse.";
         }

     }
   });

 }
}
