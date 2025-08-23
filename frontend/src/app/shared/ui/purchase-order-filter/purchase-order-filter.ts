import {Component, inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import {NzDatePickerModule} from 'ng-zorro-antd/date-picker';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzOptionComponent, NzSelectComponent} from 'ng-zorro-antd/select';

@Component({
  selector: 'app-purchase-order-filter',
  templateUrl: './purchase-order-filter.html',
  styleUrls: ['./purchase-order-filter.css'],
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NzDatePickerModule, NzFormModule, NzButtonComponent, NzSelectComponent, NzOptionComponent],
})

export class PurchaseOrderFilterComponent {
  // Mock data
  productList = [
    { id: 1, name: 'Product A' },
    { id: 2, name: 'Product B' },
    { id: 3, name: 'Product C' }
  ];

  customerList = [
    { id: 1, name: 'Customer X' },
    { id: 2, name: 'Customer Y' },
    { id: 3, name: 'Customer Z' }
  ];

  statusList = ['All', 'Open', 'Closed'];
  fb = inject(NonNullableFormBuilder);

  filterForm = this.fb.group({
    // not null: NonNullableFormBuilder guarantees value is string, not null
    month: this.fb.control('', { validators: Validators.required }),
    // fields can be null
    productId: this.fb.control<number | null>(null),
    customerId: this.fb.control<number | null>(null),
    status: new FormControl<string | null>(null)
  });

  onSubmit() {
    if (this.filterForm.invalid) {
      Object.values(this.filterForm.controls).forEach((control) => {
        if (control.invalid){
          control.markAsDirty();
          control.updateValueAndValidity();
        }
      })
      console.error('Form is invalid');
    } else {
      console.log(this.filterForm.value);
    }

  }

  onReset(){
    this.filterForm.reset({ status: 'Open' });
  }
}
