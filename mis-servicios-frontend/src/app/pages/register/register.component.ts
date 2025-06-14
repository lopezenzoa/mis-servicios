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

  // Prestador
  licenseNumber = '';
  categorias: string[] = ['Electricista', 'Plomero', 'Gasista', 'Pintor', 'Jardinero','Paseador de perro','Niniera','Maguiver','Techista','Herrero','Carpintero'];
  categoriaSeleccionada: string = '';


  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/categorias').subscribe({
      next: data => this.categorias = data.map(c => c.nombre),
      error: err => console.error('Error al cargar categorías', err)
    });
  }

onSubmit() {
  if (this.password !== this.confirmPassword) {
    alert('Las contraseñas no coinciden');
    return;
  }

  if (this.rol === 'prestador' && !this.categoriaSeleccionada) {
    alert('Seleccioná una categoría');
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

  let url = '';

  if (this.rol === 'prestador') {
    payload.licenseNumber = this.licenseNumber;
    payload.facility = this.categoriaSeleccionada;
    url = 'http://localhost:8080/providers/register';
  } else {
    url = 'http://localhost:8080/customers/register';
  }

  this.http.post(url, payload, { responseType: 'text' }).subscribe({
    next: res => {
      alert(res);
      this.router.navigate(['/login']);
    },
    error: err => {
      alert('Error en el registro');
      console.error(err);
    }
  });
}


}
