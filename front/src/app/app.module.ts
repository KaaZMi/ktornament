import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { RouterModule } from "@angular/router";
import { PlayersListComponent } from "./players-list/players-list.component";
import { TopBarComponent } from "./top-bar/top-bar.component";
import { HttpClientModule } from "@angular/common/http";
import { EditPlayerComponent } from "./edit-player/edit-player.component";
import { FormsModule } from "@angular/forms";
import { AddPlayerComponent } from "./add-player/add-player.component";

@NgModule({
  imports: [
    BrowserModule,
    RouterModule.forRoot([
      { path: '', pathMatch: 'full', redirectTo: 'players-list' },
      { path: 'players-list', component: PlayersListComponent },
      { path: 'add-player', component: AddPlayerComponent },
      { path: 'edit-player/:id', component: EditPlayerComponent },
    ]),
    HttpClientModule,
    FormsModule
  ],
  declarations: [
    AppComponent,
    TopBarComponent,
    PlayersListComponent,
    AddPlayerComponent,
    EditPlayerComponent,
  ],
  bootstrap: [AppComponent],
  providers: []
})
export class AppModule {
}
