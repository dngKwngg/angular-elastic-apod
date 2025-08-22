import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzCardModule } from 'ng-zorro-antd/card';

@Component({
  selector: 'app-record-list',
  templateUrl: './record-list.html',
  standalone: true,
  imports: [CommonModule, NzCardModule],
  styleUrls: ['./record-list.css']
})
export class RecordListComponent {
  @Input() results?: any[];

  ngOnInit() {
    if (!this.results) {
      this.results = [];
    }
  }

}
