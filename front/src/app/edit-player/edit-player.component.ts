import { Component, Input, OnInit } from '@angular/core';
import { PlayersApiService } from "../shared/players-api.service";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-edit-player',
  templateUrl: './edit-player.component.html',
  styleUrls: ['./edit-player.component.css']
})
export class EditPlayerComponent implements OnInit {
  id = this.actRoute.snapshot.params['id'];
  @Input() points = 0;

  constructor(
    public playersApiService: PlayersApiService,
    public actRoute: ActivatedRoute,
    public router: Router
  ) {
  }

  ngOnInit() {
    this.playersApiService.getPlayer(this.id).subscribe((data: { points: number }) => {
      this.points = data.points;
    })
  }

  editPlayer(points: number | undefined) {
    if (points) {
      this.playersApiService.updatePlayer(this.id, points).subscribe(() => {
        this.router.navigate(['/players-list']);
      });
    }
  }
}
