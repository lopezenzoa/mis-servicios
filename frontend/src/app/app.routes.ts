import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { InicioComponent } from './components/inicio/inicio.component';
import { RegistroComponent } from './components/registro/registro.component';
import { PrestadoresListadoComponent } from './components/prestadores-listado/prestadores-listado.component';
import { ServiciosListadoComponent } from './components/services/servicios-listado.component';


export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'inicio', component: InicioComponent },
  { path: 'registro', component: RegistroComponent },
  { path: 'prestadores', component: PrestadoresListadoComponent },
 { path: 'servicios', component: ServiciosListadoComponent }

];
