import { Component, ViewChild } from '@angular/core';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { DiscountFormComponent } from '../discount-form/discount-form'; // ✅ import your form
import { NzButtonModule } from 'ng-zorro-antd/button';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [NzModalModule, DiscountFormComponent, NzButtonModule],
  templateUrl: './discount-modal.html',
  styleUrls: ['./discount-modal.css'],
})
export class DiscountModal {
  isVisible = false;

  // 👇 link template reference to component instance
  @ViewChild('discountForm') discountForm!: DiscountFormComponent;
  handleCancel(): void {
    this.isVisible = false;
  }

  handleOk(discountForm: DiscountFormComponent): void {
    console.log('Apply clicked', {
      mode: discountForm.radioValue,
      unitPrice: discountForm.value,
      discountType: discountForm.discountType,
      discountValue: discountForm.discountValue,
    });
    this.isVisible = false;
  }
}
