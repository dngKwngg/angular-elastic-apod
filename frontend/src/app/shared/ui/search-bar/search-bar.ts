import {Component, EventEmitter, Output} from "@angular/core";
import { FormsModule } from "@angular/forms";
import { NzIconModule } from "ng-zorro-antd/icon";
import { NzInputModule } from "ng-zorro-antd/input";
import {NzButtonModule} from 'ng-zorro-antd/button';
import {ApiService} from '../../../service/api.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.html',
  styleUrls: ['./search-bar.css'],
  imports: [FormsModule, NzInputModule, NzIconModule, NzButtonModule, CommonModule]
})

export class SearchBarComponent {
	searchTerm: string = '';
  @Output() resultsChange = new EventEmitter<any[]>();

  constructor(private apiService: ApiService) {
  }

	onSearch() {
		this.apiService.search(this.searchTerm).subscribe({
      next: (data: any) => {
        this.resultsChange.emit(data);
      },
      error: (err: any) => {
        console.error('Search error:', err);
        this.resultsChange.emit([]); // Emit empty array on error
      }
    })
	}
}
