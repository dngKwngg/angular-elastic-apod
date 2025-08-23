import { Component, inject } from '@angular/core';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzButtonModule } from 'ng-zorro-antd/button';
import {dateRangeValidator} from '../../validators/date-range.validator';

@Component({
  selector: 'app-cash-sales-filter',
  templateUrl: './cash-sales-filter.html',
  styleUrls: ['./cash-sales-filter.css'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NzFormModule,
    NzDatePickerModule,
    NzButtonModule,
  ],
})
export class CashSalesFilterComponent {
  fb = inject(FormBuilder); // ✅ use FormBuilder (not NonNullableFormBuilder)

  filterForm = this.fb.group({
    dateFrom: [null, Validators.required],
    dateTo: [null, [dateRangeValidator]],
  });

  onSubmit() {
    if (this.filterForm.invalid) {
      Object.values(this.filterForm.controls).forEach((control) => {
        control.markAsDirty();
        control.updateValueAndValidity();
      });

      // console log the specific validation errors
      const errors = this.filterForm.errors;
      if (errors) {
        console.error('Form validation errors:', errors);
      }
    } else {
      console.log(this.filterForm.value);
    }
  }

  onReset() {
    this.filterForm.reset();
  }
}
