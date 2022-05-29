import { Component, OnInit } from '@angular/core';
import { PlayersApiService } from "../shared/players-api.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-players-list',
  templateUrl: './players-list.component.html',
  styleUrls: ['./players-list.component.css']
})
export class PlayersListComponent implements OnInit {
  players: any = []

  constructor(
    public playersApiService: PlayersApiService,
    public router: Router
  ) {
  }

  ngOnInit() {
    this.loadPlayers();
  }

  loadPlayers() {
    this.playersApiService.getPlayers().subscribe((data: {}) => {
      this.players = data;
    });
  }

  deletePlayers() {
    this.playersApiService.deletePlayers().subscribe(() => {
      this.router.navigate(['/players-list']);
    });
  }
}
