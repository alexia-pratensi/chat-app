import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { LoginRequest } from 'src/app/interfaces/loginRequest.interface';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';
import { SessionService } from 'src/app/services/session.service';
import { UserApiService } from 'src/app/services/user-api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  public hide = true;
  public onError = false;

  constructor(private authService: UserApiService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService) {
    }
  
  // Form for login
  protected loginForm = this.fb.group({
    username: [
      '',
      [
        Validators.required,
      ]
    ],
    password: [
      '',
      Validators.required
    ]
  });

  // Sends the login request to the server and redirects to the posts page when the form is submitted
  protected submitLogin(): void {
    const loginRequest = this.loginForm.value as LoginRequest;
    this.authService.login(loginRequest)
      .pipe(take(1))
      .subscribe({
        next: (response: SessionInformation) => {
          this.sessionService.logIn(response);
          if(response.role === 'AGENT') {
            this.router.navigate(['/agent']);
          } else {
            this.router.navigate(['/chat', response.id]);
          }
        },
      error: error => this.onError = true,
    });
  }
}
