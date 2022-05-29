import { Component, Input, OnInit } from '@angular/core';
import { PlayersApiService } from "../shared/players-api.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-add-player',
  templateUrl: './add-player.component.html',
  styleUrls: ['./add-player.component.css']
})
export class AddPlayerComponent implements OnInit {
  @Input() pseudo = '';

  constructor(public playersApiService: PlayersApiService, public router: Router) {
  }

  ngOnInit() {
  }

  addPlayer(pseudo: string | undefined) {
    if (pseudo) {
      this.playersApiService.addPlayer(pseudo).subscribe(() => {
        this.router.navigate(['/players-list']);
      });
    }
  }
}
