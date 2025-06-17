import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { InicioUsuarioComponent } from './pages/inicio-usuario/inicio-usuario.component';
import { InicioPrestadorComponent } from './pages/inicio-prestador/inicio-prestador.component';
import { AgendarTurnoComponent } from './pages/agendar-turno/agendar-turno.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  // Públicas
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegisterComponent },

  // Protegidas con AuthGuard y rol
  {
    path: 'inicio-usuario',
    component: InicioUsuarioComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_CUSTOMER' }
  },
  {
    path: 'inicio-prestador',
    component: InicioPrestadorComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_PROVIDER' }
  },
  {
    path: 'agendar-turno',
    component: AgendarTurnoComponent,
    canActivate: [AuthGuard],
    data: { role: 'ROLE_CUSTOMER' }
  },

  // Redirecciones
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];
