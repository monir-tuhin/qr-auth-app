import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from './_services/token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username?: string;

  sidebar: any;
  closeBtn: any;
  searchBtn: any;

  toogleNav = false;

  constructor(
    private tokenStorageService: TokenStorageService,
    private router: Router,
  ) {
  }

  ngOnInit(): void {
    console.log('i am calling');
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAdminBoard = this.roles.includes('ROLE_ADMIN');
      this.showModeratorBoard = this.roles.includes('ROLE_MODERATOR');

      this.username = user.username;
    } else {
      // this.router.navigate(['home']);
    }
    this.initUi();
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.router.navigate(['login']);
    setTimeout(() => {
      window.location.reload();
    }, 50);
  }

  initUi(): any {
    this.sidebar = document.querySelector('.sidebar');
    this.closeBtn = document.querySelector('#btn');
    this.searchBtn = document.querySelector('.bx-search');

    if (this.closeBtn) {
      this.closeBtn.addEventListener('click', () => {
        this.sidebar.classList.toggle('open');
        this.menuBtnChange(); // calling the function(optional)
      });
    }
    // following are the code to change sidebar button(optional)
  }

  onClickToggleNav(): void {
    console.log('clicked');
    this.toogleNav = !this.toogleNav;
  }

  menuBtnChange(): void {
    if (this.sidebar.classList.contains('open')) {
      this.closeBtn.classList.replace('bx-menu', 'bx-menu-alt-right'); // replacing the iocns class
    } else {
      this.closeBtn.classList.replace('bx-menu-alt-right', 'bx-menu'); // replacing the iocns class
    }
  }
}
