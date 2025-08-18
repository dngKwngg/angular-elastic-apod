import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SearchBarComponent } from "./shared/ui/search-bar/search-bar";
import { DiscountFormComponent } from "./shared/ui/discount-form/discount-form";
import { TestComponent } from './shared/ui/test-component/test-component';

@Component({
  selector: 'app-root',
  //   imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
  imports: [SearchBarComponent, DiscountFormComponent, TestComponent],
})
export class App {
  protected readonly title = signal('frontend');
}
