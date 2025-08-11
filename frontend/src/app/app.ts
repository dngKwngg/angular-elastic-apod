import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SearchBarComponent } from "./shared/ui/search-bar/search-bar";

@Component({
  selector: 'app-root',
//   imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
  imports: [SearchBarComponent]
})
export class App {
  protected readonly title = signal('frontend');
}
