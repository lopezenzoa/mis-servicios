import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-registro',
  standalone: true,
 imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './registro.component.html',  styleUrls: ['./registro.component.css']

})
export class RegistroComponent {
  formData = {
    firstName: '',
    lastName: '',
    email: '',
    address: '',
    username: '',
    password: '',
    confirmPassword: ''
  };

  constructor(private http: HttpClient, private router: Router) {}

 onSubmit() {
   if (this.formData.password !== this.formData.confirmPassword) {
     alert('Las contraseñas no coinciden');
     return;
   }

   const { confirmPassword, ...datosAEnviar } = this.formData;

   this.http.post('http://localhost:8080/auth/register', datosAEnviar).subscribe({
     next: () => {

       this.router.navigate(['/login']);
     },
     error: (err) => {
       console.error(err);
       alert('Error al registrar usuario: ' + (err.error?.message || 'desconocido'));
     },
   });
 }

}
