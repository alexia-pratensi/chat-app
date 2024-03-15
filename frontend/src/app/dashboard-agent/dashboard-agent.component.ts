import { Component, OnInit, OnDestroy } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { User } from 'src/app/interfaces/user.interface';
import { UserService } from '../services/user.service';
import { MessageApiService } from '../services/message-api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard-agent',
  templateUrl: './dashboard-agent.component.html',
  styleUrls: ['./dashboard-agent.component.scss']
})
export class DashboardAgentComponent implements OnInit, OnDestroy {
  public currentUser$!: Observable<User>;
  public users: User[] = [];
  private unsubscribe$ = new Subject<void>();

  constructor(private userService: UserService,
              private messageApiService: MessageApiService,
              private router: Router) {}

  ngOnInit(): void {
    this.currentUser$ = this.userService.getCurrentUser();
    this.getUsersWhoSentMessagesToAgent();
  }

  private getUsersWhoSentMessagesToAgent() {
    this.currentUser$.pipe(
      takeUntil(this.unsubscribe$)
    ).subscribe({
      next: (user: User) => {
        // Fetching the users who sent messages to the agent
        this.messageApiService.getMessagesFromUsersToAgent(user.id).pipe(
          takeUntil(this.unsubscribe$)
        ).subscribe({
          next: (data) => {
            this.users = data;
          },
          error: (error) => console.log(error)
        });
      },
      error: (error) => console.log(error)
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe$.next();
    this.unsubscribe$.complete();
  }

  public navigateToChat(userId: number) {
    this.router.navigate(['/chat', userId]);
  }
}
