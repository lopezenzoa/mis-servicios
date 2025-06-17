import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { InicioUsuarioComponent } from './pages/inicio-usuario/inicio-usuario.component';
import { InicioPrestadorComponent } from './pages/inicio-prestador/inicio-prestador.component';
import { AgendarTurnoComponent } from './pages/agendar-turno/agendar-turno.component';
export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegisterComponent },
  { path: 'inicio-usuario', component: InicioUsuarioComponent },
  { path: 'inicio-prestador', component: InicioPrestadorComponent },


{ path: 'agendar-turno', component: AgendarTurnoComponent },


  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' },
];

