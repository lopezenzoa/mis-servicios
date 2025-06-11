import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink, Router } from '@angular/router';
import { HttpClient, provideHttpClient, HttpClientModule } from '@angular/common/http';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
   const loginData = {
     identificador: this.email,
     password: this.password
   };

    this.http.post('http://localhost:8080/auth/login', loginData, {
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.router.navigate(['/inicio']);
      },
      error: (err) => {
        alert(err.error);
      }
    });
  }
}
