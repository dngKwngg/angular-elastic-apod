import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { NzInputModule } from 'ng-zorro-antd/input';
import { NzRadioModule } from 'ng-zorro-antd/radio';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzButtonModule } from 'ng-zorro-antd/button';

@Component({
  selector: 'app-discount-form',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    NzRadioModule,
    NzInputModule,
    NzSelectModule,
    NzButtonModule,
  ],
  templateUrl: './discount-form.html',
  styleUrls: ['./discount-form.css'],
})
export class DiscountFormComponent {
	@Output() close = new EventEmitter<void>();
	@Output() apply = new EventEmitter<any>();

	radioValue: string = 'unitPrice';
	value: number | null = null;
	discountType: string = '%';
	discountValue: number | null = null;

	onClose(): void {
		this.close.emit();
	}

	onApply(): void {
		this.apply.emit({
			mode: this.radioValue,
			unitPrice: this.value,
			discountType: this.discountType,
			discountValue: this.discountValue,
		});
	}
}
