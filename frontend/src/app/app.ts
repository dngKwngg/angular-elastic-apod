import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SearchBarComponent } from "./shared/ui/search-bar/search-bar";
import { DiscountFormComponent } from "./shared/ui/discount-form/discount-form";
import { DiscountModal } from './shared/ui/discount-modal/discount-modal';
import {RecordListComponent} from './shared/ui/record-list/record-list';
import {PurchaseOrderFilterComponent} from './shared/ui/purchase-order-filter/purchase-order-filter';
import {CashSalesFilterComponent} from './shared/ui/cash-sales-filter/cash-sales-filter';

@Component({
  selector: 'app-root',
  //   imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css',
  imports: [SearchBarComponent, RecordListComponent, PurchaseOrderFilterComponent, CashSalesFilterComponent],
})
export class App {
  protected readonly title = signal('frontend');
  results: any;
}
