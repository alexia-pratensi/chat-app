import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { UnauthGuard } from './auth/guards/unauth.guard';
import { AuthGuard } from './auth/guards/auth.guard';
import { ChatComponent } from './chat/chat.component';
import { DashboardAgentComponent } from './dashboard-agent/dashboard-agent.component';

const routes: Routes = [
  { path: 'chat/:userId', component: ChatComponent, canActivate: [AuthGuard] },
  { path: 'agent', component: DashboardAgentComponent, canActivate: [AuthGuard] },
  { path: '', component: LoginComponent, canActivate: [UnauthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
