import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  onSubmit() {
    const loginData = {
      username: this.email,
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
