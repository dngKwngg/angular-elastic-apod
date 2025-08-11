import { Component } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { NzIconModule } from "ng-zorro-antd/icon";
import { NzInputModule } from "ng-zorro-antd/input";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.html',
  styleUrls: ['./search-bar.css'],
  imports: [FormsModule, NzInputModule, NzIconModule]
})

export class SearchBarComponent {
	searchTerm: string = '';

	onSearch() {
		console.log('Search term:', this.searchTerm);
	}
}