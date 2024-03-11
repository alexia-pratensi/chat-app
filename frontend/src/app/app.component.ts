import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SessionService } from './services/session.service';
import { UserService } from './services/user.service';
import { User } from './interfaces/user.interface';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ChatAppFrontend';
  public currentUser$: Observable<User> | undefined;

  constructor(private router: Router,
              private userService: UserService,
              private sessionService: SessionService) {
                if (!this.isHomePage()) {
                  this.currentUser$ = this.userService.getCurrentUser();
                }
              }

  // check if the current page is the home page
  public isHomePage(): boolean {
    return this.router.url.valueOf() === '/';
  }

  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }
  
}
