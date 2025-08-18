import { Component, inject } from '@angular/core';
import { NonNullableFormBuilder, ReactiveFormsModule } from '@angular/forms';

import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzTableModule } from 'ng-zorro-antd/table';

interface InvoiceItem {
  id: number;
  description: string;
  quantity: number;
  unitPrice: number;
  subTotal: number;
  sst: number;
  discount: number;
  totalDiscount: number;
}

@Component({
  selector: 'invoice-manual-form',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NzButtonModule,
    NzFormModule,
    NzInputModule,
    NzDividerModule,
    NzTableModule,
  ],
  templateUrl: './invoice-mform.html',
  styleUrls: ['./invoice-mform.css'],
})
export class InvoiceManualFormComponent {
  // constrain key to InvoiceItem properties
  listOfColumns: { title: string; key: keyof InvoiceItem }[] = [
    { title: '#', key: 'id' },
    { title: 'Description', key: 'description' },
    { title: 'Quantity', key: 'quantity' },
    { title: 'Unit Price', key: 'unitPrice' },
    { title: 'Sub Total', key: 'subTotal' },
    { title: 'SST', key: 'sst' },
    { title: 'Discount', key: 'discount' },
    { title: 'Total Discount', key: 'totalDiscount' },
  ];

  listOfData: InvoiceItem[] = [
    {
      id: 1,
      description: 'Laptop Dell XPS 13',
      quantity: 2,
      unitPrice: 4500,
      subTotal: 9000,
      sst: 540,
      discount: 200,
      totalDiscount: 8800,
    },
    {
      id: 2,
      description: 'Wireless Mouse Logitech',
      quantity: 5,
      unitPrice: 120,
      subTotal: 600,
      sst: 36,
      discount: 50,
      totalDiscount: 586,
    },
    {
      id: 3,
      description: 'Mechanical Keyboard Keychron K2',
      quantity: 3,
      unitPrice: 350,
      subTotal: 1050,
      sst: 63,
      discount: 30,
      totalDiscount: 1083,
    },
  ];

  private fb = inject(NonNullableFormBuilder);

  form = this.fb.group({
    invoiceNo: [''],
    soNo: [''],
    term: [''],
    customerName: [''],
    address: [''],
    tel: [''],
  });

  submitForm(): void {
    console.log('Form Values:', this.form.value);
    console.log('Table Data:', this.listOfData);
  }
}
