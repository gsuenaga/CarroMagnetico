// import { Routes } from '@angular/router';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { DetailComponent } from './components/configuracion/detail.component';
import { SerialPortComponent } from './components/serialport/serialport.component';
import { ReglaComponent } from './components/configuracion/regla.component';

const routes: Routes = [
    { path: 'home', component: HomeComponent },
    { path: 'detail/:id/:port/:baudrate/:databits/:stopbits', component: DetailComponent },
    { path: 'ports', component: SerialPortComponent },
    { path: 'regla', component: ReglaComponent },
    { path: '', pathMatch: 'full', redirectTo: 'home' },
    { path: '**', pathMatch: 'full', redirectTo: 'home' }
];

@NgModule({
    imports : [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}
